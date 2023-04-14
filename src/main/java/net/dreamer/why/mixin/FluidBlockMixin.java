package net.dreamer.why.mixin;

import net.dreamer.why.item.WhyItemRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin extends Block {
    @Shadow @Final public static IntProperty LEVEL;

    public FluidBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "getDroppedStacks", cancellable = true)
    public void modifyLavaDrops(BlockState state,LootContext.Builder builder,CallbackInfoReturnable<List<ItemStack>> cir) {
        if(this.equals(Blocks.LAVA) && state.get(LEVEL) == 0)
            cir.setReturnValue(List.of(new ItemStack(WhyItemRegistry.LAVA)));
    }

    @Inject(at = @At("HEAD"), method = "getOutlineShape", cancellable = true)
    public void modifyLavaOutline(BlockState state,BlockView world,BlockPos pos,ShapeContext context,CallbackInfoReturnable<VoxelShape> cir) {
        if(this.equals(Blocks.LAVA) && state.get(LEVEL) == 0)
            cir.setReturnValue(Block.createCuboidShape(0.0,0.0,0.0,16.0,14.5,16.0));
    }
}
