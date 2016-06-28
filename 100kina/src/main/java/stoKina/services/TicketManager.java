<<<<<<< HEAD
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
import javax.ws.rs.core.MediaType;

import stoKina.dao.TicketDAO;
import stoKina.model.Ticket;

@Stateless
public class TicketManager {
	
	@Inject
	private TicketDAO ticketDAO;
	
	@GET
	@Path("{ticketId}")
	@Produces("aplication/json")
	public Ticket getTicket(@PathParam("ticketId") String ticketId) {
		return ticketDAO.findById(Long.parseLong(ticketId));
	}
	
	@GET
	@Produces
	public Collection<Ticket> getAllTickets() {
		return ticketDAO.getAllTickets();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addTicket(Ticket ticket){
		ticketDAO.addTicket(ticket);
	}

}
=======
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
import javax.ws.rs.core.MediaType;

import stoKina.dao.TicketDAO;
import stoKina.model.Ticket;

@Stateless
public class TicketManager {
	
	@Inject
	private TicketDAO ticketDAO;
	
	@Inject
	private TicketMaster ticketMaster;
	
	@GET
	@Path("{ticketId}")
	@Produces("aplication/json")
	public Ticket getTicket(@PathParam("ticketId") String ticketId) {
		return ticketDAO.findById(Long.parseLong(ticketId));
	}
	
	@GET
	@Path("getAllTickets")
	@Produces("application/json")
	public Collection<Ticket> getAllTicketsForMovie(@PathParam("movieId") String movieId) {
		return ticketDAO.getAllTicketsByMvoieId(Long.parseLong(movieId));
	}
	
	@GET
	@Path("getReservedTickets")
	@Produces("application/json")
	public Collection<Ticket> getAllReservedTicketsForMovie(@PathParam("movieId") String movieId) {
		return null;
		//TODO: finish
		//return ticketMaster.getReservedTicketsByMovieId(Long.parseLong(movieId));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addTicket(Ticket ticket){
		ticketDAO.addTicket(ticket);
	}

}
>>>>>>> origin/master
