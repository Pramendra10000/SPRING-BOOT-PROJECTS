package com.cu.chat.testcases;



public class methodRecursion {

	
	public void manage() {
		System.out.println("start here ");
		manage();
		System.out.println("end here ");
	
	}
	
	public static void main(String[] args) {
		System.out.println("in main");
		methodRecursion m2 = new methodRecursion();
		
		m2.manage();
		
		}
	
	
}
