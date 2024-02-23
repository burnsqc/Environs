package com.environs.client.multiplayer;

public class ClientLevelDataExtension {
	private static int moonType;

	public static int getMoon() {
		return moonType;
	}

	public void setMoon(int moonTypeIn) {
		moonType = moonTypeIn;
	}
}
