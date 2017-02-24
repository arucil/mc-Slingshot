package plodsoft.slingshot.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import plodsoft.slingshot.Slingshot;
import plodsoft.slingshot.entities.*;
import plodsoft.slingshot.events.EventHandlerPlayerLogin;
import plodsoft.slingshot.network.MessageHandlerSyncConfig;
import plodsoft.slingshot.network.MessageSyncConfig;

public class CommonProxy {
	public SimpleNetworkWrapper networkWrapper;

	public void registerItemRenderer(Item item, int meta, String id) {
	}

	public void registerRenderers() {}

	public void registerEntities() {
		int i = 0;
		EntityRegistry.registerModEntity(EntityStoneBall.class, "StoneBall",
				++i, Slingshot.instance, 128, 1, true);
		EntityRegistry.registerModEntity(EntityMetalBall.class, "MetalBall",
				++i, Slingshot.instance, 128, 1, true);
		EntityRegistry.registerModEntity(EntityEnderBall.class, "EnderBall",
				++i, Slingshot.instance, 128, 1, true);
	}

	public void preInit() {
		registerEntities();
		registerRenderers();
	}

	public void init() {
		MinecraftForge.EVENT_BUS.register(new EventHandlerPlayerLogin());
		registerNetwork();
	}

	private void registerNetwork() {
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Slingshot.MODID);
		networkWrapper.registerMessage(MessageHandlerSyncConfig.class,
				MessageSyncConfig.class, 1, Side.CLIENT);
	}
}
