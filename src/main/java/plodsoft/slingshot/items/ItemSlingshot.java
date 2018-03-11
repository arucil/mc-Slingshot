package plodsoft.slingshot.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import plodsoft.slingshot.ModItems;
import plodsoft.slingshot.entities.EntityBall;

import javax.annotation.Nullable;

public class ItemSlingshot extends ItemBow {
	public static final String NAME = "slingshot";

	public ItemSlingshot() {
		setUnlocalizedName(NAME);
		setRegistryName(NAME);
		setMaxDamage(10000);
	}

   private ItemStack findBall(EntityPlayer player)
	{
		if (this.isBall(player.getHeldItem(EnumHand.OFF_HAND)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
        else if (this.isBall(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isBall(itemstack))
				{
					return itemstack;
				}
			}

			return null;
		}
	}

	private boolean isBall(ItemStack stack) {
		return stack.getItem() instanceof ItemBall;
	}

	@Override
	public void onPlayerStoppedUsing(@Nullable ItemStack stack, @Nullable World world, EntityLivingBase player, int time) {}

	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nullable World world,
			EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
		ItemStack ball = findBall(player);
		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		if (flag || ball != null) {
			if (null == ball)
				ball = new ItemStack(ModItems.stone_ball);
			// consume ball
			if (!flag) {
			    ball.shrink(1);
			    if (ball.isEmpty())
			        player.inventory.deleteStack(ball);
            }
			world.playSound(null, player.posX, player.posY, player.posZ,
					SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,
					.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote) {
				EntityBall entity = EntityBall.createEntity(world, player, (ItemBall) ball.getItem());

				entity.shoot(player, player.rotationPitch, player.rotationYaw, 0f, 2.5f, 1f);

				int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				if (k > 0)
					entity.addDamage(k * .5F + .5F);

				k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
				if (k > 0)
					entity.setKnockbackStrength(k);

				k = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack);
				if (k > 0)
					entity.setFire(100);

				world.spawnEntity(entity);
			}
			player.addStat(StatList.getObjectUseStats(this));
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.NONE;
	}
}
