package org.shop.pojo;


public class Student {
	private String id;
	private String password;
	private String name;
	private int year;
	private String phonenum;


	public Student(String id, String password, String name, int year,
			String phonenum) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.year = year;
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



	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}


}
