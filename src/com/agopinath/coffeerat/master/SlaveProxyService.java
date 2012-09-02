package com.agopinath.coffeerat.master;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.agopinath.coffeerat.common.ConnectionSettings;
import com.agopinath.lthelogutil.L;

public class SlaveProxyService extends AbstractConnectionDelegate implements Runnable {
	private ServerSocket masterSock;
	private ArrayList<SlaveDelegate> slaves;
	
	public SlaveProxyService() {
		try {
			masterSock = new ServerSocket(ConnectionSettings.HOST_PORT);
			L.dbg("Starting master on " + ConnectionSettings.HOST_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		slaves = new ArrayList<SlaveDelegate>();
	}
	
	@Override
	public void run() {
		while(true) {
			Socket newSlaveSock = null;
			
			BufferedReader slaveSockReader = null;
			BufferedWriter slaveSockWriter = null;
			
			try {
				newSlaveSock = masterSock.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				slaveSockReader = new BufferedReader(new InputStreamReader(newSlaveSock.getInputStream()));
				slaveSockWriter = new BufferedWriter(new OutputStreamWriter(newSlaveSock.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			SlaveDelegate newSlave = new SlaveDelegate(newSlaveSock, slaveSockReader, slaveSockWriter);
			
			L.dbg("Delegated slave successfully to socket " + newSlaveSock.getRemoteSocketAddress());
			slaves.add(newSlave);
		}
	}

	public void writeAll(String message) {
		try {
			for(SlaveDelegate currSlave: slaves) {
				currSlave.writeToSlave(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
