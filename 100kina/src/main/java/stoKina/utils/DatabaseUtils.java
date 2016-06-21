package stoKina.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
	            new Movie("Star wars: The Phantom Menance", writeImage("/100kina/src/main/webapp/images/tpm")),
	            new Movie("Star wars: Attack of The Clones",writeImage("/100kina/src/main/webapp/images/atc")),
	            new Movie("Star wars: Revenge of The Sith", writeImage("/100kina/src/main/webapp/images/rts")),
	            new Movie("Star wars: A New Hope", writeImage("/100kina/src/main/webapp/images/nh")),
	            new Movie("Star wars: The Empire Strikes Back", writeImage("/100kina/src/main/webapp/images/tesb")),
	            new Movie("Star wars: Return of The Jedi", writeImage("/100kina/src/main/webapp/images/rtj")),
	            new Movie("Star wars: The Force Awakens", writeImage("/100kina/src/main/webapp/images/tfa"))};

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
	    private static byte[] writeImage (String ImageName) {
	    	File fi = new File(ImageName);
	    	try {
				return Files.readAllBytes(fi.toPath());
			} catch (IOException e) {
		    	return new byte[10];
			}
	    }
}
