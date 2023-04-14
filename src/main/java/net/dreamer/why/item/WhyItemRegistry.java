package net.dreamer.why.item;

import net.dreamer.why.util.WhyRegistryHandler;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

public class WhyItemRegistry {
    public static final Item LAVA = new LavaItem();
    public static final Item LAVA_SOUP = new LavaSoupItem();
    public static final Item CHARRED_BOWL = new CharredBowlItem();

    public static void register() {
        registerItem("lava",LAVA);
        registerItem("lava_soup",LAVA_SOUP);
        registerItem("charred_bowl",CHARRED_BOWL);

        FuelRegistry.INSTANCE.add(LAVA,FuelRegistry.INSTANCE.get(Items.LAVA_BUCKET));
        FuelRegistry.INSTANCE.add(LAVA_SOUP,FuelRegistry.INSTANCE.get(Items.LAVA_BUCKET) / 4 + FuelRegistry.INSTANCE.get(Items.BOWL) / 4);
        FuelRegistry.INSTANCE.add(CHARRED_BOWL,FuelRegistry.INSTANCE.get(Items.CHARCOAL) / 4 * 3);
    }

    private static void registerItem(String id,Item item) {
        WhyRegistryHandler.register(Registries.ITEM,id,item);
    }
}
