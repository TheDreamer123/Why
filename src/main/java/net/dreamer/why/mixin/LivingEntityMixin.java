package net.dreamer.why.mixin;

import net.dreamer.why.item.WhyItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type,World world) {
        super(type,world);
    }

    @Inject(at = @At("HEAD"), method = "damage")
    public void setAttackerOnFireIfHitByLava(DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir) {
        if(!this.isWet() && !source.isOf(DamageTypes.MAGIC) && !source.isIn(DamageTypeTags.IS_EXPLOSION) && source.getSource() != null && source.getSource() instanceof LivingEntity living && living.getMainHandStack().isOf(WhyItemRegistry.LAVA))
            this.setFireTicks(200);
    }
}
