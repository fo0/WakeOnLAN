package com.fo0.wol.main;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		WakeOnLAN wol = new WakeOnLAN(args[0], args[1]);
		if (wol.wakeUp()) {
			System.out.println("successful waked up: " + Arrays.toString(args));
		}
	}

}
