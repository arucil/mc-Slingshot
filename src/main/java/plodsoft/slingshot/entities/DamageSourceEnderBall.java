package plodsoft.slingshot.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class DamageSourceEnderBall extends EntityDamageSource {
    private final Entity indirectEntity;

    public DamageSourceEnderBall(Entity source, @Nullable Entity indirectEntityIn) {
        super("ender_ball", source);
        this.indirectEntity = indirectEntityIn;
    }

    /**
     * Retrieves the immediate causer of the damage, e.g. the arrow entity, not its shooter
     */
    @Nullable
    public Entity getImmediateSource() {
        return this.damageSourceEntity;
    }

    /**
     * Retrieves the true causer of the damage, e.g. the player who fired an arrow, the shulker who fired the bullet,
     * etc.
     */
    @Nullable
    public Entity getTrueSource() {
        return this.indirectEntity;
    }

    /**
     * Gets the death message that is displayed when the player dies
     */
    @Override
    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        ITextComponent itextcomponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
        ItemStack itemstack = this.indirectEntity instanceof LivingEntity ? ((LivingEntity)this.indirectEntity).getHeldItemMainhand() : ItemStack.EMPTY;
        String s = "death.attack." + this.damageType;
        String s1 = s + ".item";
        return !itemstack.isEmpty() && itemstack.hasDisplayName() ? new TranslationTextComponent(s1, entityLivingBaseIn.getDisplayName(), itextcomponent, itemstack.getTextComponent()) : new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(), itextcomponent);
    }
}
