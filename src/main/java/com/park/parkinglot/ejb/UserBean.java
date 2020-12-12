/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.park.parkinglot.ejb;

import com.park.parkinglot.common.UserDetails;
import com.park.parkinglot.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dani
 */
@Stateless
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void CreateUser(String userame, String email, String passwordSha256, String position) {
        User user = new User();
        user.setUsername(userame);
        user.setEmail(email);
        user.setPassword(passwordSha256);
        user.setPosition(position);
        em.persist(user);
    }
    public List<UserDetails> getAllUsers() {
        LOG.info("getAllUsers");
        try {
            List<User> users = (List<User>) em.createQuery("SELECT u FROM User u").getResultList();
            return copyUsersToDetails(users);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
    
    public List<UserDetails> copyUsersToDetails(List<User> users) {
        List<UserDetails> detailsList = new ArrayList<>();
        for (User user : users) {
            UserDetails  userDetails = new UserDetails(user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPosition());
            detailsList.add(userDetails);      
        }
        return detailsList;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
