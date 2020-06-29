package ru.javawebinar.topjava.repository.jpa;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListenerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.reactive.TransactionContext;
import org.springframework.transaction.reactive.TransactionContextManager;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.UserServlet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Propagation.NESTED;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()){
            em.persist(meal);
            return meal;
        } else
            if (get(meal.getId(), userId) == null){
                return null;
        }
            return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
//        Meal ref = em.getReference(Meal.class, id);
//        em.remove(ref);

//        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId");
//        return query.setParameter("id", id)
//                .setParameter("userId", userId)
//                .executeUpdate() != 0;

        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);

//        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId");
//        List<Meal> meals = query
//                .setParameter("id", id)
//                .setParameter("userId", userId)
//                .getResultList();
//        Meal meal = meals.stream().findFirst().orElse(null);

//        return meal != null && meal.getUser().getId() == userId ? meal : null;
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        System.out.println("USER_ID: "+userId);

        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId " +
                "ORDER BY m.dateTime DESC ");
        List<Meal> meals = query
                .setParameter("userId", userId)
                .getResultList();

//        List<Meal> meals = em.createNativeQuery(Meal.ALL_SORTED)
//               .setParameter("userId", userId)
//                .getResultList();

        System.out.println("ALL MEALS: "+meals);
        return meals;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        System.out.println("REPO startDate = "+startDateTime+"  endFate = "+endDateTime);
        System.out.println("REPO: "+Meal.GET_BETWEEN);
//        return em.createNativeQuery(Meal.GET_BETWEEN)
//                .setParameter("userId", userId)
//                .setParameter("startDateTime", startDateTime)
//                .setParameter("endDateTime", endDateTime)
//                .getResultList();

        Query query = em.createQuery("SELECT m FROM Meal m WHERE  m.user.id=:userId AND " +
                "m.dateTime >= : startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC ");
        return query
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}