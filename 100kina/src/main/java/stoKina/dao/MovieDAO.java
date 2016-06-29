package stoKina.dao;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.model.User;
import stoKina.model.Movie;
import stoKina.model.Ticket;

@Singleton
public class MovieDAO {
    @PersistenceContext
    private EntityManager em;
    
	public void addMovie(Movie movie) {
		 //Movie foundMovie = findById(movie.getId());
	      //  if (foundMovie != null) {
	      em.persist(movie);
	        //}
	}
	
	/*//popravil na public
	public Movie findByTitle(String title) {
	        TypedQuery<Movie> query = em
	                .createNamedQuery("findByTitle", Movie.class)
	                .setParameter("title", title);
	        try {
	            return query.getSingleResult();
	        } catch (NoResultException e) {
	            return null;
	        }
	}*/
	
//	private Movie findById(long id) {
//		TypedQuery<Movie> query = em
//				.createNamedQuery("findById", Movie.class)
//				.setParameter("movieId", id);
//		try {
//			return query.getSingleResult();
//		} catch (NoResultException e) {
//			return null;
//		}
//	}

//    public void buyTicket(String userName, Integer seatNumber, String movieTitle, Date timeOfEntry) {
//        new TicketDAO().addTicket(seatNumber, movieTitle, timeOfEntry);
//        Movie foundMovie = findByTitle(movieTitle);
//        User foundUser = new UserDAO().findUserByName(userName);
//        foundUser.getPaidTickets().add(new Ticket(seatNumber, movieTitle, timeOfEntry));
//        foundMovie.getUsers().add(foundUser);
//    }
    
    public void buyTicket(Movie movieToReserve, User user, Ticket ticket) {
        user.getPaidTickets().add(ticket);
        movieToReserve.getPaidTickets().add(ticket);
    }
    
    public Collection<Movie> getAllMovies() {
        return em.createNamedQuery("getAllMovies", Movie.class).getResultList();
    }
    
	public Movie findById(long key) {
        return em.find(Movie.class, key);
	}
	
	public Collection<Movie> getMovieIdAndTitle() {
		return em.createNamedQuery("getMovieIdAndTitle", Movie.class).getResultList();
	}
    
}