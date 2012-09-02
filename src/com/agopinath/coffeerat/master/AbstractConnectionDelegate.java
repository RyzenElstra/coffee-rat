package com.agopinath.coffeerat.master;

public class AbstractConnectionDelegate {
	protected CoffeeRatMaster master;
	
	protected void setMaster(CoffeeRatMaster newMaster) {
		this.master = newMaster;
	}
}
