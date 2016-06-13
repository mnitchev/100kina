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
	            new User("mario", "3kapodis", "supermario@abv.bg", User.ADMINISTRATOR),
	            new User("100kila", "kilatamaika", "kilata@abv.bg")};

	    private static Movie[] MOVIES = {
	            new Movie("Star wars: The Force Awakens"),
	            new Movie("Star wars: The Force Awakens"),
	            new Movie("Star wars: The Force Awakens"),
	            new Movie("Star wars: The Force Awakens"),
	            new Movie("Star wars: The Force Awakens"),
	            new Movie("Star wars: The Force Awakens")};

	    @PersistenceContext
	    private EntityManager em;

	    
	    private MovieDAO movieDAO;
	    
	    private UserDAO userDAO;
	    
	    public void addTestDataToDB() {
	        deleteData();
	        addTestUsers();
	        addTestMovies();
	    }

	    private void deleteData() {
	        em.createQuery("DELETE FROM Movie").executeUpdate();
	        em.createQuery("DELETE FROM User").executeUpdate();
	        em.createQuery("DELETE FROM Ticket").executeUpdate();
	   }

	    private void addTestUsers() {
	        for (User user : USERS) {
	            userDAO.addUser(user);
	        }
	    }

	    private void addTestMovies() {
	        for (Movie movie : MOVIES) {
	            movieDAO.addMovie(movie);
	        }
	    }
}
