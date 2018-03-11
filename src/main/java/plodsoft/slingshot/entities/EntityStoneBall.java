package plodsoft.slingshot.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import plodsoft.slingshot.Config;

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
		return Config.stoneballDamage;
	}

    public static void registerFixesRock(DataFixer fixer) {
        EntityThrowable.registerFixesThrowable(fixer, "StoneBall");
    }
}
