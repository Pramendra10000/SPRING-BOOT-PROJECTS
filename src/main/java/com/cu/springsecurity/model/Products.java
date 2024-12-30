package com.cu.springsecurity.model;

public class Products {

	
	private int id;
	private String name;
	private String desc;
	
	
	
	
	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Products(int id, String name, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
	}




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", desc=" + desc + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getDesc()=" + getDesc() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
