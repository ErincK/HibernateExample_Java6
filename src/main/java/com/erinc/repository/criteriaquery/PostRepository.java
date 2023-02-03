package com.erinc.repository.criteriaquery;

import com.erinc.repository.entity.ICrud;
import com.erinc.repository.entity.Post;
import com.erinc.repository.entity.User;
import com.erinc.utility.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 12- her kullanıcı kaç post atmış onu bulalım.
 */
public class PostRepository implements ICrud<Post> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    public PostRepository() {
        entityManager= HibernateUtils.getSessionFactory().createEntityManager();
        criteriaBuilder= entityManager.getCriteriaBuilder();
    }

    @Override
    public Optional<Post> findByUsername(String T) {
        return Optional.empty();
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Optional<Post> findbyId(Long id) {
        return Optional.empty();
    }

    public List<Object[]> howManyPost(){
        CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
        Root<Post> root = criteria.from(Post.class);
        //criteria.multiselect(root.get("gender"),criteriaBuilder.count(root)).groupBy(root.get("gender"));
        criteria.multiselect(root.get("userId"),criteriaBuilder.count(root)).groupBy(root.get("id"));
        return entityManager.createQuery(criteria).getResultList();
    }

    public void usersPostCount() {
        CriteriaQuery<Tuple> criteria = criteriaBuilder.createQuery(Tuple.class);
        Root<Post> root = criteria.from(Post.class);
        criteria.multiselect(root.get("userId"),criteriaBuilder.count(root)).groupBy(root.get("userId"));

        List<Tuple> tuples = entityManager.createQuery(criteria).getResultList();
        tuples.forEach(x->{
            System.out.println(x.get(0)+" - "+x.get(1));
        });
    }

    public void userPostCountGt3(){
        CriteriaQuery<Tuple> criteria = criteriaBuilder.createQuery(Tuple.class);
        Root<Post> root = criteria.from(Post.class);
        Path<Long> userId = root.get("userId");
        Expression<Long> count = criteriaBuilder.count(root.get("userId"));
        criteria.multiselect(userId,count).groupBy(userId).having(criteriaBuilder.greaterThan(count,3L));
        List<Tuple>tuples = entityManager.createQuery(criteria).getResultList();
        tuples.forEach(x->{
            System.out.println(x.get(0)+ " - "+ x.get(1));
        });
     }

}
