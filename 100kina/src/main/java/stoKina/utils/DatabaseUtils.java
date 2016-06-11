package stoKina.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.dao.MovieDAO;
import stoKina.dao.UserDAO;
import stoKina.model.Movie;
import stoKina.model.User;

public class DatabaseUtils {
	    
	    private static User[] USERS = {
	            new User("kriska", "kriska", "kriska@abv.bg"),
	            new User("bojkata", "superqkataparola", "glorfindel@abv.bg"),
	            new User("vasko", "witcher3thebest", "vasilnikolov@abv.bg"),
	            new User("dani", "daniboy", "dani@abv.bg"),
	            new User("mario", "3kapodis", "supermario@abv.bg"),
	            new User("100kila", "kilatamaika", "kilata@abv.bg")};

	    private static Movie[] MOVIES = {
	            new Movie("Star wars: The Force Awakens", "10:00", 5),
	            new Movie("Star wars: The Force Awakens", "12:00", 5),
	            new Movie("Star wars: The Force Awakens", "14:00", 5),
	            new Movie("Star wars: The Force Awakens", "16:00", 5),
	            new Movie("Star wars: The Force Awakens", "18:00", 5),
	            new Movie("Star wars: The Force Awakens", "20:00", 5)};

	    @PersistenceContext
	    private EntityManager em;

	    
	    private MovieDAO movieDAO;
	    
	    private UserDAO userDAO;
	    
	    public void addTestDataToDB() {
	        deleteData();
	        addTestUsers();
	        addTestBooks();
	    }

	    private void deleteData() {
	        em.createQuery("DELETE FROM Book").executeUpdate();
	        em.createQuery("DELETE FROM User").executeUpdate();
	   }

	    private void addTestUsers() {
	        for (User user : USERS) {
	            userDAO.addUser(user);
	        }
	    }

	    private void addTestBooks() {
	        for (Movie book : MOVIES) {
	            movieDAO.addBook(book);
	        }
	    }
}
