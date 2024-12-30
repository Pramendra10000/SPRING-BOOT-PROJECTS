package com.ps.shop;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class test {

	
	  public static void main(String[] args) throws Exception {
		//  byte[] fileContent = Files.readAllBytes(Paths.get("path/to/your/image.jpg"));
	        byte[] fileContent = Files.readAllBytes(Paths.get("C:\\Users\\ComUnus 237\\Pictures\\Screenshots\\Screenshot (1).png"));
	        String encodedImage = Base64.getEncoder().encodeToString(fileContent);
	        System.out.println(encodedImage);
	    }
	
}
//
//public class ImageEncoder {
//    public static void main(String[] args) throws Exception {
//        byte[] fileContent = Files.readAllBytes(Paths.get("path/to/your/image.jpg"));
//        String encodedImage = Base64.getEncoder().encodeToString(fileContent);
//        System.out.println(encodedImage);
//    }
//}