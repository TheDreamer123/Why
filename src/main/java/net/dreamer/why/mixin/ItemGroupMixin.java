package net.dreamer.why.mixin;

import net.dreamer.why.item.WhyItemRegistry;
import net.minecraft.item.*;
import org.apache.commons.compress.utils.Lists;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mixin(ItemGroup.class)
public abstract class ItemGroupMixin {
    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/item/ItemGroup$EntriesImpl;parentTabStacks:Ljava/util/Collection;"), method = "updateEntries")
    public Collection<ItemStack> addLavaStuffToTabs(ItemGroup.EntriesImpl instance) {
        boolean bl = false;
        for(ItemStack stack : instance.parentTabStacks)
            if(stack.getItem() == Items.LAVA_BUCKET || stack.getItem() == Items.RABBIT_STEW || stack.getItem() == Items.CHARCOAL) {
                bl = true;
                break;
            }
        if(bl) {
            List<ItemStack> stacks = Lists.newArrayList();
            for(ItemStack stack : instance.parentTabStacks) {
                if(stack.getItem() == Items.LAVA_BUCKET) this.addLavaStuff(stacks,false);
                stacks.add(stack);
                if(stack.getItem() == Items.CHARCOAL) stacks.add(new ItemStack(WhyItemRegistry.CHARRED_BOWL));
                if(stack.getItem() == Items.RABBIT_STEW) this.addLavaStuff(stacks,true);
            }

            return stacks;
        }

        return instance.parentTabStacks;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/item/ItemGroup$EntriesImpl;searchTabStacks:Ljava/util/Set;"), method = "updateEntries")
    public Set<ItemStack> addLavaStuffToSearch(ItemGroup.EntriesImpl instance) {
        Set<ItemStack> stacks = ItemStackSet.create();
        for(ItemStack stack : instance.searchTabStacks) {
            if(stack.getItem() == Items.LAVA_BUCKET) this.addLavaStuff(stacks,true);
            stacks.add(stack);
            if(stack.getItem() == Items.BOWL) stacks.add(new ItemStack(WhyItemRegistry.CHARRED_BOWL));
        }

        return stacks;
    }

    private void addLavaStuff(Collection<ItemStack> stacks,boolean food) {
        stacks.add(new ItemStack(WhyItemRegistry.LAVA));
        if(food) stacks.add(new ItemStack(WhyItemRegistry.LAVA_SOUP));
    }
}
