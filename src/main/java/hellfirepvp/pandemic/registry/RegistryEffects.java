package hellfirepvp.pandemic.registry;

import hellfirepvp.pandemic.Pandemic;
import hellfirepvp.pandemic.effect.EffectDisease;
import hellfirepvp.pandemic.util.NameUtil;
import net.minecraft.potion.Effect;

import static hellfirepvp.pandemic.lib.EffectsPD.*;

/**
 * This class is part of the Pandemic Mod
 * Class: RegistryEffects
 * Created by HellFirePvP
 * Date: 27.11.2020 / 20:43
 */
public class RegistryEffects {

    public static void init() {
        DISEASE = register(new EffectDisease());
    }

    private static <T extends Effect> T register(T effect) {
        effect.setRegistryName(NameUtil.fromClass(effect, "Effect"));
        Pandemic.getInstance().getRegistryPrimer().register(effect);
        return effect;
    }

}
