package hellfirepvp.pandemic.registry;

import hellfirepvp.pandemic.Pandemic;
import hellfirepvp.pandemic.item.ItemSanitizer;
import hellfirepvp.pandemic.util.NameUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static hellfirepvp.pandemic.lib.ItemsPD.*;

/**
 * This class is part of the Pandemic Mod
 * Class: RegistryItems
 * Created by HellFirePvP
 * Date: 27.11.2020 / 20:30
 */
public class RegistryItems {

    public static void init() {
        SANITIZER = registerItem(new ItemSanitizer());
    }

    private static <T extends Item> T registerItem(T item) {
        ResourceLocation name = NameUtil.fromClass(item, "Item");
        item.setRegistryName(name);
        Pandemic.getInstance().getRegistryPrimer().register(item);
        return item;
    }

}
