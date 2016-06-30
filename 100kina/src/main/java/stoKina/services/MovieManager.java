package stoKina.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import stoKina.dao.MovieDAO;
import stoKina.dao.TicketDAO;
import stoKina.model.Movie;
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
	public Collection<Movie> getAllMovies() {
		Collection<Movie> queryResult = movieDAO.getAllMovies();
		return queryResult;
    }

    @GET
    @Path("{movieTitle}")
    @Produces("application/json")
    public Movie getMovie(@PathParam("movieTitle") String movieTitle) {
        return movieDAO.findByTitle(movieTitle);
    }
    
    @GET
	@Path("getTicketsForMovie")
	@Produces("application/json")
	public Collection<Ticket> getAllTicketsForMovie(@PathParam("movieTitle") String movieTitle) {
		return ticketDAO.getAllTicketsByMovieTitle(movieTitle);
	}
   
}
