package com.ssh.entities;

public class Restrition {
	private String name;
	private String restritionName;
	private String restritionValue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRestritionName() {
		return restritionName;
	}
	public void setRestritionName(String restritionName) {
		this.restritionName = restritionName;
	}
	public String getRestritionValue() {
		return restritionValue;
	}
	public void setRestritionValue(String restritionValue) {
		this.restritionValue = restritionValue;
	}
	@Override
	public String toString() {
		return "Restrition [name: " + name + ", restritionName: "
				+ restritionName + ", restritionValue: " + restritionValue + "]";
	}
	@Override
	public boolean equals(Object obj) {
		Restrition c = (Restrition)obj;
		return this.restritionName.equals(c.getRestritionName());
	}
	
}

