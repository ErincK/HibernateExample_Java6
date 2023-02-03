package com.erinc.repository.hql;

import com.erinc.repository.entity.Name;
import com.erinc.repository.entity.User;
import com.erinc.repository.entity.ICrud;
import com.erinc.utility.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserDao implements ICrud<User> {

    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        String hql = "select u from User as u where username=:myusername";
        Session session = HibernateUtils.getSessionFactory().openSession();
        TypedQuery<User> typedQuery = session.createQuery(hql, User.class);
        typedQuery.setParameter("myusername",username);
        try{
            user = typedQuery.getSingleResult();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public List<User> findByUserStartWithValue(String value){
        String hql = "select u from User as u where u.name.name like:x";
        Session session = HibernateUtils.getSessionFactory().openSession();
        TypedQuery<User> typedQuery = session.createQuery(hql, User.class);
        typedQuery.setParameter("x",value+"%");
        return typedQuery.getResultList();
    }

    @Override
    public List<User> findAll() {
        String hql="select u from User as u";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<User> typedQuery=session.createQuery(hql, User.class);
        List<User> users=typedQuery.getResultList();
        return users;
    }
    @Override
    public Optional<User> findbyId(Long id) {
        User user=null;
        String hql="select u from User as u where id="+id;
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<User> typedQuery=session.createQuery(hql, User.class);
       try {
          user =typedQuery.getSingleResult();
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
        return Optional.ofNullable(user);
    }

    public Optional<User> findbyId2(Long id) {
        User user=null;
        try {
            Session session= HibernateUtils.getSessionFactory().openSession();
            user =session.find(User.class,id);
            if (user==null){
                System.out.println("Kullanıcı bulunamadı");
                return Optional.empty();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public void findAllUserName(){
        String hql="select name from User";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<Name> typedQuery=session.createQuery(hql, Name.class);
        List<Name> users=typedQuery.getResultList();
        users.forEach(System.out::println);
    }

    public void findAllUserFirstName(){
        String hql="select name.name from User";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<String> typedQuery=session.createQuery(hql, String.class);
        List<String> users=typedQuery.getResultList();
        users.forEach(System.out::println);
    }
    public void findAllUserUsername(){
        String hql="select username from User";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<String> typedQuery=session.createQuery(hql, String.class);
        List<String> users=typedQuery.getResultList();
        users.forEach(System.out::println);
    }
    public List<User> findByUserStartWithValueAndPostCound(){
        String hql="select u from User as u where u.name.name like:x and postCount>:number";
        Session session = HibernateUtils.getSessionFactory().openSession();
        TypedQuery<User> typedQuery = session.createQuery(hql,User.class);
        typedQuery.setParameter("x","M%");
        typedQuery.setParameter("number",6);
        return typedQuery.getResultList();
    }

    public List<Tuple> genderCount(){
        String hql = "select gender, count(*) from User as u group by gender";
        Session session = HibernateUtils.getSessionFactory().openSession();
        TypedQuery<Tuple> typedQuery = session.createQuery(hql, Tuple.class);
        return typedQuery.getResultList();


    }


}
