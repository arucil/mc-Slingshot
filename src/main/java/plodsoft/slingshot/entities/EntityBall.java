package plodsoft.slingshot.entities;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import plodsoft.slingshot.ModItems;
import plodsoft.slingshot.items.ItemBall;

public abstract class EntityBall extends EntityThrowable {
	private float damage;
	private int knockbackStrength;

	protected abstract float getInitialDamage();

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public void addDamage(float d) {
		damage += d;
	}

	public float getDamage() {
		return damage;
	}

	public void setKnockbackStrength(int knockbackStrength) {
		this.knockbackStrength = knockbackStrength;
	}

	public EntityBall(World world) {
		super(world);
		setDamage(getInitialDamage());
	}

	public EntityBall(World world, EntityLivingBase thrower) {
		super(world, thrower);
		setDamage(getInitialDamage());
	}

	public EntityBall(World world, double x, double y, double z) {
		super(world, x, y, z);
		setDamage(getInitialDamage());
	}

	// velocity not affected by water
	@Override
	public boolean isInWater() {
      return false;
   }

	@Override
	protected float getGravityVelocity() {
		return 0f;
	}

	protected DamageSource createDamageSource(Entity thrower) {
		return DamageSource.causeThrownDamage(this, thrower);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
			EntityLivingBase thrower = getThrower();
			// prevent damaging thrower
			if (result.entityHit == thrower)
				return;

			Entity entity = result.entityHit;
			DamageSource ds = createDamageSource(null == thrower ? this : thrower);

			if (isBurning() && !(entity instanceof EntityEnderman))
				entity.setFire(5);
			if (entity.attackEntityFrom(ds, getDamage())) {
				if (entity instanceof EntityLivingBase) {
					EntityLivingBase base = (EntityLivingBase) entity;

					if (knockbackStrength > 0) {
						float f1 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
						if (f1 > 0f) {
							base.addVelocity(motionX * knockbackStrength * 0.6000000238418579D / f1,
								0.1D, motionZ * knockbackStrength * 0.6000000238418579D / f1);
						}
					}

					if (null != thrower) {
						EnchantmentHelper.applyThornEnchantments(base, thrower);
						EnchantmentHelper.applyArthropodEnchantments(thrower, base);
						if (base != thrower && base instanceof EntityPlayer && thrower instanceof EntityPlayerMP)
						{
							((EntityPlayerMP)thrower).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
						}
					}
				}
			}
		}
		if (!this.world.isRemote)
         setDead();
	}

	public static EntityBall createEntity(World world, EntityLivingBase thrower, ItemBall item) {
		EntityBall ball;
		if (item == ModItems.stone_ball)
			ball = new EntityStoneBall(world, thrower);
		else if (item == ModItems.metal_ball)
			ball = new EntityMetalBall(world, thrower);
		else
			ball = new EntityEnderBall(world, thrower);

		return ball;
	}
}
