package net.oschina.j2cache;

import java.io.Serializable;

public class School implements Serializable {

    private String name;
    private int random = (int)(Math.random() * 1000);

    public School(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    @Override
    public String toString() {
        return String.format("NAME:%s,RANDOM:%d%n", name, random);
    }
}