package plodsoft.slingshot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import plodsoft.slingshot.items.ItemBall;
import plodsoft.slingshot.items.ItemSlingshot;

public class ModItems {
	public static Item slingshot;
	public static Item stone_ball;
	public static Item metal_ball;
	public static Item ender_ball;

	public static void init() {
		register(slingshot = new ItemSlingshot());
		register(stone_ball = new ItemBall("stone_ball"));
		register(metal_ball = new ItemBall("metal_ball"));
		register(ender_ball = new ItemBall("ender_ball"));
	}

	private static void register(Item item) {
		item.setCreativeTab(CreativeTabs.COMBAT);
		GameRegistry.register(item);
		Slingshot.proxy.registerItemRenderer(item, 0, item.getRegistryName().toString());
	}

	public static void registerRecipe() {
		GameRegistry.addShapedRecipe(new ItemStack(stone_ball, 8),
				"A ",
				" A",
				'A', Item.getItemFromBlock(Blocks.COBBLESTONE));
		GameRegistry.addShapedRecipe(new ItemStack(stone_ball, 8),
				" A",
				"A ",
				'A', Item.getItemFromBlock(Blocks.COBBLESTONE));

		GameRegistry.addShapedRecipe(new ItemStack(metal_ball, 16),
				" A",
				"B ",
				'A', Items.IRON_INGOT,
				'B', Items.GOLD_INGOT);
		GameRegistry.addShapedRecipe(new ItemStack(metal_ball, 16),
				" B",
				"A ",
				'A', Items.IRON_INGOT,
				'B', Items.GOLD_INGOT);
		GameRegistry.addShapedRecipe(new ItemStack(metal_ball, 16),
				"A ",
				" B",
				'A', Items.IRON_INGOT,
				'B', Items.GOLD_INGOT);
		GameRegistry.addShapedRecipe(new ItemStack(metal_ball, 16),
				"B ",
				" A",
				'A', Items.IRON_INGOT,
				'B', Items.GOLD_INGOT);

		GameRegistry.addShapedRecipe(new ItemStack(ender_ball, 16),
				"B ",
				" A",
				'A', Items.DIAMOND,
				'B', Items.ENDER_PEARL);
		GameRegistry.addShapedRecipe(new ItemStack(ender_ball, 16),
				"A ",
				" B",
				'A', Items.DIAMOND,
				'B', Items.ENDER_PEARL);
		GameRegistry.addShapedRecipe(new ItemStack(ender_ball, 16),
				" A",
				"B ",
				'A', Items.DIAMOND,
				'B', Items.ENDER_PEARL);
		GameRegistry.addShapedRecipe(new ItemStack(ender_ball, 16),
				" B",
				"A ",
				'A', Items.DIAMOND,
				'B', Items.ENDER_PEARL);

		GameRegistry.addShapedRecipe(new ItemStack(slingshot),
				"sfs",
				"t t",
				" t ",
				's', Items.STRING,
				'f', Items.LEATHER,
				't', Items.STICK);
	}
}
