package plodsoft.slingshot.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import plodsoft.slingshot.ModEntityTypes;
import plodsoft.slingshot.ModItems;

public class EntityStoneBall extends EntityBall {
    public EntityStoneBall(EntityType<? extends EntityStoneBall> entityType, World world) {
        super(entityType, world);
    }

    public EntityStoneBall(World world, LivingEntity thrower) {
        super(ModEntityTypes.STONE_BALL.get(), world, thrower);
    }

    public EntityStoneBall(World world, double x, double y, double z) {
        super(ModEntityTypes.STONE_BALL.get(), world, x, y, z);
    }

    @Override
    protected float getInitialDamage() {
        return 2f;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.STONE_BALL;
    }
}
