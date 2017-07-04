package com.animania;

import com.animania.common.creativeTab.TabAnimaniaEntities;
import com.animania.common.creativeTab.TabAnimaniaResources;
import com.animania.proxy.CommonProxy;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
@Mod(modid = Animania.MODID, name = Animania.NAME, version = Animania.VERSION, guiFactory = "com.animania.client.gui.GuiFactoryAnimania", dependencies = "required-after:craftstudioapi;after:quark;required-after:forge@[13.20.1.2386,)")
public class Animania
{

    @SidedProxy(clientSide = "com.animania.proxy.ClientProxy", serverSide = "com.animania.proxy.ServerProxy")
    public static CommonProxy  proxy;

    // Instance
    @Instance(Animania.MODID)
    public static Animania     instance;

    public static final String MODID                = "animania";
    public static final String VERSION              = "1.2";
    public static final String NAME                 = "Animania";

	public static SimpleNetworkWrapper network;

    
    // Tabs
    public static CreativeTabs TabAnimaniaEggs      = new TabAnimaniaEntities(CreativeTabs.getNextID(), "Animania");
    public static CreativeTabs TabAnimaniaResources = new TabAnimaniaResources(CreativeTabs.getNextID(), "Animania");

    @EventHandler
    public void construction(FMLConstructionEvent event)
    {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Animania.proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Animania.proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {}

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(RegistryEvent.Register<CSReadedModel> e)
    {
        proxy.registerCraftStudioModels();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerAnimations(RegistryEvent.Register<CSReadedAnim> e)
    {
        proxy.registerCraftStudioAnimations();
    }
}