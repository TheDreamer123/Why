package net.dreamer.why;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.Nullable;

public interface WhyAccessible {
    DamageSource getEatLava();
    DamageSource lavaOnFire(@Nullable Entity source,@Nullable Entity attacker);

    AbstractBlock.Settings getSettings();
    void setSettings(AbstractBlock.Settings settings);
}
