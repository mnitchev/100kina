package stoKina.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import stoKina.model.Movie;
import stoKina.model.Ticket;

@ApplicationScoped
public class TicketOrganizer {
	public static final int Timer = 10000;
	private ConcurrentHashMap<Integer , List<Ticket>> reservedTickets = new ConcurrentHashMap<>();
	
	public void cleanBlocked(Integer movieId){
		if(!reservedTickets.containsKey(movieId)){
			return;
		}
		List<Ticket> ticketsForMovie = this.reservedTickets.get(movieId);
		if(ticketsForMovie == null){
			reservedTickets.remove(movieId);
			return;
		}
		for(Ticket ticket : ticketsForMovie){
			Date current = new Date();
			if(!(current.getTime() - ticket.getTimeOfEntry().getTime() < Timer)){
				ticketsForMovie.remove(ticket);
				//reservedTickets.get(movieId).remove(ticket);
			}
			if(ticketsForMovie.isEmpty()){
				break;
			}
		}
		if(ticketsForMovie.isEmpty()){
			reservedTickets.remove(movieId);
			return;
		}
		reservedTickets.put(movieId, ticketsForMovie);
	}
	
	public boolean isTicketReseved(Integer movieId , int seatNumber) {
		List<Ticket> ticketsForMovie = this.reservedTickets.get(movieId);
		if(ticketsForMovie == null){
			return false;
		}
		for (Ticket ticket : ticketsForMovie) {
			if(ticket.getSeatNumber()==seatNumber)
			{
				Date current = new Date();
				if(current.getTime() - ticket.getTimeOfEntry().getTime() < Timer){
					return true;
				}
				else 
				{
					reservedTickets.get(movieId).remove(ticket);
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean reserveTicket(Integer movieId , Ticket ticket){
		if (isTicketReseved( movieId ,ticket.getSeatNumber())){
			return false;
		}
		else {
			if(this.reservedTickets.containsKey(movieId))
			{
				this.reservedTickets.get(movieId).add(ticket);
				return true;
			}
			else 
			{
				this.reservedTickets.put(movieId, new ArrayList<Ticket>());
				this.reservedTickets.get(movieId).add(ticket);
				return true;
			}
		}
		
	}
	
	public Collection<Ticket> getAllReservedForMovie(Integer movieId){
		if(!reservedTickets.containsKey(movieId)){
			return null;
		}
		return reservedTickets.get(movieId);
	}
}