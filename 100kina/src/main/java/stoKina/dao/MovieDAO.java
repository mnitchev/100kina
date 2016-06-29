package stoKina.dao;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stoKina.model.Movie;

@Singleton
public class MovieDAO {
    @PersistenceContext
    private EntityManager em;
    
    
	public void addMovie(Movie movie) {
	      em.persist(movie);
	}
	
	
	public Movie findByTitle(String title) {
	        TypedQuery<Movie> query = em
	                .createNamedQuery("findByTitle", Movie.class)
	                .setParameter("title", title);
	        try{
	            return query.getSingleResult();
	        }catch(NoResultException e){
	        	return null;
	        }
	}

    /*
    public void buyTicket(Movie movieToReserve, User user, Ticket ticket) {
        user.getPaidTickets().add(ticket);
        movieToReserve.getPaidTickets().add(ticket);
    }*/
    
    public Collection<Movie> getAllMovies() {
    	Collection<Movie> result =  em.createNamedQuery("getAllMovies", Movie.class).getResultList();
    	return result;
    }
    
	public Movie findById(Integer key) {
        return em.find(Movie.class, key);
	}
	
	public Collection<Movie> getMovieIdAndTitle() {
		return em.createNamedQuery("getMovieIdAndTitle", Movie.class).getResultList();
	}


	/*public void addTicket(Movie movie, Ticket ticket) {
		em.getTransaction().begin();
		movie = em.find(Movie.class, movie.getId());
		movie.getPaidTickets().add(ticket);
		em.getTransaction().commit();
	}*/
    
	
}