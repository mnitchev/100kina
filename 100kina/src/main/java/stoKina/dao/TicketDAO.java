package stoKina.dao;


import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.model.Ticket;

@Singleton
public class TicketDAO {

    @PersistenceContext
    private EntityManager em;
    
    public Ticket findById(long key) {
        return em.find(Ticket.class, key);
	}
    
    public void addTicket(Ticket ticket) {
    	em.persist(ticket);
    }
    
    public Collection<Ticket> getTicketByMovieTitle(String movieTitle){
    	return em.createNamedQuery("getTicketByMovieTitle", Ticket.class).
    			setParameter("movieTitle", movieTitle).getResultList();
    }

	public Collection<Ticket> getAllTickets() {
		return em.createNamedQuery("getAllTickets", Ticket.class).getResultList();
	}
    
	/*public void addTicket(int seatNumber, String movieTitle, Date timeOfEntry) {
        em.persist(new Ticket(seatNumber, movieTitle, timeOfEntry));
	}*/
}
