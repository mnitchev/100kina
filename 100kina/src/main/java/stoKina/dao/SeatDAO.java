package stoKina.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stoKina.model.Seat;

public class SeatDAO {

    @PersistenceContext
    private EntityManager em;
    
	public void addSeat(Seat seat) {
        em.persist(seat);
	}
}
