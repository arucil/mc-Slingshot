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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import plodsoft.slingshot.ModItems;

import javax.annotation.Nonnull;
import java.util.Map;

public class RecipeLootingSlingshot extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private final ItemStack output = new ItemStack(ModItems.slingshot);

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasSlingshot = false, hasBook = false;
        for (int i = inv.getSizeInventory(); --i >= 0; ) {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();
            if (item == ModItems.slingshot) {
                if (hasSlingshot) {
                    return false;
                }
                hasSlingshot = true;
            } else if (item == Items.ENCHANTED_BOOK) {
                if (hasBook) {
                    return false;
                }
                if (getBookLootingLevel(stack) <= 0) {
                    return false;
                }
                hasBook = true;
            } else if (stack.getCount() != 0) {
                return false;
            }
        }

        return hasSlingshot && hasBook;
    }

    // EnchantmentHelper.getEnchantmentLevel doesn't work for enchanted book
    private static int getBookLootingLevel(ItemStack stack) {
        NBTTagList nbttaglist = ItemEnchantedBook.getEnchantments(stack);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            Enchantment enchantment = Enchantment.getEnchantmentByID(
                    nbttaglist.getCompoundTagAt(i).getShort("id"));
            int j = nbttaglist.getCompoundTagAt(i).getShort("lvl");
            if (Enchantments.LOOTING == enchantment)
                return j;
        }

        return 0;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull  InventoryCrafting inv) {
        ItemStack stkSlingshot = null;
        int lv = 0;
        for (int i = inv.getSizeInventory(); --i >= 0; ) {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();
            if (item == ModItems.slingshot) {
                stkSlingshot = stack;
            } else if (item == Items.ENCHANTED_BOOK) {
                lv = getBookLootingLevel(stack);
            }
        }

        ItemStack stack = stkSlingshot.copy();
        Map<Enchantment, Integer> ench = EnchantmentHelper.getEnchantments(stack);
        int lv1 = ench.getOrDefault(Enchantments.LOOTING, 0);
        lv = lv == lv1 ? Math.max(Enchantments.LOOTING.getMaxLevel(), lv + 1) : Math.max(lv, lv1);
        ench.put(Enchantments.LOOTING, lv);
        EnchantmentHelper.setEnchantments(ench, stack);
        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return output.copy();
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonnulllist;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public static class Factory implements IRecipeFactory {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            return new RecipeLootingSlingshot();
        }
    }
}
