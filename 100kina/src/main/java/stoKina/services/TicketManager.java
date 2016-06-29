package stoKina.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import javax.ws.rs.core.Response;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.model.Movie;
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
		return ticketDAO.getAllTicketsByMovieTitle(movieTitle);
	}
	
	@GET
	@Path("getAllTicketsForUser")
	@Produces("application/json")
	public Collection<Ticket> getAllTicketsForUser() {
		return ticketDAO.getAllTicketsByUserId(context.getCurrentUser().getId());
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
	public Response buyTickets(@QueryParam("movieTitle") String movieTitle, @QueryParam("ticket1") String ticket1, 
			@QueryParam("ticket2") String ticket2, @QueryParam("ticket3") String ticket3,
			@QueryParam("ticket4") String ticket4, @QueryParam("ticket5") String ticket5){
		Movie movie = movieDAO.findByTitle(movieTitle);
		List<Ticket> tickets = new ArrayList<>();
		if(!ticket1.equals("undefined")){
			tickets.add(new Ticket(Integer.parseInt(ticket1),context.getCurrentUser(), movie));
		}
		if(!ticket2.equals("undefined")){
			tickets.add(new Ticket(Integer.parseInt(ticket2),context.getCurrentUser(), movie));
		}
		if(!ticket3.equals("undefined")){
			tickets.add(new Ticket(Integer.parseInt(ticket3),context.getCurrentUser(), movie));
		}
		if(!ticket4.equals("undefined")){
			tickets.add(new Ticket(Integer.parseInt(ticket4),context.getCurrentUser(), movie));
		}
		if(!ticket5.equals("undefined")){
			tickets.add(new Ticket(Integer.parseInt(ticket5),context.getCurrentUser(), movie));
		}
		
		for (Ticket ticket : tickets) {
			if (!ticketDAO.isFree(ticket)) {
				return Response.notAcceptable(null).build();
			}
		}
		
		for(Ticket ticket : tickets){
			ticket.setOwner(context.getCurrentUser());
			ticket.setMovie(movieDAO.findByTitle(movieTitle));
			ticketDAO.addTicket(ticket);
		}
		
		return Response.ok().build();
	}

}
