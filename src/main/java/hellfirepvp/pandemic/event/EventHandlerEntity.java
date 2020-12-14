package hellfirepvp.pandemic.event;

import hellfirepvp.pandemic.Pandemic;
import hellfirepvp.pandemic.lib.EffectsPD;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

        String typeStr = src.getType().getRegistryName().toString();
        List<? extends String> configuredEntities = Pandemic.getInstance().getConfig().configuredEntities.get();

        if (!configuredEntities.contains(typeStr)) {
            return;
        }

        double applyChance = Pandemic.getInstance().getConfig().applyChance.get();
        if (rand.nextFloat() < applyChance) {
            hurt.addPotionEffect(new EffectInstance(EffectsPD.DISEASE, Integer.MAX_VALUE));
        }
    }

}
