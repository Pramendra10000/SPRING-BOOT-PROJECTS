package com.cu.chat.model;



public class userdetails {

    private Long id;

    private String fullName;
    private String mobile;
    private String city;
    private String email;
    private String password;
    
    
    
    
    
	public userdetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	public userdetails(Long id, String fullName, String mobile, String city, String email, String password) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.mobile = mobile;
		this.city = city;
		this.email = email;
		this.password = password;
	}





	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "userdetails [id=" + id + ", fullName=" + fullName + ", mobile=" + mobile + ", city=" + city + ", email="
				+ email + ", password=" + password + "]";
	}
    
    

	
}
