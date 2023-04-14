package net.dreamer.why.mixin;

import net.dreamer.why.WhyAccessible;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registry;register(Lnet/minecraft/registry/Registry;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;"), method = "register")
    private static <T> T modifyLavaSettings(Registry<? super T> registry,String id,T entry) {
        if(id.equals("lava")) {
            AbstractBlock.Settings settings = ((WhyAccessible) entry).getSettings();
            settings.hardness(100.0F);
            ((WhyAccessible) entry).setSettings(settings);
            ((Block) entry).getDefaultState().hardness = settings.hardness;
        }

        return Registry.register(registry,id,entry);
    }
}
