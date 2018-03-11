package plodsoft.slingshot.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import plodsoft.slingshot.Slingshot;
import plodsoft.slingshot.events.EventHandlerPlayerLogin;
import plodsoft.slingshot.network.MessageHandlerSyncConfig;
import plodsoft.slingshot.network.MessageSyncConfig;

@Mod.EventBusSubscriber
public class CommonProxy {
	public SimpleNetworkWrapper networkWrapper;

	public void registerItemRenderer(Item item, int meta, String id) {
	}


	public void preInit() {
        registerNetwork();
	}

	public void init() {
		MinecraftForge.EVENT_BUS.register(new EventHandlerPlayerLogin());
	}

	private void registerNetwork() {
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Slingshot.MODID);
		networkWrapper.registerMessage(MessageHandlerSyncConfig.class,
				MessageSyncConfig.class, 1, Side.CLIENT);
	}

}
