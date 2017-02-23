package plodsoft.slingshot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import plodsoft.slingshot.entities.*;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta,
			new ModelResourceLocation(id, "inventory"));
	}

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityStoneBall.class,
				m -> new RenderSnowball(m, ModItems.stone_ball,
				Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMetalBall.class,
				m -> new RenderSnowball(m, ModItems.metal_ball,
				Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderBall.class,
				m -> new RenderSnowball(m, ModItems.ender_ball,
				Minecraft.getMinecraft().getRenderItem()));
	}
}
