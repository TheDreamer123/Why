package net.dreamer.why.util;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class WhyDamageTypes {
    public static final RegistryKey<DamageType> EAT_LAVA = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,WhyRegistryHandler.getId("eat_lava"));
    public static final RegistryKey<DamageType> LAVA_ON_FIRE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,WhyRegistryHandler.getId("lava_on_fire"));
}
