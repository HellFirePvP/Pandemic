package hellfirepvp.pandemic.event;

import hellfirepvp.pandemic.Pandemic;
import hellfirepvp.pandemic.data.ConfigPandemic;
import hellfirepvp.pandemic.lib.EffectsPD;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.List;
import java.util.Random;

/**
 * This class is part of the Pandemic Mod
 * Class: EventHandlerEntity
 * Created by HellFirePvP
 * Date: 27.11.2020 / 21:29
 */
public class EventHandlerEntity {

    private static final Random rand = new Random();

    public static void attachEventListeners(IEventBus bus) {
        bus.addListener(EventHandlerEntity::onHurt);
        bus.addListener(EventHandlerEntity::onSpawn);
    }

    private static void onSpawn(LivingSpawnEvent.SpecialSpawn event) {
        if (event.getWorld().isRemote() || event.getEntityLiving() instanceof PlayerEntity) {
            return;
        }

        ConfigPandemic cfg = Pandemic.getInstance().getConfig();
        if (rand.nextFloat() < cfg.applySpawnChance.get()) {
            event.getEntityLiving().addPotionEffect(new EffectInstance(EffectsPD.DISEASE, Integer.MAX_VALUE));
        }
    }

    private static void onHurt(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity hurt = (PlayerEntity) event.getEntityLiving();

        LivingEntity src = null;
        DamageSource source = event.getSource();
        if (source.getImmediateSource() instanceof LivingEntity) {
            src = (LivingEntity) source.getImmediateSource();
        } else if (source.getTrueSource() instanceof LivingEntity) {
            src = (LivingEntity) source.getTrueSource();
        }
        if (src == null) {
            return;
        }

        ConfigPandemic cfg = Pandemic.getInstance().getConfig();
        String typeStr = src.getType().getRegistryName().toString();
        List<? extends String> configuredEntities = cfg.configuredEntities.get();

        if (!configuredEntities.contains(typeStr)) {
            return;
        }

        if (rand.nextFloat() < cfg.applyChance.get()) {
            hurt.addPotionEffect(new EffectInstance(EffectsPD.DISEASE, Integer.MAX_VALUE));
            return;
        }
        if (src.isPotionActive(EffectsPD.DISEASE) && rand.nextFloat() < cfg.applyChanceDisease.get()) {
            hurt.addPotionEffect(new EffectInstance(EffectsPD.DISEASE, Integer.MAX_VALUE));
        }
    }

}
