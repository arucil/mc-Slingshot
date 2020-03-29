package plodsoft.slingshot;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import plodsoft.slingshot.items.ItemBall;
import plodsoft.slingshot.items.ItemSlingshot;

@ObjectHolder(Slingshot.MODID)
public class ModItems {
    public static final Item SLINGSHOT = null;
    public static final Item STONE_BALL = null;
    public static final Item METAL_BALL = null;
    public static final Item ENDER_BALL = null;

    public static void register(IForgeRegistry<Item> registry) {
        registry.register(new ItemSlingshot());
        registry.register(new ItemBall("stone_ball"));
        registry.register(new ItemBall("metal_ball"));
        registry.register(new ItemBall("ender_ball"));
    }
}
