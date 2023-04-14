package net.dreamer.why.mixin;

import net.dreamer.why.WhyAccessible;
import net.dreamer.why.item.LavaItem;
import net.dreamer.why.util.ExplosiveItemsTag;
import net.dreamer.why.util.WhyGameruleRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract ItemStack getStack();
    @Shadow public abstract void setStack(ItemStack stack);

    public ItemEntityMixin(EntityType<?> type,World world) {
        super(type,world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void turnLavaIntoStone(CallbackInfo info) {
        if(this.getStack().getItem() instanceof LavaItem)
            if(this.isTouchingWater()) {
                NbtCompound compound = this.getStack().getNbt();
                ItemStack stack = new ItemStack(Items.OBSIDIAN,this.getStack().getCount());
                if(compound != null) stack.getOrCreateNbt().copyFrom(compound);
                this.setStack(stack);
                for(int i = 0; i < stack.getCount(); i++) this.playExtinguishSound();
            }
        if(this.getStack().isIn(ExplosiveItemsTag.explosiveItems()))
            if(!this.world.isClient)
                if(this.isOnFire() || this.isInLava()) {
                    if(!this.world.getGameRules().getBoolean(WhyGameruleRegistry.getLavaExplosionDestroyEverything()))
                        if(this.isInLava()) this.setPosition(this.getX(),this.getY() + 1.0D,this.getZ());
                    for(int i = 0; i < this.getStack().getCount(); i++)
                        this.world.createExplosion(this,((WhyAccessible) this.getDamageSources()).lavaOnFire(this,this),null,this.getX(),this.getY() - i,this.getZ(),this.world.getGameRules().getInt(WhyGameruleRegistry.getLavaExplosionPower()),true,World.ExplosionSourceType.TNT);
                    this.discard();
                }
    }
}
