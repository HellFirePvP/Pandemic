/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2020
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.pandemic.util;

import com.google.common.base.CaseFormat;
import hellfirepvp.pandemic.Pandemic;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * This class is part of the Pandemic Mod
 * Class: NameUtil
 * Created by HellFirePvP
 * Date: 27.11.2020 / 20:33
 */
public class NameUtil {

    public static ResourceLocation prefixPath(ResourceLocation key, String prefix) {
        return new ResourceLocation(key.getNamespace(), prefix + key.getPath());
    }

    public static ResourceLocation suffixPath(ResourceLocation key, String suffix) {
        return new ResourceLocation(key.getNamespace(), key.getPath() + suffix);
    }

    public static ResourceLocation fromClass(Object object) {
        return fromClass(object, null);
    }

    public static ResourceLocation fromClass(Class<?> clazz) {
        return fromClass(clazz, null);
    }

    public static ResourceLocation fromClass(Object object, @Nullable String cutPrefix) {
        return fromClass(object, cutPrefix, null);
    }

    public static ResourceLocation fromClass(Class<?> clazz, @Nullable String cutPrefix) {
        return fromClass(clazz, cutPrefix, null);
    }

    public static ResourceLocation fromClass(Object object, @Nullable String cutPrefix, @Nullable String cutSuffix) {
        return fromClass(object.getClass(), cutPrefix, cutSuffix);
    }

    public static ResourceLocation fromClass(Class<?> clazz, @Nullable String cutPrefix, @Nullable String cutSuffix) {
        String name = clazz.getSimpleName();
        if (clazz.getEnclosingClass() != null) {
            name = clazz.getEnclosingClass().getSimpleName() + name;
        }
        if (cutPrefix != null && name.startsWith(cutPrefix)) {
            name = name.substring(cutPrefix.length());
        }
        if (cutSuffix != null && name.endsWith(cutSuffix)) {
            name = name.substring(0, name.length() - cutSuffix.length());
        }
        name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        return Pandemic.key(name);
    }

}
