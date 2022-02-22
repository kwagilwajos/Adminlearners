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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

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

        return renderStudent(model);
    }

    @RequestMapping("/addStudent")
    public String add(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {

        Session session = this.sessionFactory.getCurrentSession();
        Student myStudent = new Student(student.getName());

        try {
            session.beginTransaction();
            session.save(myStudent);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return renderStudent(model);
    }

    @RequestMapping("/delete")
    public String processForm(@RequestParam Long id, Model model) {

        Student student = new Student();
        Map<String, Object> attributes = new HashMap<String, Object>();

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Student student1 = session.get(Student.class, id);
            session.delete(student1);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        attributes.put("student", student);
        attributes.put("students", getStudents());
        model.addAllAttributes(attributes);

        return "student";
    }

    @RequestMapping("/assign")
    public String assign(HttpServletRequest req, Model model) {

        System.out.println("Student ID::::" + req.getParameter("sid"));
        System.out.println("Class ID::::" + req.getParameter("cid"));

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Student st = (Student) session.get(Student.class, Long.parseLong(req.getParameter("sid")));
            Class myClass = (Class) session.get(Class.class, Long.parseLong(req.getParameter("cid")));
            myClass.addStudent(st);
            session.update(myClass);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        } finally {
            session.close();
        }

        return renderStudent(model);
    }

    private String renderStudent(Model model) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        Student student = new Student();
        attributes.put("student", student);
        attributes.put("classes", getClasses());
        attributes.put("students", getStudents());
        model.addAllAttributes(attributes);
        return "student";
    }

    private List<Student> getStudents() {

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Student> students = session.createQuery("SELECT a FROM Student a", Student.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return students;
    }

    private List<Class> getClasses() {

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Class> classes = session.createQuery("SELECT a FROM Class a", Class.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return classes;
    }
}
