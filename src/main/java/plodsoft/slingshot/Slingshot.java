package plodsoft.slingshot;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plodsoft.slingshot.proxy.CommonProxy;

@Mod(modid = Slingshot.MODID,
	name = Slingshot.NAME,
	version = Slingshot.VERSION,
    useMetadata = true)
public class Slingshot {
	public static final String MODID = "slingshot";
	public static final String VERSION = "1.0";
	public static final String NAME = "Slingshot";

	@SidedProxy(serverSide = "plodsoft.slingshot.proxy.CommonProxy",
		clientSide = "plodsoft.slingshot.proxy.ClientProxy")
	public static CommonProxy proxy;

	@Mod.Instance(MODID)
	public static Slingshot instance;

	public static Logger log = LogManager.getLogger(NAME);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Config.init(e);
		// ModItems.init();
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		// ModItems.registerRecipe();
		proxy.init();
	}
}
