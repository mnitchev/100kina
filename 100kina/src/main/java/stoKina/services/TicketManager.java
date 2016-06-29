package stoKina.services;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.model.Ticket;

@Stateless
@Path("ticket")
public class TicketManager {
	
	@Inject
	private TicketDAO ticketDAO;
	
	@Inject
	private MovieDAO movieDAO;
	
	@Inject
	private TicketMaster ticketMaster;
	
	@Inject
	private UserContext context;
	
	@GET
	@Path("{ticketId}")
	@Produces("aplication/json")
	public Ticket getTicket(@PathParam("ticketId") String ticketId) {
		return ticketDAO.findById(Long.parseLong(ticketId));
	}
	
	@GET
	@Path("getAllTicketsForMovie")
	@Produces("application/json")
	public Collection<Ticket> getAllTicketsForMovie(@QueryParam("movieTitle") String movieTitle) {
		System.out.println(movieTitle);
		return ticketDAO.getAllTicketsByMovieTitle(movieTitle);
	}
	
	@GET
	@Path("getReservedTickets")
	@Produces("application/json")
	public Collection<Ticket> getAllReservedTicketsForMovie(@PathParam("movieId") String movieId) {
		return null;
		//TODO: finish
		//return .!.. ticketMaster.getReservedTicketsByMovieId(Long.parseLong(movieId));
	}
	
	/*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addTicket(Ticket ticket){
		ticketDAO.addTicket(ticket);
	}*/
	
	@Path("buy")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void buyTickets(Collection<Ticket> tickets, @PathParam("movieTitle") String movieTitle){
		for(Ticket ticket : tickets){
			ticket.setOwner(context.getCurrentUser());
			ticket.setMovie(movieDAO.findByTitle(movieTitle));
			ticketDAO.addTicket(ticket);
		}
	}

}
