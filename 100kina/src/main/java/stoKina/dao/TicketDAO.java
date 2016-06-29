package stoKina.dao;


import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

	public Collection<Ticket> getAllTickets() {
		return em.createNamedQuery("getAllTickets", Ticket.class).getResultList();
	}

	public Collection<Ticket> getAllTicketsByMovieTitle(String movieTitle) {
		return em.createNamedQuery("getTicketsByMovieTitle", Ticket.class).
				setParameter("movieTitle", movieTitle).getResultList();
	}

	public Collection<Ticket> getAllTicketsByUserId(Long id) {
		return em.createNamedQuery("findTicketsByUser", Ticket.class).
				setParameter("id", id).getResultList();
	}

	public boolean isFree(Ticket ticket) {
		TypedQuery<Ticket> query = em.createNamedQuery("findTicket", Ticket.class).
				setParameter("movieTitle", ticket.getMovieTitle()).
				setParameter("seatNumber", ticket.getSeatNumber());
		
		return query.getSingleResult() != null;
	}
}
