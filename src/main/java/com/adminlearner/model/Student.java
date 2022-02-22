package com.adminlearner.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 1, message = "*Student name is required")
    @NotBlank(message = "*Can't be empty")
    @Column(name = "full_name", nullable = false,  unique = true)
    String name;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "class_id")
    private Class studentclass;

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, Class studentclass) {
        this.name = name;
        this.studentclass = studentclass;
    }

    public Student() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Class getStudentclass() {
        return studentclass;
    }

    public void setStudentclass(Class studentclass) {
        this.studentclass = studentclass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
