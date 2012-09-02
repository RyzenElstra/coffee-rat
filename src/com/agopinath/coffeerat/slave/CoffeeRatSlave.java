package com.agopinath.coffeerat.slave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.agopinath.coffeerat.common.ConnectionSettings;
import com.agopinath.lthelogutil.L;

public class CoffeeRatSlave {
	private Socket hostConn;
	
	public static void main(String args[]) {
		CoffeeRatSlave slave = new CoffeeRatSlave();
		
		slave.initSlave();
		slave.startSlave();
	}

	private void initSlave() {
		hostConn = new Socket();
	}	

	private void startSlave() {
		try {
			hostConn.connect(new InetSocketAddress(ConnectionSettings.HOST, ConnectionSettings.HOST_PORT));
			L.dbg("Slave connected to " + ConnectionSettings.HOST + " : " + ConnectionSettings.HOST_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader serverReader = null;
		try {
			serverReader = new BufferedReader(new InputStreamReader(hostConn.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String incoming = "first null message";
		
		try {
			while((incoming = serverReader.readLine()) != null) {
				L.dbg("Got from server: " + incoming);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
