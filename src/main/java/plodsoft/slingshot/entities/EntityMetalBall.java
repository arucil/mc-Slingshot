package plodsoft.slingshot.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import plodsoft.slingshot.ModEntityTypes;
import plodsoft.slingshot.ModItems;

public class EntityMetalBall extends EntityBall {
    public EntityMetalBall(EntityType<? extends EntityMetalBall> entityType, World world) {
        super(entityType, world);
    }

    public EntityMetalBall(World world, LivingEntity thrower) {
        super(ModEntityTypes.METAL_BALL.get(), world, thrower);
    }

    public EntityMetalBall(World world, double x, double y, double z) {
        super(ModEntityTypes.METAL_BALL.get(), world, x, y, z);
    }

    @Override
    protected float getInitialDamage() {
        return 6f;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.METAL_BALL;
    }
}
