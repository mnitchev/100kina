package stoKina.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.dao.UserDAO;
import stoKina.model.Movie;
import stoKina.model.Ticket;


public class TicketMaster {
	
	private static ConcurrentHashMap<Long, List<Ticket>> reservedTickets = new ConcurrentHashMap<Long, List<Ticket>>();
	
	@Inject
	private TicketDAO ticketDAO;
	
	@Inject
	private MovieDAO movieDAO;
	
	public boolean isTicketReserved(Ticket ticket)
	{
		List<Ticket> value = reservedTickets.get(movieDAO.findByTitle(ticket.getMovieTitle()));
		boolean hasTicket = value.contains(ticket);
		return hasTicket;
	}
	
	public boolean isTicketFree(Ticket ticket){
		// da se proveri dali ima bilet v bd
		// kak da vidq dali bileta go ima v bd ?
		Collection<Ticket> movieTickets = ticketDAO.getTicketByMovieTitle(ticket.getMovieTitle());
		boolean isTicketInDB = movieTickets.contains(ticket);
		return !(isTicketReserved(ticket) && isTicketInDB);
	}
	
	public void reserveTicket(Ticket ticket){
		if(isTicketFree(ticket)){
			List<Ticket> listToAdd = reservedTickets.get(movieDAO.findByTitle(ticket.getMovieTitle()));
			listToAdd.add(ticket);
			reservedTickets.put(movieDAO.findByTitle(ticket.getMovieTitle()).getId(),listToAdd);
			//start na nishkata
			Thread countDown = new CountDownThread(ticket, this);
			countDown.start();
		}
		else{
			//return response ?? 
		}
	}

	public void removeTicket(Ticket reservedTicket) {
		List<Ticket> value = reservedTickets.get(movieDAO.findByTitle(reservedTicket.getMovieTitle()));
		boolean hasTicket = value.contains(reservedTicket);
		if(hasTicket){
			//remove ticket from list
			reservedTickets.get(movieDAO.findByTitle(reservedTicket.getMovieTitle())).remove(reservedTicket);
		}
	}
	
	
	
	
}
