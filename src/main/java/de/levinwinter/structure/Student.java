package de.levinwinter.structure;

public class Student {

    private final String name;
    private final byte[] image;

    public Student(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

}
