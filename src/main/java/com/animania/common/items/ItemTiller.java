package com.animania.common.items;

import java.util.Random;

import com.animania.Animania;
import com.animania.common.ModSoundEvents;
import com.animania.common.entities.props.EntityTiller;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemTiller extends Item
{
	public ItemTiller()
	{
		this.maxStackSize = 1;
		this.setCreativeTab(Animania.TabAnimaniaResources);
		this.setRegistryName(Animania.MODID, "item_tiller");
		this.setUnlocalizedName("tiller");
		ForgeRegistries.ITEMS.register(this);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		pos = pos.offset(facing);

		ItemStack stack = playerIn.getHeldItem(hand);

		if (world.isRemote)
			return EnumActionResult.SUCCESS;

		EntityTiller entity = new EntityTiller(world);



		entity.setLocationAndAngles(pos.getX() + .5, pos.getY(), pos.getZ() + .5, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);

		if (stack.hasDisplayName())
			((EntityTiller) entity).setCustomNameTag(stack.getDisplayName());

		if (!playerIn.isCreative())
			stack.shrink(1);

		Random rand = new Random();
		world.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, ModSoundEvents.combo, SoundCategory.PLAYERS, 0.8F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
		entity.rotationYaw = entity.rotationYaw;
		entity.deltaRotation = entity.rotationYaw;
		world.spawnEntity(entity);
		return EnumActionResult.SUCCESS;

	}

}