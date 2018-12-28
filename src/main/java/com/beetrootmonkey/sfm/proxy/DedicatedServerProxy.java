package com.beetrootmonkey.sfm.proxy;

public class DedicatedServerProxy extends CommonProxy {

	public void preInit() {
		super.preInit();
	}

	public void init() {
		super.init();
	}

	public void postInit() {
		super.postInit();
	}

	@Override
	public boolean isDedicatedServer() {
		return true;
	}
}
