package plodsoft.slingshot.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import plodsoft.slingshot.Config;

public class EntityMetalBall extends EntityBall {
	public EntityMetalBall(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	public EntityMetalBall(World world) {
		super(world);
	}

	public EntityMetalBall(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected float getInitialDamage() {
		return Config.metalballDamage;
	}

    public static void registerFixesRock(DataFixer fixer) {
        EntityThrowable.registerFixesThrowable(fixer, "MetalBall");
    }
}
