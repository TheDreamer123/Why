package net.dreamer.why.item;

import net.dreamer.why.WhyAccessible;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class LavaSoupItem extends Item {
    public LavaSoupItem() {
        super(new Settings().maxCount(4).rarity(Rarity.EPIC).food(new FoodComponent.Builder().hunger(7).saturationModifier(0.7F).statusEffect(new StatusEffectInstance(StatusEffects.GLOWING,600,0),1.0F).alwaysEdible().build()));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        super.finishUsing(stack,world,user);
        user.damage(((WhyAccessible) user.getDamageSources()).getEatLava(),15.0F);
        if(stack.isEmpty()) return new ItemStack(WhyItemRegistry.CHARRED_BOWL);

        if(user instanceof PlayerEntity player && !((PlayerEntity)user).getAbilities().creativeMode) {
            ItemStack itemStack = new ItemStack(WhyItemRegistry.CHARRED_BOWL);
            if(!player.getInventory().insertStack(itemStack)) player.dropItem(itemStack,false);
        }

        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack,@Nullable World world,List<Text> tooltip,TooltipContext context) {
        MutableText text = Text.translatable(this.getTranslationKey() + ".why");
        Style style = text.getStyle().withColor(new Color(185,112,80).getRGB());
        tooltip.add(text.setStyle(style).formatted(Formatting.BOLD,Formatting.ITALIC));
    }
}
