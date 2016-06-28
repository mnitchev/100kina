package stoKina.services;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.dao.UserDAO;
import stoKina.model.Movie;
import stoKina.model.Ticket;


public class TicketMaster {
	
	private static ConcurrentHashMap<Long, List<Ticket>> reservedTickets= new ConcurrentHashMap<Long, List<Ticket>>();
	
	@Inject
	private TicketDAO ticketDAO;
	
	public boolean isTicketReserved(Ticket ticket,Movie movie)
	{
		List<Ticket> value = reservedTickets.get(movie.getId());
		boolean hasTicket = value.contains(ticket);
		return hasTicket;
	}
	
	public boolean isTicketFree(Ticket ticket,Movie movie){
		// da se proveri dali ima bilet v bd
		// kak da vidq dali bileta go ima v bd ?
		boolean isTicketInDB = ticketDAO.findById(ticket.getId());
		return isTicketReserved(ticket, movie) && isTicketInDB;
	}
	
	
	
	
}
