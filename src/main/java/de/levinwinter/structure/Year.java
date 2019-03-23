package de.levinwinter.structure;

import java.util.ArrayList;
import java.util.List;

public class Year {

    private final String name;
    private List<Course> courses = new ArrayList<>();

    public Year(String name) {
        this.name = name;
    }

    public Course addCourse(Course course) {
        courses.add(course);
        return course;
    }

    public List<Course> getCourses() {
        return courses;
    }

}
