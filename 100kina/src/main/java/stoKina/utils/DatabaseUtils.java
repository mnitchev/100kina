package stoKina.utils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.dao.UserDAO;
import stoKina.model.Movie;
import stoKina.model.Ticket;
import stoKina.model.User;
import stoKina.services.TicketMaster;

@Stateless
public class DatabaseUtils {
	    
	    private static User[] USERS = {
	            new User("kriska", "kriska", "kriska@abv.bg"),
	            new User("bojkata", "superqkataparola", "glorfindel@abv.bg"),
	            new User("vasko", "witcher3thebest", "vasilnikolov@abv.bg"),
	            new User("dani", "daniboy", "dani@abv.bg"),
	            new User("mario", "3kapodis", "supermario@abv.bg", User.ADMINISTRATOR),
	            new User("100kila", "kilatamaika", "kilata@abv.bg")};

	    private static Movie[] MOVIES = {
	            new Movie("Star Wars: The Phantom Menance"),
	            new Movie("Star Wars: Attack of The Clones"),
	            new Movie("Star Wars: Revenge of The Sith"),
	            new Movie("Star Wars: A New Hope"),
	            new Movie("Star Wars: The Empire Strikes Back"),
	            new Movie("Star Wars: Return of The Jedi"),
	            new Movie("Star Wars: The Force Awakens")};
	   /* private static Ticket[] TICKETS = {
	    		new Ticket(12, "Star wars: The Phantom Menance"),
	    		new Ticket(13, "Star wars: The Phantom Menance"),
	    		new Ticket(14, "Star wars: The Phantom Menance"),
	    		new Ticket(1, "Star wars: The Phantom Menance")};
	    */
	    @PersistenceContext
	    private EntityManager em;

	    @EJB
	    private MovieDAO movieDAO;
	    
	    @EJB
	    private UserDAO userDAO;

	    @EJB
	    private TicketDAO ticketDAO;
	    
	    @EJB
	    private TicketMaster ticketMaster;
	    
	    
	    public void addTestDataToDB() {
	        deleteData();
	        addTestUsers();
	        addTestMovies();
	        addTestTickets();
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
	    
	    private void addTestTickets(){
	    	User user = userDAO.findByUserName(USERS[0].getUserName());
	    	Movie movie = movieDAO.findByTitle(MOVIES[0].getTitle());
	    	Ticket ticket = new Ticket(12, user, movie);
	    	ticketDAO.addTicket(ticket);
	    	ticket = new Ticket(13, user, movie);
	    	ticketDAO.addTicket(ticket);
	    	ticket = new Ticket(14, user, movie);
	    	ticketDAO.addTicket(ticket);
	    	ticket = new Ticket(1, user, movie);
	    	ticketDAO.addTicket(ticket);
	    	
	    }
}
