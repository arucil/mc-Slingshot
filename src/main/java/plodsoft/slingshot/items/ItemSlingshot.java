package plodsoft.slingshot.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;
import plodsoft.slingshot.ModItems;
import plodsoft.slingshot.entities.EntityBall;

import javax.annotation.Nullable;

public class ItemSlingshot extends BowItem {
    public ItemSlingshot() {
        super(new Item.Properties()
                .maxDamage(10000)
                .group(ItemGroup.COMBAT));
        setRegistryName("slingshot");
    }

    private ItemStack findBall(PlayerEntity player)
    {
        if (this.isBall(player.getHeldItem(Hand.OFF_HAND)))
        {
            return player.getHeldItem(Hand.OFF_HAND);
        }
        else if (this.isBall(player.getHeldItem(Hand.MAIN_HAND)))
        {
            return player.getHeldItem(Hand.MAIN_HAND);
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

            return ItemStack.EMPTY;
        }
    }

    private boolean isBall(ItemStack stack) {
        return stack.getItem() instanceof ItemBall;
    }

    @Override
    public void onPlayerStoppedUsing(@Nullable ItemStack stack, @Nullable World world, LivingEntity player, int time) {}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack ball = findBall(player);

        boolean flag = player.abilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        if (flag || !ball.isEmpty()) {
            if (ball.isEmpty())
                ball = new ItemStack(ModItems.STONE_BALL);

            // consume ball
            if (!flag) {
                ball.shrink(1);
                if (ball.isEmpty())
                    player.inventory.deleteStack(ball);
            }

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

                world.addEntity(entity);

            }

            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS,
                    .5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

            player.addStat(Stats.ITEM_USED.get(this));

            return ActionResult.resultConsume(stack);
        }
        return ActionResult.resultFail(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }
}
