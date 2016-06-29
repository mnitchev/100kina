package stoKina.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonTypeId;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "getTicketsByMovieTitle",
    		query = "SELECT t FROM Ticket t WHERE t.movieTitle=:movieTitle"),
    @NamedQuery(name = "findTicketsByUser",
    		query = "SELECT t FROM Ticket t WHERE t.owner.id=:id"),
    @NamedQuery(name = "findTicket",
    		query = "SELECT t FROM Ticket t WHERE t.movieTitle=:title AND t.seatNumber=:seatNumber")})
public class Ticket implements Serializable{
	
	
	private static final long serialVersionUID = 3073280968997835124L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonTypeId
	private Long id;
	private Integer seatNumber;
	
	private String movieTitle;
	
	@ManyToOne
	private User owner;
	
	@ManyToOne
	private Movie movie;
	
	@Temporal(TemporalType.DATE)
    private Date timeOfEntry;

	public Ticket() {
	}
	
	public Ticket(Integer seatNumber, User user, Movie movie) {
		this.seatNumber = seatNumber;
		this.movie = movie;
		this.movieTitle = movie.getTitle();
		this.owner = user;
		this.timeOfEntry = new Date();
	}

	public Long getId() {
		return id;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	
	public Date getTimeOfEntry() {
		return timeOfEntry;
	}

	public void setTimeOfEntry(Date timeOfEntry) {
		this.timeOfEntry = timeOfEntry;
	}
	@Override
	public String toString(){
		return getClass().getSimpleName() + " "+ seatNumber;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Ticket)) {
			return false;
		}
		Ticket other = (Ticket) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	public String getMovieTitle() {
		return movie.getTitle();
	}
	
	public void setOwner(User user){
		this.owner = user;
	}
	
	public void setMovie(Movie movie){
		this.movie = movie;
		this.movieTitle = movie.getTitle();
	}
//	public void setMovieTitle(String movieTitle) {
//		this.movieTitle = movieTitle;
//	}
	
}