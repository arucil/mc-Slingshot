package plodsoft.slingshot;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Slingshot.MODID,
	name = Slingshot.NAME,
	version = Slingshot.VERSION,
	acceptedMinecraftVersions = "[1.10.2]")
public class Slingshot {
	public static final String MODID = "slingshot";
	public static final String VERSION = "1.0";
	public static final String NAME = "Slingshot";

	@SidedProxy(serverSide = "plodsoft.slingshot.CommonProxy",
		clientSide = "plodsoft.slingshot.ClientProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		ModItems.init();
		proxy.registerEntities(this);
		proxy.registerRenderers();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		ModItems.registerRecipe();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}
}
