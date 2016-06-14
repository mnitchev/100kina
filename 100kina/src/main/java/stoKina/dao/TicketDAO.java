package stoKina.dao;

import java.util.Date;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.model.Ticket;

@Singleton
public class TicketDAO {

    @PersistenceContext
    private EntityManager em;
    
    
	public void addTicket(int seatNumber, String movieTitle, Date timeOfEntry) {
        em.persist(new Ticket(seatNumber,movieTitle,timeOfEntry));
	}
}
