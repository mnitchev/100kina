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

	public Collection<Ticket> getAllTickets() {
		return em.createNamedQuery("getAllTickets", Ticket.class).getResultList();
	}

	public Collection<Ticket> getAllTicketsByMvoieTitle(String movieTitle) {
		return em.createNamedQuery("getTicketsByMovieTitle", Ticket.class).
				setParameter("movieTitle", movieTitle).getResultList();
	}
}
