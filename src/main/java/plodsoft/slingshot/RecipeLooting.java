package plodsoft.slingshot;

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
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

public class RecipeLooting implements IRecipe {
   private static final ItemStack[] EMPTY_ITEMS = new ItemStack[9];

   private final ItemStack slingshot = new ItemStack(ModItems.slingshot);

   @Override
   public boolean matches(InventoryCrafting inv, World worldIn) {
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
   static int getBookLootingLevel(ItemStack stack) {
      NBTTagList nbttaglist = Items.ENCHANTED_BOOK.getEnchantments(stack);

      if (nbttaglist != null) {
         for (int i = 0; i < nbttaglist.tagCount(); ++i)
         {
            Enchantment enchantment = Enchantment.getEnchantmentByID(
                  nbttaglist.getCompoundTagAt(i).getShort("id"));
            int j = nbttaglist.getCompoundTagAt(i).getShort("lvl");
            if (Enchantments.LOOTING == enchantment)
               return j;
         }
      }

      return 0;
   }

   @Nullable
   @Override
   public ItemStack getCraftingResult(InventoryCrafting inv) {
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

   @Override
   public int getRecipeSize() {
      return 4;
   }

   @Nullable
   @Override
   public ItemStack getRecipeOutput() {
      return null;
   }

   @Override
   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
      return EMPTY_ITEMS;
   }
}
