package plodsoft.slingshot;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import plodsoft.slingshot.entities.*;

public class CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	public void registerRenderers() {}

	public void registerEntities(Slingshot mod) {
		EntityRegistry.registerModEntity(EntityStoneBall.class, "StoneBall", 1, mod, 128, 1, true);
		EntityRegistry.registerModEntity(EntityMetalBall.class, "MetalBall", 2, mod, 128, 1, true);
		EntityRegistry.registerModEntity(EntityEnderBall.class, "EnderBall", 5, mod, 128, 1, true);
	}

}
