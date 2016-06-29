package stoKina.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
    /*@NamedQuery(name = "getTicketByMovieTitle", 
    		query = "SELECT t FROM Ticket t WHERE t.movieTitle=:movieTitle"),*/
    @NamedQuery(name = "getTicketsByMovieId",
    		query = "SELECT t FROM Ticket t WHERE t.movieId=:movieId")})
public class Ticket implements Serializable{
	
	
	private static final long serialVersionUID = 3073280968997835124L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer seatNumber;
	
	//private String movieTitle;
	
	private Long movieId;
	
	@Temporal(TemporalType.DATE)
    private Date timeOfEntry;

	public Ticket() {
	}
	
	public Ticket(Integer seatNumber, Long movieId) {
		super();
		this.seatNumber = seatNumber;
		this.movieId = movieId;
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
	
	public Long getMovieId() {
		return movieId;
	}
	
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	/*public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}*/

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
	
}