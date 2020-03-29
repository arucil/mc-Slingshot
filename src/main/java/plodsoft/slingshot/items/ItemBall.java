package plodsoft.slingshot.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBall extends Item {
    public ItemBall(String name) {
        super(new Item.Properties()
                .group(ItemGroup.COMBAT));
        setRegistryName(name);
    }
}
