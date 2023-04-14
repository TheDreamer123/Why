package net.dreamer.why.util;

import net.dreamer.why.Why;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WhyRegistryHandler {
    public static <T> void register(Registry<T> registry,String id,T element) {
        Registry.register(registry,getId(id),element);
    }

    public static Identifier getId(String id) {
        return new Identifier(Why.MOD_ID,id);
    }
}
