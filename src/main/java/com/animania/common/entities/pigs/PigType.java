package com.animania.common.entities.pigs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.animania.common.AnimaniaAchievements;

import net.minecraft.stats.StatBase;
import net.minecraft.world.World;

public enum PigType
{
	DUROC(EntityHogDuroc.class, EntitySowDuroc.class, EntityPigletDuroc.class, AnimaniaAchievements.Duroc),
	HAMPSHIRE(EntityHogHampshire.class, EntitySowHampshire.class, EntityPigletHampshire.class, AnimaniaAchievements.Hampshire),
	LARGE_BLACK(EntityHogLargeBlack.class, EntitySowLargeBlack.class, EntityPigletLargeBlack.class, AnimaniaAchievements.LargeBlack),
	LARGE_WHITE(EntityHogLargeWhite.class, EntitySowLargeWhite.class, EntityPigletLargeWhite.class, AnimaniaAchievements.LargeWhite),
	OLD_SPOT(EntityHogOldSpot.class, EntitySowOldSpot.class, EntityPigletOldSpot.class, AnimaniaAchievements.OldSpot),
	YORKSHIRE(EntityHogYorkshire.class, EntitySowYorkshire.class, EntityPigletYorkshire.class, AnimaniaAchievements.Yorkshire);


	private Class hog;
	private Class sow;
	private Class piglet;
	private StatBase achievement;

	private PigType(Class hog, Class sow, Class piglet, StatBase achievement)
	{
		this.hog = hog;
		this.sow = sow;
		this.piglet = piglet;
		this.achievement = achievement;
	}

	public static Class getMale(PigType type)
	{
		return type.hog;
	}

	public static Class getFemale(PigType type)
	{
		return type.sow;
	}

	public static Class getChild(PigType type)
	{
		return type.piglet;
	}

	public EntityHogBase getMale(World world)
	{
		Constructor<?> constructor = null;
		try
		{
			constructor = this.hog.getConstructor(World.class);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		EntityHogBase bull = null;
		try
		{
			bull = (EntityHogBase) constructor.newInstance(world);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return bull;
	}

	public EntitySowBase getFemale(World world)
	{
		Constructor<?> constructor = null;
		try
		{
			constructor = this.sow.getConstructor(World.class);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		EntitySowBase cow = null;
		try
		{
			cow = (EntitySowBase) constructor.newInstance(world);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return cow;	
	}

	public EntityPigletBase getChild(World world)
	{
		Constructor<?> constructor = null;
		try
		{
			constructor = this.piglet.getConstructor(World.class);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		EntityPigletBase calf = null;
		try
		{
			calf = (EntityPigletBase) constructor.newInstance(world);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return calf;	
	}

	public static PigType breed(PigType male, PigType female)
	{
		Random rand = new Random();
		if(rand.nextInt(2) == 0)
			return male;
		else
			return female;
	}

	public StatBase getAchievement()
	{
		return this.achievement;
	}



}