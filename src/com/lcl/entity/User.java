package com.lcl.entity;

public class User {
	private long id;
	private String username;
	private String password;
	private String phone;
	private String email;
	private String created;
	private String updated;
	
	
	public User() {
		super();
	}
	public User(long id, String name, String password, String phone, String email, String created, String updated) {
		super();
		this.id = id;
		this.username = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.created = created;
		this.updated = updated;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", password=" + password + ", phone=" + phone + ", email=" + email
				+ ", created=" + created + ", updated=" + updated + "]";
	}

}
