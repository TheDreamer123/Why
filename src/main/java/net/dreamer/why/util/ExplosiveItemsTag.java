package net.dreamer.why.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ExplosiveItemsTag {
    public static TagKey<Item> explosiveItems() {
        return TagKey.of(RegistryKeys.ITEM,WhyRegistryHandler.getId("explosive_items"));
    }
}
