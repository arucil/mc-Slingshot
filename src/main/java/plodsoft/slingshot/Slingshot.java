package plodsoft.slingshot;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Slingshot.MODID)
public class Slingshot {
    public static final String MODID = "slingshot";

    public static Logger logger = LogManager.getLogger(MODID);

    public Slingshot() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Deferred Registers (Does not need to be before Configs)
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
    }
}
