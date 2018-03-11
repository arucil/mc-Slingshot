package plodsoft.slingshot.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import plodsoft.slingshot.ModItems;
import plodsoft.slingshot.Slingshot;
import plodsoft.slingshot.entities.*;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta,
			new ModelResourceLocation(id, "inventory"));
	}

	@Override
    public void preInit() {
	    super.preInit();
	    registerEntities();
	    registerRenderers();
    }

	private static void registerRenderers() {
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

	private static void registerEntities() {
        EntityRegistry.registerModEntity(ModItems.stone_ball.getRegistryName(), EntityStoneBall.class,
                "StoneBall", 1, Slingshot.instance, 128, 1, true);
        EntityRegistry.registerModEntity(ModItems.metal_ball.getRegistryName(), EntityMetalBall.class,
                "MetalBall", 2, Slingshot.instance, 128, 1, true);
        EntityRegistry.registerModEntity(ModItems.ender_ball.getRegistryName(), EntityEnderBall.class,
                "EnderBall", 3, Slingshot.instance, 128, 1, true);
    }
}
