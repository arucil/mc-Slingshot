package plodsoft.slingshot.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityStoneBall extends EntityBall {
	public EntityStoneBall(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	public EntityStoneBall(World world) {
		super(world);
	}

	public EntityStoneBall(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected float getInitialDamage() {
		return 2f;
	}
}
