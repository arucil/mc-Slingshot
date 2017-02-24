package plodsoft.slingshot.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;

// copy of class EntityDamageSourceIndirect
public class DamageSourceEnderBall extends EntityDamageSource {
	private final Entity indirectEntity;

	public DamageSourceEnderBall(Entity source, @Nullable Entity indirectEntity)
	{
		super("enderBall", source);
		this.indirectEntity = indirectEntity;
		setProjectile(); // from DamageSource.causeThrownDamage
	}

	@Override
	@Nullable
	public Entity getSourceOfDamage()
	{
		return this.damageSourceEntity;
	}

	@Override
	@Nullable
	public Entity getEntity()
	{
		return this.indirectEntity;
	}

	/**
	 * Gets the death message that is displayed when the player dies
	 */
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
	{
		ITextComponent itextcomponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
		ItemStack itemstack = this.indirectEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.indirectEntity).getHeldItemMainhand() : null;
		String s = "death.attack." + this.damageType;
		String s1 = s + ".item";
		return itemstack != null && itemstack.hasDisplayName() && I18n.canTranslate(s1) ? new TextComponentTranslation(s1, new Object[] {entityLivingBaseIn.getDisplayName(), itextcomponent, itemstack.getTextComponent()}): new TextComponentTranslation(s, new Object[] {entityLivingBaseIn.getDisplayName(), itextcomponent});
	}
}
