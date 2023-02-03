package com.erinc.repository.criteriaquery;
/*
   1- findall fonksiyonu yazalım

 */

import com.erinc.repository.entity.EGender;
import com.erinc.repository.entity.ICrud;
import com.erinc.repository.entity.Name;
import com.erinc.repository.entity.User;
import com.erinc.utility.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/*

1-find all
2-findallUserName
3-findbyid;--> dısaridan bir id alacagız bu idye ait useri donecegız
4-findbyusername
5-ismi M ile başlayanları getiren sorgu..
6-ismi M ile başlayıp, postcount'u 6 üzerinde olanları getiren method.
7-Toplam post sayısı.
8-en az post atan kullanıcı,
9-en çok post atan kullanıcı.
10- kullanıcıların username, gender ve postCountlarını dönelim.
11- erkak kullanıcı ve kadın kullanıcı sayılarını bulalım.
12- postCoun'u 7 üzerinde olan kullanıcı sayısı
 */
public class UserRepository implements ICrud<User> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public UserRepository() {
        entityManager= HibernateUtils.getSessionFactory().createEntityManager();
        criteriaBuilder= entityManager.getCriteriaBuilder();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root =criteria.from(User.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("username"),username));
        try{

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        CriteriaQuery<User> criteria=criteriaBuilder.createQuery(User.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root);
        List<User> users=entityManager.createQuery(criteria).getResultList();
        return users;
    }

    @Override
    public Optional<User> findbyId(Long id) {
        User user=null;
        CriteriaQuery<User> criteria=criteriaBuilder.createQuery(User.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"),id));
        try {
          user=entityManager.createQuery(criteria).getSingleResult();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public void findAllUserName(){
        CriteriaQuery<Name> criteria=criteriaBuilder.createQuery(Name.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root.get("name"));
        List<Name> nameList=entityManager.createQuery(criteria).getResultList();
        nameList.forEach(System.out::println);
    }

    public void findAllUserFirstName(){
        CriteriaQuery<String> criteria=criteriaBuilder.createQuery(String.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root.get("name").get("name"));
        List<String> nameList=entityManager.createQuery(criteria).getResultList();
        nameList.forEach(System.out::println);
    }


    public List<User> findByUserStartWithValue(String value){
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root= criteria.from(User.class);
        //Predicate predicate = criteriaBuilder.like(root.get("name").get("name"),"M%");
        //criteria.where(predicate);
        criteria.select(root).where(criteriaBuilder.like(root.get("name").get("name"),value+"%"));
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<User> findByUserStartWithValueAndPostCound(){
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root= criteria.from(User.class);
        criteria.select(root).where(criteriaBuilder.and(criteriaBuilder.like(root.get("name").get("name"),"M%"),
                criteriaBuilder.greaterThan(root.get("postCount"),6)));
        return entityManager.createQuery(criteria).getResultList();
    }
    public List<User> findByUserStartWithValueAndPostCound2(){
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root= criteria.from(User.class);
        //Path<String> path = root.get("name").get("name");

        Predicate like = criteriaBuilder.like(root.get("name").get("name"),"M%");
        Predicate gt = criteriaBuilder.greaterThan(root.get("postCount"),6);

        criteria.select(root).where(criteriaBuilder.and(like,gt));

        return entityManager.createQuery(criteria).getResultList();
    }

    public int sumPost(){
        CriteriaQuery<Integer> criteria = criteriaBuilder.createQuery(Integer.class);
        Root<User> root= criteria.from(User.class);
        criteria.select(criteriaBuilder.sum(root.get("postCount")));
        return entityManager.createQuery(criteria).getSingleResult();
    }
    public User maxPost(){
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).orderBy(criteriaBuilder.desc(root.get("postCount")));
        return entityManager.createQuery(criteria).getResultList().get(0);
    }
//kullanıcıların username, gender ve postCountlarını dönelim.
    public void manyColumns(){
        CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
        Root<User> root = criteria.from(User.class);

        Path<String> userNamePath = root.get("username");
        Path<EGender> genderPath = root.get("gender");
        Path<Integer> postCountPath = root.get("postCount");

        criteria.select(criteriaBuilder.array(userNamePath,genderPath,postCountPath));

        List<Object[]> manyList = entityManager.createQuery(criteria).getResultList();
        manyList.forEach(x->{
            for (Object o:x) {
                System.out.printf("Veriler.....: " + o.toString());
            }
            System.out.println();
        });
    }

    public List<Object[]> getUsernameGenderPostCount(){
        CriteriaQuery<Object[]> criteria= criteriaBuilder.createQuery(Object[].class);
        Root<User> root= criteria.from(User.class);
        criteria.multiselect(root.get("username"),root.get("gender"),root.get("postCount"));
        return  entityManager.createQuery(criteria).getResultList();
    }

    public List<Tuple> getUsernameGenderPostCount2(){
        CriteriaQuery<Tuple> criteria= criteriaBuilder.createQuery(Tuple.class);
        Root<User> root= criteria.from(User.class);
        Path<String> username=root.get("username");
        Path<String> gender=root.get("gender");
        Path<Integer> postCount=root.get("postCount");
        criteria.multiselect(username,gender,postCount);
        return  entityManager.createQuery(criteria).getResultList();
    }

    public List<Object[]> genderCount(){
        CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
        Root<User> root = criteria.from(User.class);
        criteria.multiselect(root.get("gender"),criteriaBuilder.count(root)).groupBy(root.get("gender"));
        return entityManager.createQuery(criteria).getResultList();
    }








}
