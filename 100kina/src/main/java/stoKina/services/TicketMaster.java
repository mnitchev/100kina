package stoKina.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Singleton;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.model.Ticket;

@Singleton
@EJB
public class TicketMaster {
	
	private ConcurrentHashMap<Long, List<Ticket>> reservedTickets = new ConcurrentHashMap<Long, List<Ticket>>();
	
	@Inject
	private TicketDAO ticketDAO;
	
	@Inject
	private MovieDAO movieDAO;
	
	public boolean isTicketReserved(Ticket ticket)
	{
		List<Ticket> value = reservedTickets.get(movieDAO.findById(ticket.getMovieId()));
		boolean hasTicket = value.contains(ticket);
		return hasTicket;
	}
	
	public boolean isTicketFree(Ticket ticket){
		/*Ticket movieTicket = ticketDAO.getAllTicketsByMvoieId(ticket.getMovieId(), ticket.getSeatNumber());
		if (movieTicket == null) {
			return true;
		}*/
		return !(isTicketReserved(ticket));
	}
	
	public boolean reserveTicket(Ticket ticket){
		if(isTicketFree(ticket)){
			List<Ticket> listToAdd = reservedTickets.get(movieDAO.findById(ticket.getMovieId()));
			listToAdd.add(ticket);
			reservedTickets.put(movieDAO.findById(ticket.getMovieId()).getId(),listToAdd);
			//start na nishkata
			Thread countDown = new CountDownThread(ticket, this);
			countDown.start();
			return true;
		}
		else{
			return false;
		}
	}

	public void removeTicket(Ticket reservedTicket) {
		List<Ticket> value = reservedTickets.get(movieDAO.findById(reservedTicket.getMovieId()));
		boolean hasTicket = value.contains(reservedTicket);
		if(hasTicket){
			//remove ticket from list
			reservedTickets.get(movieDAO.findById(reservedTicket.getMovieId())).remove(reservedTicket);
		}
	}

	/*public Collection<Ticket> getReservedTicketsByMovieId(Long movieId) {
		Collection<Ticket> ticketList = new ArrayList<>();
		for (Ticket ticket : reservedTickets.values()) {
			//TO BE CONTINUED
		}
		return reservedTickets.values();
	}*/
}
