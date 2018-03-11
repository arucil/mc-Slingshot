package plodsoft.slingshot.recipes;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;
import plodsoft.slingshot.ModItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class LootingSlingshotRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {


    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, World worldIn) {
        boolean hasSlingshot = false, hasBook = false;
        for (int i = inv.getSizeInventory(); --i >= 0; ) {
            ItemStack stack = inv.getStackInSlot(i);
            if (null != stack) {
                Item item = stack.getItem();
                if (item == ModItems.slingshot) {
                    if (hasSlingshot)
                        return false;
                    hasSlingshot = true;
                } else if (item == Items.ENCHANTED_BOOK) {
                    if (hasBook)
                        return false;
                    if (getBookLootingLevel(stack) <= 0)
                        return false;
                    hasBook = true;
                } else
                    return false;
            }
        }

        return hasSlingshot && hasBook;
    }

    // EnchantmentHelper.getEnchantmentLevel doesn't work for enchanted book
    private static int getBookLootingLevel(ItemStack stack) {
        /*
        NBTTagList nbttaglist = Items.ENCHANTED_BOOK.getEnchantments(stack);

        if (nbttaglist != null) {
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                Enchantment enchantment = Enchantment.getEnchantmentByID(
                        nbttaglist.getCompoundTagAt(i).getShort("id"));
                int j = nbttaglist.getCompoundTagAt(i).getShort("lvl");
                if (Enchantments.LOOTING == enchantment)
                    return j;
            }
        }

        return 0;
        */
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack);
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack stkSlingshot = null;
        int lv = 0;
        for (int i = inv.getSizeInventory(); --i >= 0; ) {
            ItemStack stack = inv.getStackInSlot(i);
            if (null != stack) {
                Item item = stack.getItem();
                if (item == ModItems.slingshot) {
                    if (null != stkSlingshot)
                        return null;
                    stkSlingshot = stack;
                } else if (item == Items.ENCHANTED_BOOK) {
                    if (lv > 0)
                        return null;
                    if ((lv = getBookLootingLevel(stack)) <= 0)
                        return null;
                } else
                    return null;
            }
        }

        if (null == stkSlingshot || 0 == lv)
            return null;
        ItemStack stack = stkSlingshot.copy();
        Map<Enchantment, Integer> ench = EnchantmentHelper.getEnchantments(stack);
        int lv1 = ench.getOrDefault(Enchantments.LOOTING, 0);
        lv = lv == lv1 ? Math.max(Enchantments.LOOTING.getMaxLevel(), lv + 1) : Math.max(lv, lv1);
        ench.put(Enchantments.LOOTING, lv);
        EnchantmentHelper.setEnchantments(ench, stack);
        return stack;
    }


    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    public static class Factory implements IRecipeFactory {

        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            return new LootingSlingshotRecipe();
        }
    }
}
