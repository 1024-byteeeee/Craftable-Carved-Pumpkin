
package top.byteeeee.craftable_carved_pumpkin.specialCraftingRecipe;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;

import static top.byteeeee.craftable_carved_pumpkin.CraftableCarvedPumpkin.PUMPKIN_SCISSORS_RECIPE_SERIALIZER;

public class PumpkinScissorsRecipe extends SpecialCraftingRecipe {
    public PumpkinScissorsRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private int shearsSlot = 0;
    ItemStack shearsStack = new ItemStack(Items.SHEARS);
    ItemStack carvedStack = new ItemStack(Items.CARVED_PUMPKIN);

    @Override
    public boolean matches(RecipeInputInventory inv, World world) {
        ArrayList<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.PUMPKIN) {
                    list.add(stack);
                } else if (stack.getItem().getDefaultStack().getItem() == Items.SHEARS) {
                    list.add(stack);
                }
            }
        }
        return list.size() == 2;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inv, DynamicRegistryManager registryManager) {
        boolean hasPumpkin = false;
        boolean hasShears = false;
        int count = 0;
        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                count++;
                if (stack.getItem() == Items.PUMPKIN) {
                    hasPumpkin = true;
                } else if (stack.getItem().getDefaultStack().getItem() == Items.SHEARS) {
                    NbtCompound nbtTag = stack.getOrCreateNbt();
                    shearsStack.setNbt(nbtTag);
                    hasShears = true;
                    shearsSlot = i;
                }
            }
        }
        if (hasPumpkin && hasShears && count == 2) {
            if (shearsStack.getDamage() >= shearsStack.getMaxDamage() && shearsStack.isDamageable()) {
                inv.setStack(shearsSlot, ItemStack.EMPTY);
                shearsStack = ItemStack.EMPTY;
            } else {
                return carvedStack = new ItemStack(Items.CARVED_PUMPKIN);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(RecipeInputInventory inv) {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(inv.size(), ItemStack.EMPTY);
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (i == shearsSlot && stack.getItem() == Items.SHEARS) {
                ItemStack damagedShears = stack.copy();
                damagedShears.setNbt(stack.getNbt());
                damagedShears.damage(1, Random.create(), null);
                if (damagedShears.getDamage() >= shearsStack.getMaxDamage() && shearsStack.isDamageable()) {
                    inv.setStack(shearsSlot, ItemStack.EMPTY);
                } else if (damagedShears.isDamaged()) {
                    list.set(i, damagedShears);
                }
            }
        }
        return list;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PUMPKIN_SCISSORS_RECIPE_SERIALIZER;
    }
}