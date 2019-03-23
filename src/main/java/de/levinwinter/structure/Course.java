package de.levinwinter.structure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private final String name;
    private List<Student> students = new ArrayList<>();

    public Course(String name) {
        this.name = name;
    }

    public Student addStudent(Student student) {
        students.add(student);
        return student;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }
}
