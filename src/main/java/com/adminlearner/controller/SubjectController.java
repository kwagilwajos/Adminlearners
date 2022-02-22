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
@RequestMapping("/subject")
public class SubjectController {

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
        Subject subject = new Subject();
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("subjects",getSubjects());
        attributes.put("subject", subject);

        //session.getTransaction().commit();
        model.addAllAttributes(attributes);
        return "subject";
    }

    @RequestMapping("/addSubject")
    public String add(@Valid @ModelAttribute("subject") Subject subject, BindingResult bindingResult, Model model){

        Session session = this.sessionFactory.getCurrentSession();
        Subject mySubject = new Subject( subject.getName() );

        try {
            session.beginTransaction();
            session.save(mySubject);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        model.addAttribute("subjects", getSubjects());
        return "subject";
    }

    @RequestMapping("/delete")
    public String processForm(@RequestParam Long id, Model model) {

        Subject subject = new Subject();
        Map<String, Object> attributes = new HashMap<String, Object>();
        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Subject subject1 = session.get(Subject.class, id);
            session.delete(subject1);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        attributes.put("subject", subject);
        attributes.put("subjects", getSubjects());
        model.addAllAttributes(attributes);

        return "subject";
    }

    public List<Subject> getSubjects(){

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Subject> subjects = session.createQuery("SELECT a FROM Subject a", Subject.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return subjects;
    }

}
