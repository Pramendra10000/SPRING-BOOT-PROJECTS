package com.cu.chat.oops.inheritance;

class Animals {
    void sleep() {
        System.out.println("This animal sleeps.");
    }
}

interface Swimmer {
    void swim();
}

class Fish extends Animals implements Swimmer {
    public void swim() {
        System.out.println("The fish swims.");
    }
}

public class Hybrid {
    public static void main(String[] args) {
        Fish f = new Fish();
        f.sleep(); // Inherited from Animal class
        f.swim();  // Implemented from Swimmer interface
    }
}

