package net.dreamer.why.mixin;

import net.dreamer.why.util.WhyDamageTypes;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registerable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTypes.class)
public interface DamageTypesMixin {
    @Inject(at = @At("TAIL"), method = "bootstrap")
    private static void addLavaExplosion(Registerable<DamageType> damageTypeRegisterable,CallbackInfo info) {
        damageTypeRegisterable.register(WhyDamageTypes.EAT_LAVA,new DamageType("why:eat_lava",DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,0.0F));
        damageTypeRegisterable.register(WhyDamageTypes.LAVA_ON_FIRE,new DamageType("why:lava_on_fire",DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,0.1F));
    }
}
