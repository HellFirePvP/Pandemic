package hellfirepvp.pandemic.effect;

import hellfirepvp.pandemic.Pandemic;
import hellfirepvp.pandemic.data.ConfigPandemic;
import hellfirepvp.pandemic.lib.EffectsPD;
import hellfirepvp.pandemic.lib.SoundsPD;
import hellfirepvp.pandemic.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is part of the Pandemic Mod
 * Class: EffectDisease
 * Created by HellFirePvP
 * Date: 27.11.2020 / 20:40
 */
public class EffectDisease extends Effect {

    private static final Random rand = new Random();
    private final Map<UUID, Long> lastTickPlacements = new HashMap<>();

    public EffectDisease() {
        super(EffectType.HARMFUL, 0x8600d9);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {
        World world = livingEntity.getEntityWorld();
        if (world.isRemote() || !livingEntity.isAlive()) {
            return;
        }
        ConfigPandemic cfg = Pandemic.getInstance().getConfig();
        EffectInstance effect = livingEntity.getActivePotionEffect(this);
        if (effect != null) { //Should be present though
            int dur = effect.getDuration();
            if (dur % 20 == 0) {
                AxisAlignedBB box = new AxisAlignedBB(0, 0, 0, 0, 0, 0)
                        .grow(cfg.spreadRadius.get())
                        .offset(livingEntity.getPositionVec());
                List<LivingEntity> entities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, box);
                for (LivingEntity le : entities) {
                    if (rand.nextFloat() < cfg.spreadSecondChance.get()) {
                        le.addPotionEffect(new EffectInstance(EffectsPD.DISEASE, Integer.MAX_VALUE));
                    }
                }
            }
        }

        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) livingEntity;
        UUID plUUID = player.getUniqueID();
        long tick = world.getGameTime();
        int delay = cfg.tickDelay.get();
        if (lastTickPlacements.containsKey(plUUID) && lastTickPlacements.get(plUUID) + delay > tick) {
            return;
        }
        double chance = cfg.chancePerTick.get();
        if (rand.nextFloat() >= chance) {
            return;
        }

        int range = cfg.maxRadius.get() - 2;
        List<? extends String> configuredBlocks = cfg.configuredBlocks.get();
        List<BlockState> blocks = configuredBlocks.stream()
                .map(ResourceLocation::new)
                .map(ForgeRegistries.BLOCKS::getValue)
                .filter(block -> block != Blocks.AIR)
                .filter(Objects::nonNull)
                .map(Block::getDefaultState)
                .collect(Collectors.toList());
        if (blocks.isEmpty()) {
            return;
        }
        BlockState state = blocks.get(rand.nextInt(blocks.size()));

        for (int i = 0; i < 3; i++) {
            BlockPos at = player.getPosition().up()
                    .add((2 + rand.nextInt(range)) * (rand.nextBoolean() ? 1 : -1),
                            (2 + rand.nextInt(range)) * (rand.nextBoolean() ? 1 : -1),
                            (2 + rand.nextInt(range)) * (rand.nextBoolean() ? 1 : -1));
            for (int yMov = 0; yMov < 5; yMov++) {
                if (BlockUtils.isReplaceable(world, at.down(), state)) {
                    at = at.down();
                }
            }
            if (BlockUtils.isReplaceable(world, at, state) && world.setBlockState(at, state)) {
                if (rand.nextFloat() < 0.03) {

                    world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                            SoundsPD.SNEEZE, SoundCategory.HOSTILE,
                            0.3F, 1F + rand.nextFloat() * 0.05F);
                }
                lastTickPlacements.put(plUUID, tick);
                return;
            }
        }
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Collections.emptyList();
    }
}
