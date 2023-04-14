package net.dreamer.why.util;

import net.dreamer.why.Why;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

public class WhyGameruleRegistry {
    private static final CustomGameRuleCategory WHY = new CustomGameRuleCategory(WhyRegistryHandler.getId("why"),Text.translatable("category." + Why.MOD_ID).formatted(Formatting.GOLD).formatted(Formatting.BOLD));

    private static GameRules.Key<GameRules.IntRule> LAVA_EXPLOSION_POWER;
    private static GameRules.Key<GameRules.BooleanRule> LAVA_EXPLOSION_DESTROY_EVERYTHING;


    public static void register() {
        LAVA_EXPLOSION_POWER = GameRuleRegistry.register(Why.MOD_ID + ":lavaExplosionPower",WHY,GameRuleFactory.createIntRule(10,0));
        LAVA_EXPLOSION_DESTROY_EVERYTHING = GameRuleRegistry.register(Why.MOD_ID + ":lavaExplosionDestroyEverything",WHY,GameRuleFactory.createBooleanRule(true));
    }

    public static GameRules.Key<GameRules.IntRule> getLavaExplosionPower() {
        return LAVA_EXPLOSION_POWER;
    }

    public static GameRules.Key<GameRules.BooleanRule> getLavaExplosionDestroyEverything() {
        return LAVA_EXPLOSION_DESTROY_EVERYTHING;
    }
}
