package plodsoft.slingshot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import plodsoft.slingshot.items.ItemBall;
import plodsoft.slingshot.items.ItemSlingshot;

public class ModItems {
    @GameRegistry.ObjectHolder("slingshot:slingshot")
	public static Item slingshot;
	public static Item stone_ball;
	public static Item metal_ball;
	public static Item ender_ball;

	static {
		slingshot = new ItemSlingshot().setCreativeTab(CreativeTabs.COMBAT);
		stone_ball = new ItemBall("stone_ball").setCreativeTab(CreativeTabs.COMBAT);
		metal_ball = new ItemBall("metal_ball").setCreativeTab(CreativeTabs.COMBAT);
		ender_ball = new ItemBall("ender_ball").setCreativeTab(CreativeTabs.COMBAT);
	}

	public static void register(IForgeRegistry<Item> registry) {
	    registry.registerAll(slingshot, stone_ball, metal_ball, ender_ball);
    }

	public static void registerModels() {
	    registerModel(slingshot);
	    registerModel(stone_ball);
        registerModel(metal_ball);
        registerModel(ender_ball);
    }

    private static void registerModel(Item item) {
        Slingshot.proxy.registerItemRenderer(item, 0, item.getRegistryName().toString());
    }

}
