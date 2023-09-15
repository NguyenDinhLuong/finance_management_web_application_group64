package com.financemanagementwebapp.controller;

import com.financemanagementwebapp.entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestUserController {

    private SessionFactory factory = new Configuration()
            .configure("pom.xml")
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    @PostMapping("/createUser")
    public boolean createUser(@RequestParam String fName,
                              @RequestParam String lName,
                              @RequestParam String password,
                              @RequestParam String email){

        User user = new User(fName,lName,email,password);

        Session session = factory.getCurrentSession();

        try{
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
