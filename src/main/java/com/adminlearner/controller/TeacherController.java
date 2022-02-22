package com.adminlearner.controller;


import com.adminlearner.model.Class;
import com.adminlearner.model.Student;
import com.adminlearner.model.Subject;
import com.adminlearner.model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    //Create session factory
    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Teacher.class)
            .addAnnotatedClass(Class.class)
            .addAnnotatedClass(Student.class)
            .addAnnotatedClass(Subject.class)
            .buildSessionFactory();

    @RequestMapping("/")
    public String home(Model model) {
        Teacher teacher = new Teacher();
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("teachers", getTeachers());
        attributes.put("teacher", teacher);

        //session.getTransaction().commit();
        model.addAllAttributes(attributes);
        return "teacher";
    }

    @RequestMapping("/addTeacher")
    public String add(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult, Model model) {
        Session session = this.sessionFactory.getCurrentSession();
        Teacher myTeacher = new Teacher(teacher.getName());

        try {
            session.beginTransaction();
            session.save(myTeacher);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        model.addAttribute("teachers", getTeachers());
        return "teacher";
    }

    @RequestMapping("/delete")
    public String processForm(@RequestParam Long id, Model model) {

        Teacher teacher = new Teacher();
        Map<String, Object> attributes = new HashMap<String, Object>();

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Teacher teacher1 = session.get(Teacher.class, id);
            session.delete(teacher1);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        attributes.put("teacher", teacher);
        attributes.put("teachers", getTeachers());
        model.addAllAttributes(attributes);

        return "teacher";
    }

    public List<Teacher> getTeachers() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Teacher> teachers = session.createQuery("SELECT a FROM Teacher a", Teacher.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return teachers;
    }

}
