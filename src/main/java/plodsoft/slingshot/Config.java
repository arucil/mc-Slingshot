package plodsoft.slingshot;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
	private static Configuration config;

	public static float stoneballDamage;
	public static float metalballDamage;
	public static float enderballDamage;

	public static void init(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();

		stoneballDamage = config.getFloat("stoneballDamage", Configuration.CATEGORY_GENERAL,
				2f, 0f, 4096f, "damage of stone ball");
		metalballDamage = config.getFloat("metalballDamage", Configuration.CATEGORY_GENERAL,
				6f, 0f, 4096f, "damage of metal ball");
		enderballDamage = config.getFloat("enderballDamage", Configuration.CATEGORY_GENERAL,
				10f, 0f, 4096f, "damage of ender ball");

		if (config.hasChanged())
			config.save();
	}
}
