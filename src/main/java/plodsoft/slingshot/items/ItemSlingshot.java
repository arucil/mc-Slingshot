package plodsoft.slingshot.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import plodsoft.slingshot.ModItems;
import plodsoft.slingshot.entities.EntityBall;

public class ItemSlingshot extends ItemBow {
	public static final String NAME = "slingshot";

	public ItemSlingshot() {
		setUnlocalizedName(NAME);
		setRegistryName(NAME);
		setMaxDamage(-1);
	}

	@Override
	public boolean isDamageable() {
		return true;
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
		return stack != null && stack.getItem() instanceof ItemBall;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int time) {}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world,
			EntityPlayer player, EnumHand hand) {
		ItemStack ball = findBall(player);
		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		if (flag || ball != null) {
			if (null == ball)
				ball = new ItemStack(ModItems.stone_ball);
			// consume ball
			if (!flag && 0 == --ball.stackSize)
					player.inventory.deleteStack(ball);
			world.playSound(null, player.posX, player.posY, player.posZ,
					SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,
					.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote) {
				EntityBall entity = EntityBall.createEntity(world, player, (ItemBall) ball.getItem());

				float f1 = MathHelper.cos(player.rotationPitch * 0.017453292F);
				float x = -MathHelper.sin(player.rotationYaw * 0.017453292F) * f1;
				float y = -MathHelper.sin(player.rotationPitch * 0.017453292F);
				float z = MathHelper.cos(player.rotationYaw * 0.017453292F) * f1;
				entity.setThrowableHeading(x, y, z, 2.5f, 1f);

				int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				if (k > 0)
					entity.addDamage(k * .5F + .5F);

				k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
				if (k > 0)
					entity.setKnockbackStrength(k);

				k = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack);
				if (k > 0)
					entity.setFire(100);

				world.spawnEntityInWorld(entity);
			}
			player.addStat(StatList.getObjectUseStats(this));
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return -1;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.NONE;
	}
}
