package stoKina.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.dao.MovieDAO;
import stoKina.dao.UserDAO;
import stoKina.dao.SeatDAO;
import stoKina.model.Movie;
import stoKina.model.Seat;
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
	            new Movie("Star wars: The Force Awakens", "10:00"),
	            new Movie("Star wars: The Force Awakens", "12:00"),
	            new Movie("Star wars: The Force Awakens", "14:00"),
	            new Movie("Star wars: The Force Awakens", "16:00"),
	            new Movie("Star wars: The Force Awakens", "18:00"),
	            new Movie("Star wars: The Force Awakens", "20:00")};
	    
	    private static Seat[] SEATS = {
	    		new Seat(5,0)};

	    @PersistenceContext
	    private EntityManager em;

	    
	    private MovieDAO movieDAO;
	    
	    private UserDAO userDAO;
	    
	    private SeatDAO seatDAO;
	    
	    public void addTestDataToDB() {
	        deleteData();
	        addTestUsers();
	        addTestMovies();
	        addTestSeats();
	    }

	    private void deleteData() {
	        em.createQuery("DELETE FROM Movie").executeUpdate();
	        em.createQuery("DELETE FROM User").executeUpdate();
	        em.createQuery("DELETe FROM Seat").executeUpdate();
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
	    
	    private void addTestSeats() {
	    	for(Seat seat: SEATS) {
	    		seatDAO.addSeat(seat);
	    	}
	    }
}
