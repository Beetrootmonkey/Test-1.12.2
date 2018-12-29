package com.beetrootmonkey.sfm.main;

import com.beetrootmonkey.sfm.client.ModTab;
import com.beetrootmonkey.sfm.proxy.CommonProxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MOD_ID, useMetadata = true)
public class Main
{
    public static final String MOD_ID = "sfm";
    
    public static final ModTab creativeTab = new ModTab();

	public static final Logger LOG = LogManager.getLogger(MOD_ID);
	
	@Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide="com.beetrootmonkey.sfm.proxy.ClientOnlyProxy", serverSide="com.beetrootmonkey.sfm.proxy.DedicatedServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
      proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
      proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
      proxy.postInit();
    }
}
