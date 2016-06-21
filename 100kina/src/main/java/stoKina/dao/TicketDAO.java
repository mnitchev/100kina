package stoKina.dao;


import javax.inject.Singleton;
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
    
	/*public void addTicket(int seatNumber, String movieTitle, Date timeOfEntry) {
        em.persist(new Ticket(seatNumber, movieTitle, timeOfEntry));
	}*/
}
