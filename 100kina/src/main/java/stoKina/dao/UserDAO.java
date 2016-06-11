package stoKina.dao;

import java.security.MessageDigest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stoKina.model.User;

public class UserDAO {

    @PersistenceContext
    private EntityManager em;

	public void addUser(User user) {
		user.setPassword(getHashedPassword(user.getPassword()));
        em.persist(user);
	}
	 public boolean validateUserCredentials(String userName, String password) {
		 TypedQuery<User> query = em.createNamedQuery("validateUser", User.class)
	                .setParameter("userName", userName)
	                .setParameter("password", getHashedPassword(password));
	      
		 try {
	          return query.getSingleResult() != null ? true : false;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	 public User findUserByName(String userName) {
	     TypedQuery<User> query = em.createNamedQuery("findUserByName", User.class)
	    		 .setParameter("userName", userName);
	     try {
	            return query.getSingleResult();
	        } catch (Exception e) {
	            return null;
	        }
	    }
	private String getHashedPassword(String password) {
        try {
            MessageDigest mda = MessageDigest.getInstance("SHA-512");
            password = new String(mda.digest(password.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
}
