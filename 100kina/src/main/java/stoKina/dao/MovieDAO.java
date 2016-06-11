package stoKina.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stoKina.model.User;
import stoKina.model.Movie;

public class MovieDAO {
    @PersistenceContext
    private EntityManager em;
    
	public void addMovie(Movie movie) {
		 Movie foundMovie = findByTitleAndTime(movie.getTitle(), movie.getTime());
	        if (foundMovie == null) {
	            em.persist(movie);
	        }
	}
	
	private Movie findByTitleAndTime(String title, String time) {
	        TypedQuery<Movie> query = em
	                .createNamedQuery("findByTitleAndTime", Movie.class)
	                .setParameter("title", title).setParameter("time", time);
	        try {
	            return query.getSingleResult();
	        } catch (NoResultException e) {
	            return null;
	        }
	}
	
	public void takeSeatsForMovie(Movie movieToReserve, User userWhoMakesReservation, int[] seats) {
        Movie foundMovie = findById(movieToReserve.getId());
        for (int seat : seats) {
			foundMovie.getSeats().get(seat).setSeatType(2); 
		}
        userWhoMakesReservation.getPaidForMovies().add(foundMovie);
    }

    public Collection<Movie> getAllMovies() {
        return em.createNamedQuery("getAllMovies", Movie.class).getResultList();
    }
	public Movie findById(long key) {
        return em.find(Movie.class, key);
	}
    
}
