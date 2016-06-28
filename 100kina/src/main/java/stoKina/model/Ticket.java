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
    @NamedQuery(name = "getTicketByMovieTitle", 
    		query = "SELECT t FROM Ticket t WHERE t.movieTitle=:movieTitle")})
public class Ticket implements Serializable{
	
	
	private static final long serialVersionUID = 3073280968997835124L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer seatNumber;
	
	private String movieTitle;
	
	@Temporal(TemporalType.DATE)
    private Date timeOfEntry;

	public Ticket() {
	}
	
	public Ticket(Integer seatNumber, String movieTitle) {
		super();
		this.seatNumber = seatNumber;
		this.movieTitle = movieTitle;
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

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
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
	
}