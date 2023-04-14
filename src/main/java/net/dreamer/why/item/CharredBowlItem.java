package net.dreamer.why.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class CharredBowlItem extends Item {
    public CharredBowlItem() {
        super(new Settings());
    }

    @Override
    public void appendTooltip(ItemStack stack,@Nullable World world,List<Text> tooltip,TooltipContext context) {
        MutableText text = Text.translatable(this.getTranslationKey() + ".why");
        Style style = text.getStyle().withColor(new Color(75,75,75).getRGB());
        tooltip.add(text.setStyle(style).formatted(Formatting.BOLD,Formatting.ITALIC));
    }
}
