package hellfirepvp.pandemic.registry;

import hellfirepvp.pandemic.Pandemic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import static hellfirepvp.pandemic.lib.SoundsPD.*;

/**
 * This class is part of the Pandemic Mod
 * Class: RegistrySounds
 * Created by HellFirePvP
 * Date: 27.11.2020 / 21:43
 */
public class RegistrySounds {

    public static void init() {
        SNEEZE = registerSound("sneeze");
    }

    private static <T extends SoundEvent> T registerSound(String jsonName) {
        ResourceLocation res = Pandemic.key(jsonName);
        SoundEvent se = new SoundEvent(res);
        se.setRegistryName(res);
        Pandemic.getInstance().getRegistryPrimer().register(se);
        return (T) se;
    }
}
