package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Objects;

public class DataConverterRecipeRename extends DataFix {

    private static final Map<String, String> a = ImmutableMap.builder().put("minecraft:acacia_bark", "minecraft:acacia_wood").put("minecraft:birch_bark", "minecraft:birch_wood").put("minecraft:dark_oak_bark", "minecraft:dark_oak_wood").put("minecraft:jungle_bark", "minecraft:jungle_wood").put("minecraft:oak_bark", "minecraft:oak_wood").put("minecraft:spruce_bark", "minecraft:spruce_wood").build();

    public DataConverterRecipeRename(Schema schema, boolean flag) {
        super(schema, flag);
    }

    protected TypeRewriteRule makeRule() {
        Type<Pair<String, String>> type = DSL.named(DataConverterTypes.v.typeName(), DSL.namespacedString());

        if (!Objects.equals(type, this.getInputSchema().getType(DataConverterTypes.v))) {
            throw new IllegalStateException("Recipe type is not what was expected.");
        } else {
            return this.fixTypeEverywhere("Recipes renamening fix", type, (dynamicops) -> {
                return (pair) -> {
                    return pair.mapSecond((s) -> {
                        return (String) DataConverterRecipeRename.a.getOrDefault(s, s);
                    });
                };
            });
        }
    }
}
