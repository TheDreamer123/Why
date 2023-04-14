package net.dreamer.why.mixin;

import net.dreamer.why.WhyAccessible;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin implements WhyAccessible {
    @Mutable @Shadow @Final public AbstractBlock.Settings settings;

    public AbstractBlock.Settings getSettings() {
        return this.settings;
    }

    public void setSettings(AbstractBlock.Settings settings) {
        this.settings = settings;
    }
}
