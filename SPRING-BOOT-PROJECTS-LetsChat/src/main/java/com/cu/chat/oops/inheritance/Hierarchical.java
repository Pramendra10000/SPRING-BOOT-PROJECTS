package com.cu.chat.oops.inheritance;

class Animal {
    void sound() {
        System.out.println("This animal makes a sound.");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("The dog barks.");
    }
}

class Cat extends Animal {
    void meow() {
        System.out.println("The cat meows.");
    }
}

public class Hierarchical {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.sound();  // Inherited from Animal class
        d.bark();   // Defined in Dog class
        
        Cat c = new Cat();
        c.sound();  // Inherited from Animal class
        c.meow();   // Defined in Cat class
    }
}
