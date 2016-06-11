package stoKina.dao;

import java.security.MessageDigest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.model.User;

public class UserDAO {

    @PersistenceContext
    private EntityManager em;

	public void addUser(User user) {
		user.setPassword(getHashedPassword(user.getPassword()));
        em.persist(user);
	}
	
	private String getHashedPassword(String password) {
        try {
            MessageDigest mda = MessageDigest.getInstance("SHA-512");
            password = new String(mda.digest(password.getBytes()));
        } catch (Exception e) {
        	//TODO:
            e.printStackTrace();
        }
        return password;
    }
}
