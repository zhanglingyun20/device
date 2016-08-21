package com.device.common;

import java.util.Date;

public class RunState {

	private String stateValue;
	private Date time;
	public String getStateValue() {
		return stateValue;
	}
	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public RunState(String stateValue, Date time) {
		super();
		this.stateValue = stateValue;
		this.time = time;
	}
	public RunState() {
		super();
	}
}
