package com.cu.chat.oops.inheritance;

interface Musician {
    void playInstrument();
}

interface Performer {
    void perform();
}

class Artist implements Musician, Performer {
    public void playInstrument() {
        System.out.println("The artist plays an instrument.");
    }
    
    public void perform() {
        System.out.println("The artist performs on stage.");
    }
}

public class Multiplethroughinterface {
    public static void main(String[] args) {
        Artist a = new Artist();
        a.playInstrument(); // Implemented from Musician interface
        a.perform();        // Implemented from Performer interface
    }
}

