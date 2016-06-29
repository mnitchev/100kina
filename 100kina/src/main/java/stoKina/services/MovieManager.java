package stoKina.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.model.Movie;
import stoKina.model.SimpleMovie;
import stoKina.model.Ticket;

@Stateless
@Path("movie")
public class MovieManager {
	
	@Inject
	private MovieDAO movieDAO;
	
	@Inject
	private UserContext userContext;
	
	@Inject
	private TicketDAO ticketDAO;
	
	@GET
	@Path("getAllMovies")
	@Produces("application/json")
	public Collection<SimpleMovie> getAllMovies() {
        Collection<Movie> queryResult =  movieDAO.getAllMovies();
        Collection<SimpleMovie> simpleResult = new ArrayList<>();
        for(Movie movie : queryResult){
        	simpleResult.add(new SimpleMovie(movie));
        }
        
        return simpleResult;
    }

    @GET
    @Path("{movieId}")
    @Produces("application/json")
    public Movie getMovie(@PathParam("movieId") String movieId) {
        return movieDAO.findById(Long.parseLong(movieId));
    }
    
    @GET
	@Path("getTicketsForMovie")
	@Produces("application/json")
	public Collection<Ticket> getAllTicketsForMovie(@PathParam("movieId") String movieId) {
		return ticketDAO.getAllTicketsByMvoieId(Long.parseLong(movieId));
	}
    
    /*@GET
	@Path("getReservedTickets")
	@Produces("application/json")
	public Collection<Ticket> getAllReservedTicketsForMovie(@PathParam("movieId") String movieId) {
		return null;
		//TODO: finish
		//return ticketMaster.getReservedTicketsByMovieId(Long.parseLong(movieId));
	}*/

    @PUT
    @Path("/buyTicket")
    public Response buyTicketForMovie(@QueryParam("movieId") String movieId, String seatNumbers) {
        Movie movieToReserve = movieDAO.findById(Long.parseLong(movieId));
        if (movieToReserve != null) {
            //movieDAO.buyTicket(movieToReserve, userContext.getCurrentUser(), ticketDAO.findById(Long.parseLong(ticketId)));
        	//TODO:
        }
        return Response.noContent().build();
    }
}
