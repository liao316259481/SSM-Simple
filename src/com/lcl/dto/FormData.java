package com.lcl.dto;

public class FormData {
	private boolean status;
	private Object info;
	
	
	public FormData() {
		super();
	}
	public FormData(boolean status, Object info) {
		super();
		this.status = status;
		this.info = info;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Object getInfo() {
		return info;
	}
	public void setInfo(Object info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "FormData [status=" + status + ", info=" + info + "]";
	}
	
}
