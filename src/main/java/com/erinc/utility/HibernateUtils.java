package com.erinc.utility;


import com.erinc.repository.entity.Post;
import com.erinc.repository.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {
    private static final SessionFactory SESSION_FACTORY = sessionFactoryHibernate();



    private static SessionFactory sessionFactoryHibernate(){
        try{
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Post.class);
            SessionFactory factory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
            return factory;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }










}
