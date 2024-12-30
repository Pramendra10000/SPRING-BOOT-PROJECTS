package com.cu.chat.testcases;

public class MethodOverloadExample {
	
	public void stark(float f) {
		System.out.println("float value : : ");
	}
	
	public void stark(double d) {
		System.out.println("double value : : ");
	}
	
	
	public void stark(long i) {
		System.out.println("long value : : ");
	}
	
	
    public static void main(String[] args) {
		System.out.println("inside main");
		MethodOverloadExample mr = new MethodOverloadExample();
		
		mr.stark('c');
	}
}


