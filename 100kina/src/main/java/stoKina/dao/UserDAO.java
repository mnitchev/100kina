package stoKina.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stoKina.model.User;

@Singleton
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    
	public void addUser(User user) {
		user.setPassword(getHashedPassword(user.getPassword()));
		user.setRole(User.USER);
        em.persist(user);
	}
	
	public boolean validateUserCredentials(String userName, String password) {
		 TypedQuery<User> query = em.createNamedQuery("validateUser", User.class);
		 query.setParameter("userName", userName);
	     query.setParameter("password", getHashedPassword(password));
	      
		 try {
	          return query.getSingleResult() != null;
	        } catch (Exception e) {
	        	e.printStackTrace();
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
	 public Collection<User> getAllUsers() {
	        return em.createNamedQuery("getAllUsers", User.class).getResultList();
	    }
	private String getHashedPassword(String password) {
    	String generatedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
            return null;
        }
        return generatedPassword;
    }
}