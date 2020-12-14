package hellfirepvp.pandemic;

import hellfirepvp.pandemic.data.ConfigPandemic;
import hellfirepvp.pandemic.event.EventHandlerEntity;
import hellfirepvp.pandemic.registry.internal.InternalRegistryPrimer;
import hellfirepvp.pandemic.registry.internal.PrimerEventHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is part of the Pandemic Mod
 * Class: Pandemic
 * Created by HellFirePvP
 * Date: 27.11.2020 / 20:27
 */
@Mod(Pandemic.MODID)
public class Pandemic {

    public static final String MODID = "pandemic";
    public static final String NAME = "Pandemic";

    public static Logger log = LogManager.getLogger(NAME);

    private static Pandemic instance;

    private final InternalRegistryPrimer registryPrimer;
    private final PrimerEventHandler registryEventHandler;

    private final ConfigPandemic config;

    public Pandemic() {
        instance = this;

        this.registryPrimer = new InternalRegistryPrimer();
        this.registryEventHandler = new PrimerEventHandler(this.registryPrimer);

        this.attachLifecycle(FMLJavaModLoadingContext.get().getModEventBus());
        this.attachEventListeners(MinecraftForge.EVENT_BUS);

        Pair<ConfigPandemic, ForgeConfigSpec> cfg = new ForgeConfigSpec.Builder().configure(ConfigPandemic::new);
        config = cfg.getLeft();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, cfg.getRight());
    }

    private void attachLifecycle(IEventBus eventBus) {
        registryEventHandler.attachEventHandlers(eventBus);
    }

    private void attachEventListeners(IEventBus eventBus) {
        EventHandlerEntity.attachEventListeners(eventBus);
    }

    public ConfigPandemic getConfig() {
        return config;
    }

    public InternalRegistryPrimer getRegistryPrimer() {
        return registryPrimer;
    }

    public static Pandemic getInstance() {
        return instance;
    }

    public static ResourceLocation key(String path) {
        return new ResourceLocation(Pandemic.MODID, path);
    }
}
