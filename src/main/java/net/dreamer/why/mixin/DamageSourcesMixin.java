package net.dreamer.why.mixin;

import net.dreamer.why.WhyAccessible;
import net.dreamer.why.util.WhyDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements WhyAccessible {
    @Shadow protected abstract DamageSource create(RegistryKey<DamageType> key);
    @Shadow protected abstract DamageSource create(RegistryKey<DamageType> key,@Nullable Entity source,@Nullable Entity attacker);

    private DamageSource eatLava;

    @Inject(at = @At("TAIL"), method = "<init>")
    public void initializeNewDamageSources(DynamicRegistryManager registryManager,CallbackInfo info) {
        this.eatLava = this.create(WhyDamageTypes.EAT_LAVA);
    }

    public DamageSource getEatLava() {
        return this.eatLava;
    }

    public DamageSource lavaOnFire(@Nullable Entity source, @Nullable Entity attacker) {
        return this.create(WhyDamageTypes.LAVA_ON_FIRE,source,attacker);
    }
}
