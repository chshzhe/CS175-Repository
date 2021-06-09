package com.bytedance.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Course {
    @SerializedName("name")
    public String name;
    @SerializedName("location")
    public String location;
    @SerializedName("lesson_count")
    public int lessonCount;
    @SerializedName("students")
    public List<Student> students;

    public static class Student {
        @SerializedName("name")
        public String name;
        @SerializedName("male")
        public boolean male;
    }
}

