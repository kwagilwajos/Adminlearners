package com.adminlearner.controller;


import com.adminlearner.model.Class;
import com.adminlearner.model.Subject;
import com.adminlearner.model.Student;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/class")
public class ClassController {

    //Create session factory
    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Student.class)
            .addAnnotatedClass(Class.class)
            .addAnnotatedClass(Teacher.class)
            .addAnnotatedClass(Subject.class)
            .buildSessionFactory();

    @RequestMapping("/")
    public String home(Model model) {

        Class myClass = new Class();
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("classes", getClasses());
        attributes.put("class", myClass);
        attributes.put("subjects", getSubjects());
        attributes.put("teachers", getTeachers());

        model.addAllAttributes(attributes);
        return "class";
    }

    @RequestMapping("/addClass")
    public String add(@Valid @ModelAttribute("class") Class mClass, BindingResult bindingResult, Model model) {

        Session session = this.sessionFactory.getCurrentSession();
        Class myClass = new Class(mClass.getName());

        try {
            session.beginTransaction();
            session.save(myClass);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("classes", getClasses());
        attributes.put("subjects", getSubjects());

        model.addAllAttributes(attributes);
        return "class";
    }

    @RequestMapping("/delete")
    public String processForm(@RequestParam Long id, Model model) {

        Class myClass = new Class();
        Map<String, Object> attributes = new HashMap<String, Object>();
        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Class class1 = session.get(Class.class, id);
            session.delete(class1);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        attributes.put("class", myClass);
        attributes.put("classes", getClasses());
        attributes.put("subjects", getSubjects());
        attributes.put("teachers", getTeachers());

        model.addAllAttributes(attributes);

        return "class";
    }

    @RequestMapping("/assign")
    public String assign(HttpServletRequest req,Model model) {

        System.out.println(req.getParameter("tid"));

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Teacher teacher = (Teacher) session.get(Teacher.class, Long.parseLong(req.getParameter("tid")));
            Subject subject = (Subject) session.get(Subject.class, Long.parseLong(req.getParameter("sid")));
            Class myClass = (Class)session.get(Class.class, Long.parseLong(req.getParameter("cid")));
            teacher.addClass(myClass);
            session.update(teacher);
            subject.addClass(myClass);
            session.update(subject);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        Map<String, Object> attributes = new HashMap<String, Object>();
        Class myClass = new Class();
        attributes.put("class", myClass);
        attributes.put("classes", getClasses());
        attributes.put("subjects", getSubjects());
        attributes.put("teachers", getTeachers());
        model.addAllAttributes(attributes);
        return "class";
    }

    public List<Class> getClasses() {

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Class> classes = session.createQuery("SELECT a FROM Class a", Class.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return classes;
    }

    public List<Subject> getSubjects() {

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Subject> subjects = session.createQuery("SELECT a FROM Subject a", Subject.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return subjects;
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
