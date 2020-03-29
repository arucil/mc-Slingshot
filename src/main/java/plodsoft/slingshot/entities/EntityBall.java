package plodsoft.slingshot.entities;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import plodsoft.slingshot.ModItems;
import plodsoft.slingshot.items.ItemBall;

public abstract class EntityBall extends ProjectileItemEntity {
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

    public EntityBall(EntityType<? extends EntityBall> entityType, World world) {
        super(entityType, world);
    }

    public EntityBall(EntityType<? extends EntityBall> entityType, World world, LivingEntity thrower) {
        super(entityType, thrower, world);
        setDamage(getInitialDamage());
    }

    public EntityBall(EntityType<? extends EntityBall> entityType, World world, double x, double y, double z) {
        super(entityType, x, y, z, world);
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
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            LivingEntity thrower = getThrower();
            Entity entity = ((EntityRayTraceResult) result).getEntity();

            // prevent damaging thrower
            if (entity == thrower)
                return;

            if (thrower != null)
                thrower.setLastAttackedEntity(entity);

            DamageSource ds = createDamageSource(null == thrower ? this : thrower);

            if (isBurning() && !(entity instanceof EndermanEntity))
                entity.setFire(5);

            if (entity.attackEntityFrom(ds, getDamage())) {
                if (entity instanceof LivingEntity) {
                    LivingEntity base = (LivingEntity) entity;

                    if (knockbackStrength > 0) {
                        Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                        if (vec3d.lengthSquared() > 0.0D) {
                            base.addVelocity(vec3d.x, 0.1D, vec3d.z);
                        }
                    }

                    if (!world.isRemote && thrower != null) {
                        EnchantmentHelper.applyThornEnchantments(base, thrower);
                        EnchantmentHelper.applyArthropodEnchantments(thrower, base);
                    }

                    if (base instanceof PlayerEntity && thrower instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)thrower).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
                    }
                }
            }
        }

        if (!this.world.isRemote)
            remove();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static EntityBall createEntity(World world, LivingEntity thrower, ItemBall item) {
        EntityBall ball;
        if (item == ModItems.STONE_BALL)
            ball = new EntityStoneBall(world, thrower);
        else if (item == ModItems.METAL_BALL)
            ball = new EntityMetalBall(world, thrower);
        else
            ball = new EntityEnderBall(world, thrower);

        return ball;
    }
}
