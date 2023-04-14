package net.dreamer.why.mixin;

import net.dreamer.why.WhyAccessible;
import net.dreamer.why.item.LavaItem;
import net.dreamer.why.util.ExplosiveItemsTag;
import net.dreamer.why.util.WhyGameruleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Debug(export = true)
@Mixin(Explosion.class)
public abstract class ExplosionMixin implements WhyAccessible {
    @Shadow @Final @Nullable private Entity entity;
    @Shadow @Final private ExplosionBehavior behavior;
    @Shadow @Final private World world;

    private boolean shouldBehaveDifferently = false;


    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)V")
    public void initializeIsLava(World world,Entity entity,DamageSource damageSource,ExplosionBehavior behavior,double x,double y,double z,float power,boolean createFire,Explosion.DestructionType destructionType,CallbackInfo info) {
        this.shouldBehaveDifferently = this.entity instanceof ItemEntity item && item.getStack().isIn(ExplosiveItemsTag.explosiveItems()) && this.world.getGameRules().getBoolean(WhyGameruleRegistry.getLavaExplosionDestroyEverything());
    }

    @ModifyVariable(at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z", shift = At.Shift.BEFORE), method = "collectBlocksAndDamageEntities")
    public Optional<Float> modifyOptional(Optional<Float> value) {
        if(this.shouldBehaveDifferently)
            value = Optional.of(0.0F);
        return value;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/explosion/ExplosionBehavior;canDestroyBlock(Lnet/minecraft/world/explosion/Explosion;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;F)Z"), method = "collectBlocksAndDamageEntities")
    public boolean makeBlockDestroyableIfEntityLava(ExplosionBehavior instance,Explosion explosion,BlockView world,BlockPos pos,BlockState state,float power) {
        if(this.shouldBehaveDifferently && !world.getBlockState(pos).isAir())
            return true;
        return this.behavior.canDestroyBlock((Explosion) (Object) this,this.world,pos,state,power);
    }
}
