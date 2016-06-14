package stoKina.services;

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
import stoKina.model.Movie;
import stoKina.model.Seat;

@Stateless
@Path("movie")
public class MovieManager {
	
	@Inject
	private MovieDAO movieDAO;
	
	@Inject
	private UserContext userContext;
	
	@Inject
	private Seat seat;
	
	@GET
	@Produces
	public Collection<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    @GET
    @Path("{movieId}")
    @Produces("application/json")
    public Movie getMovie(@PathParam("movieId") String movieId) {
        return movieDAO.findById(Long.parseLong(movieId));
    }

    @PUT
    @Path("/buyTicket")
    public Response buyTicketForMovie(@QueryParam("movieId") String movieId) {
        Movie movieToReserve = movieDAO.findById(Long.parseLong(movieId));
        if (movieToReserve != null) {
            movieDAO.buyTicket(movieToReserve, userContext.getCurrentUser(), seat);
        }
        return Response.noContent().build();
    }
}
