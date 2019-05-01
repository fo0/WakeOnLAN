package com.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import lombok.NonNull;

public class WakeOnLAN {

	public static final int PORT = 9;

	private final String broadcastAddress;
	private final String MAC;

	public WakeOnLAN(@NonNull String broadcastAddress, @NonNull String mAC) {
		this.broadcastAddress = broadcastAddress;
		MAC = mAC.toLowerCase();
	}

	public boolean wakeUp() {
		byte[] macBytes = getMacBytes(MAC);
		byte[] bytes = new byte[6 + 16 * macBytes.length];

		for (int i = 0; i < 6; i++) {
			bytes[i] = (byte) 0xff;
		}

		for (int i = 6; i < bytes.length; i += macBytes.length) {
			System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
		}

		try {
			InetAddress address = InetAddress.getByName(broadcastAddress);
			DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private byte[] getMacBytes(String macStr) throws IllegalArgumentException {
		byte[] bytes = new byte[6];
		String[] hex = macStr.split("(\\:|\\-)");
		if (hex.length != 6) {
			throw new IllegalArgumentException("Invalid MAC address.");
		}

		try {
			for (int i = 0; i < 6; i++) {
				bytes[i] = (byte) Integer.parseInt(hex[i], 16);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid hex digit in MAC address.");
		}

		return bytes;
	}

}
