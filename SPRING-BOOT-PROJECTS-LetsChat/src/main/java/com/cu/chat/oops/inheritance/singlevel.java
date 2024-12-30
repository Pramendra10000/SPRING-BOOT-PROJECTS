package com.cu.chat.oops.inheritance;

class Vehicle {
    void move() {
        System.out.println("This vehicle moves.");
    }
}

class Cars extends Vehicle {
    void honk() {
        System.out.println("The car honks.");
    }
}

public class singlevel {
    public static void main(String[] args) {
        Cars c = new Cars();
        c.move();  // Inherited from Vehicle class
        c.honk();  // Defined in Car class
    }
}


