package hellfirepvp.pandemic.registry.internal;

import hellfirepvp.pandemic.registry.RegistryEffects;
import hellfirepvp.pandemic.registry.RegistryItems;
import hellfirepvp.pandemic.registry.RegistrySounds;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * This class is part of the Pandemic Mod
 * Class: PrimerEventHandler
 * Created by HellFirePvP
 * Date: 27.11.2020 / 20:29
 */
public class PrimerEventHandler {

    private final InternalRegistryPrimer registry;

    public PrimerEventHandler(InternalRegistryPrimer registry) {
        this.registry = registry;
    }

    public void attachEventHandlers(IEventBus eventBus) {
        eventBus.addGenericListener(Item.class, this::registerItems);
        eventBus.addGenericListener(Effect.class, this::registerEffects);
        eventBus.addGenericListener(SoundEvent.class, this::registerSounds);
    }

    private void registerItems(RegistryEvent.Register<Item> event) {
        RegistryItems.init();
        fillRegistry(event.getRegistry().getRegistrySuperType(), event.getRegistry());
    }

    private void registerEffects(RegistryEvent.Register<Effect> event) {
        RegistryEffects.init();
        fillRegistry(event.getRegistry().getRegistrySuperType(), event.getRegistry());
    }

    private void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        RegistrySounds.init();
        fillRegistry(event.getRegistry().getRegistrySuperType(), event.getRegistry());
    }

    private <T extends IForgeRegistryEntry<T>> void fillRegistry(Class<T> registrySuperType, IForgeRegistry<T> forgeRegistry) {
        registry.getEntries(registrySuperType).forEach(e -> forgeRegistry.register((T) e));
    }

}
