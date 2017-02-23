package plodsoft.slingshot.items;

import net.minecraft.item.Item;

public class ItemBall extends Item {
	public final String Name;

	public ItemBall(String name) {
		Name = name;
		setRegistryName(name);
		setUnlocalizedName(name);
	}
}
