package net.dreamer.why.mixin;

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("HEAD"), method = "onBreak")
    public void breakLavaBlockFix(World world,BlockPos pos,BlockState state,PlayerEntity player,CallbackInfo info) {
        if(state.isOf(Blocks.LAVA)) {
            int level = MiningLevelManager.getRequiredMiningLevel(state);
            boolean bl = player.getMainHandStack().getItem() instanceof PickaxeItem pickaxe && pickaxe.getMaterial().getMiningLevel() >= level && !player.getAbilities().creativeMode;
            world.setBlockState(pos,Blocks.AIR.getDefaultState());
            if(bl) {
                BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
                Block.dropStacks(state,world,pos,blockEntity,player,player.getMainHandStack());
            }
        }
    }
}
