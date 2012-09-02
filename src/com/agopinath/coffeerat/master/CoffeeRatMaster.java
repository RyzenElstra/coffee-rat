package com.agopinath.coffeerat.master;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CoffeeRatMaster {
	private JFrame frame;
	private JTextField commandField;
	private JButton commandSendButton;
	private JPanel ratPanel;
	
	private SlaveProxyService slaveProxy;
	
	public static void main(String args[]) {
		CoffeeRatMaster master = new CoffeeRatMaster();
		
		master.initMaster();
	}

	private void initMaster() {
		setupGui();
		setupNetworking();
	}
	
	private void setupGui() {
		frame = new JFrame("Coffee Rat");
		ratPanel = new JPanel();
		commandField = new JTextField(50);
		commandSendButton = new JButton("Send command");
		
		commandSendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slaveProxy.writeAll(commandField.getText());
			}
		});
		
		ratPanel.add(commandField);
		ratPanel.add(commandSendButton);
		
		frame.add(ratPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	private void setupNetworking() {
		slaveProxy = new SlaveProxyService();
		slaveProxy.setMaster(this);
		
		Thread acceptSlaveThread = new Thread(slaveProxy);
		acceptSlaveThread.start();
	}
}
