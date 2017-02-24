package plodsoft.slingshot.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import plodsoft.slingshot.Config;
import plodsoft.slingshot.misc.DamageSourceEnderBall;

public class EntityEnderBall extends EntityBall {
	public EntityEnderBall(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	public EntityEnderBall(World world) {
		super(world);
	}

	public EntityEnderBall(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected float getInitialDamage() {
		return Config.enderballDamage;
	}

	@Override
	protected DamageSource createDamageSource(Entity thrower) {
		return new DamageSourceEnderBall(this, thrower);
	}
}
