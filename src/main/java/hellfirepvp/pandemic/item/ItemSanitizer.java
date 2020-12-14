package hellfirepvp.pandemic.item;

import hellfirepvp.pandemic.lib.EffectsPD;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * This class is part of the Pandemic Mod
 * Class: ItemSanitizer
 * Created by HellFirePvP
 * Date: 27.11.2020 / 20:32
 */
public class ItemSanitizer extends Item {

    public ItemSanitizer() {
        super(new Properties()
                .maxStackSize(16)
                .group(ItemGroup.FOOD));
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entityLiving) {
        entityLiving.removePotionEffect(EffectsPD.DISEASE);
        if (!(entityLiving instanceof PlayerEntity) || !((PlayerEntity) entityLiving).abilities.isCreativeMode) {
            stack.shrink(1);
        }
        return stack;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        player.setActiveHand(hand);
        return ActionResult.resultConsume(player.getHeldItem(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 48;
    }
}
