package com.cu.chat.oops.inheritance;

class Transport {
    void move() {
        System.out.println("This transport moves.");
    }
}

class Car extends Transport {
    void honk() {
        System.out.println("The car honks.");
    }
}

class SportsCar extends Car {
    void accelerate() {
        System.out.println("The sports car accelerates quickly.");
    }
}

public class Multilevel {
    public static void main(String[] args) {
        SportsCar sc = new SportsCar();
        sc.move();       // Inherited from Transport
        sc.honk();       // Inherited from Car
        sc.accelerate(); // Defined in SportsCar
    }
}





