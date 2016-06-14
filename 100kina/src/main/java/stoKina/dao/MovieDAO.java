package stoKina.dao;

import java.util.Collection;
import java.util.Date;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stoKina.model.User;
import stoKina.model.Movie;
import stoKina.model.Seat;
import stoKina.model.Ticket;

@Singleton
public class MovieDAO {
    @PersistenceContext
    private EntityManager em;
    
	public void addMovie(Movie movie) {
		 Movie foundMovie = findByTitle(movie.getTitle());
	        if (foundMovie == null) {
	            em.persist(movie);
	        }
	}
	
	private Movie findByTitle(String title) {
	        TypedQuery<Movie> query = em
	                .createNamedQuery("findByTitle", Movie.class)
	                .setParameter("title", title);
	        try {
	            return query.getSingleResult();
	        } catch (NoResultException e) {
	            return null;
	        }
	}
	
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
    
    public void buyTicket(Movie movieToReserve, User user, Seat seat) {
        new TicketDAO().addTicket(seat.getSeatNumber(), movieToReserve.getTitle(), new Date());
        Movie foundMovie = findByTitle(movieToReserve.getTitle());
        //User foundUser = new UserDAO().findUserByName(user.getUserName());
        user.getPaidTickets().add(new Ticket(seat.getSeatNumber(), movieToReserve.getTitle(), new Date()));
        foundMovie.getUsers().add(user);
    }

    public Collection<Movie> getAllMovies() {
        return em.createNamedQuery("getAllMovies", Movie.class).getResultList();
    }
	public Movie findById(long key) {
        return em.find(Movie.class, key);
	}
    
}