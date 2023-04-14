package net.dreamer.why.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.dreamer.why.WhyAccessible;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class LavaItem extends BlockItem implements Equipment {
    public LavaItem() {
        super(Blocks.LAVA,new Settings().food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.GLOWING,2400,0),1.0F).alwaysEdible().build()).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Direction direction = context.getSide();
        BlockPos pos = context.getBlockPos().offset(direction);
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() == Blocks.LAVA && state.get(FluidBlock.LEVEL) > 0)
            world.setBlockState(pos,Blocks.AIR.getDefaultState(),3);

        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack,@Nullable World world,List<Text> tooltip,TooltipContext context) {
        MutableText text = Text.translatable(this.getTranslationKey() + ".why");
        Style style = text.getStyle().withColor(new Color(218,85,0).getRGB());
        tooltip.add(text.setStyle(style).formatted(Formatting.BOLD,Formatting.ITALIC));

        super.appendTooltip(stack,world,tooltip,context);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public void inventoryTick(ItemStack stack,World world,Entity entity,int slot,boolean selected) {
        if(selected && !entity.isWet())
            if(!(entity instanceof PlayerEntity player) || !player.getAbilities().creativeMode) entity.setFireTicks(200);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        user.damage(((WhyAccessible) user.getDamageSources()).getEatLava(),Float.MAX_VALUE);

        return super.finishUsing(stack,world,user);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,"Lava damage boost",6.0D,EntityAttributeModifier.Operation.ADDITION));
        return slot == EquipmentSlot.MAINHAND ? builder.build() : super.getAttributeModifiers(slot);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }
}
