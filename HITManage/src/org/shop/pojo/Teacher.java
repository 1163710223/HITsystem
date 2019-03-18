package org.shop.pojo;

public class Teacher {
    String id;
    String password;
    String name;
    String phonenum;
    
	public Teacher(String id, String password, String name, String phonenum) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.phonenum = phonenum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", password=" + password + ", name=" + name + ", phonenum=" + phonenum + "]";
	}
    
}
