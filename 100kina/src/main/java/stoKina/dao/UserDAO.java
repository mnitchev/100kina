package stoKina.dao;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stoKina.model.Ticket;
import stoKina.model.User;

@Singleton
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    
	public boolean addUser(User user) {
		if (findByUserName(user.getUserName()) != null) {
			return false;
		}
		user.setPassword(getHashedPassword(user.getPassword()));
        em.persist(user);
        return true;
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
	
	
	
	 public User findById(long key) {
		        return em.find(User.class, key);
		}
	 public User findByUserName(String userName)
	 {
	     TypedQuery<User> query = em.createNamedQuery("findByUserName", User.class)
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
	 
	 public Collection<Ticket> getTicketsForUser(User user) {	 
		 TypedQuery<User> query = em.createNamedQuery("findById", User.class)
				 .setParameter("id",user.getId());
		user = query.getSingleResult();
		TypedQuery<Ticket> ticketQuery = em.createNamedQuery("findTicketsByUser", Ticket.class);
		return ticketQuery.getResultList();
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
	
	@TransactionAttribute
	public void buyTicketForUser(User user, Ticket ticket) {
		try{
			em.getTransaction().begin();
			user = em.find(User.class, user.getId());
			//user.getPaidTickets().add(ticket);
			em.getTransaction().commit();
		}catch(Exception e){
			
		}finally{
			
		}
	}
}