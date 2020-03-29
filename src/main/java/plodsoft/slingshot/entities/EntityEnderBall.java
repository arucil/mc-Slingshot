package plodsoft.slingshot.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import plodsoft.slingshot.ModEntityTypes;
import plodsoft.slingshot.ModItems;

public class EntityEnderBall extends EntityBall {
    public EntityEnderBall(EntityType<? extends EntityEnderBall> entityType, World world) {
        super(entityType, world);
    }

    public EntityEnderBall(World world, LivingEntity thrower) {
        super(ModEntityTypes.ENDER_BALL.get(), world, thrower);
    }

    public EntityEnderBall(World world, double x, double y, double z) {
        super(ModEntityTypes.ENDER_BALL.get(), world, x, y, z);
    }

    @Override
    protected float getInitialDamage() {
        return 10f;
    }

    @Override
    protected DamageSource createDamageSource(Entity thrower) {
        return new DamageSourceEnderBall(this, thrower);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ENDER_BALL;
    }
}
