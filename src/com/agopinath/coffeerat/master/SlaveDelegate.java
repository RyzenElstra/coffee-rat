package com.agopinath.coffeerat.master;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

import com.agopinath.lthelogutil.L;

public class SlaveDelegate {
	private final Socket slaveSocket;
	private final BufferedReader slaveReader;
	private final BufferedWriter slaveWriter;
	
	public SlaveDelegate(final Socket slaveSocket, final BufferedReader slaveReader, final BufferedWriter slaveWriter) {
		this.slaveSocket = slaveSocket;
		this.slaveReader = slaveReader;
		this.slaveWriter = slaveWriter;
	}
	
	public void writeToSlave(String message) throws IOException {
		L.dbg("Writing message \"" + message + "\" to... " + slaveSocket);
		slaveWriter.write(message + "\r\n");
		slaveWriter.flush();
	}
	
	public String toString() {
		return slaveSocket.toString();
	}
}
