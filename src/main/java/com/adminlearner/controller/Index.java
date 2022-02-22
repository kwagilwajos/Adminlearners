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
import org.springframework.web.bind.annotation.RequestMapping;


import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class Index {

    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Teacher.class)
            .addAnnotatedClass(Class.class)
            .addAnnotatedClass(Student.class)
            .addAnnotatedClass(Subject.class)
            .buildSessionFactory();

    @RequestMapping("/")
    public String home(Model model) {
        model.addAllAttributes(getCounts());
        return "dashboard";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/logout")
    public void logout(Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.logout();
        response.sendRedirect(request.getContextPath()+"/login?logout=true");

    }


    public Map<String,Object> getCounts(){
        Map<String,Object> counts = new HashMap<>();
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query countTeacher = session.createQuery("SELECT Count(*) FROM Teacher");
        Query  countSubject = session.createQuery("SELECT Count(*) FROM Subject");
        Query countClasses = session.createQuery("SELECT Count(*) FROM Class");
        Query countStudent = session.createQuery("SELECT Count(*) FROM Student");
        counts.put("teachers", countTeacher.getSingleResult());
        counts.put("students", countStudent.getSingleResult());
        counts.put("classes", countClasses.getSingleResult());
        counts.put("subjects", countSubject.getSingleResult());
        session.close();
        return counts;
    }

}
