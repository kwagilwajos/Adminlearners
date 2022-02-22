package com.adminlearner.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 1, message = "*Class name is required")
    @NotBlank(message = "*Can't be empty")
    @Column(name = "classname", nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany( mappedBy = "studentclass",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Student> students;

    public Class(Long id, String name, Subject subject, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Class(String name, Subject subject, Teacher teacher, List<Student> students) {
        this.name = name;
        this.subject = subject;
        this.teacher = teacher;
        this.students = students;
    }

    public Class(String name) {
        this.name = name;
    }

    public Class() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Student> getStudent() {
        return students;
    }

    public void setStudent(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student tempStudent){
        if(students == null){
            students = new ArrayList<>();
        }
        tempStudent.setStudentclass(this);
        students.add(tempStudent);
    }
}
