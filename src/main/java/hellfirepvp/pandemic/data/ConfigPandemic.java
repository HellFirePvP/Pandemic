package hellfirepvp.pandemic.data;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is part of the Pandemic Mod
 * Class: ConfigPandemic
 * Created by HellFirePvP
 * Date: 27.11.2020 / 21:18
 */
public class ConfigPandemic {

    public final ForgeConfigSpec.ConfigValue<List<? extends String>> configuredEntities;
    public final ForgeConfigSpec.DoubleValue applyChance;

    public final ForgeConfigSpec.IntValue maxRadius;
    public final ForgeConfigSpec.DoubleValue chancePerTick;
    public final ForgeConfigSpec.IntValue tickDelay;

    public final ForgeConfigSpec.ConfigValue<List<? extends String>> configuredBlocks;

    public ConfigPandemic(ForgeConfigSpec.Builder builder) {
        List<String> all = ForgeRegistries.ENTITIES.getValues().stream()
                .filter(EntityType::isSummonable)
                .map(ForgeRegistryEntry::getRegistryName)
                .filter(Objects::nonNull)
                .map(ResourceLocation::toString)
                .collect(Collectors.toList());
        String allStr = String.join(", ", all);

        String phantomDefault = EntityType.PHANTOM.getRegistryName().toString();
        String stoneDefault = Blocks.STONE.getRegistryName().toString();

        configuredEntities = builder
                .comment("Define the entity types that should apply the disease on hit. List of all known Types: (" + allStr + ")")
                .defineList("entities", Lists.newArrayList(phantomDefault), obj -> obj instanceof String);
        applyChance = builder
                .comment("Defines the chance on hit if the disease should be applied. 0 = 0%, 1 = 100% chance")
                .defineInRange("applyChance", 0.05, 0, 1);

        maxRadius = builder
                .comment("Defines the maximum radius in which blocks might get placed.")
                .defineInRange("maxRadius", 5, 2, 200);
        chancePerTick = builder
                .comment("Defines the chance per tick a configured block might get placed.")
                .defineInRange("chancePerTick", 0.04, 0.001, 1);
        tickDelay = builder
                .comment("Defines the minimum ticks between two block placements")
                .defineInRange("tickDelay", 10, 0, 200_000);

        configuredBlocks = builder
                .comment("Define the blocks it'll place at random. Each entry is weighted the same. Block will be randomly chosen.")
                .defineList("placeableBlocks", Lists.newArrayList(stoneDefault), obj -> obj instanceof String);
    }
}
