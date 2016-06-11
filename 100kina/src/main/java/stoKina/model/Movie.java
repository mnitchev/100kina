package stoKina.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "findByTitleAndTime", query = "SELECT m FROM Movie m WHERE m.title = :title AND m.time = :author"),
        @NamedQuery(name = "getAllMovies", query = "SELECT m FROM Movie m ")})
public class Movie implements Serializable{
	
	
	private static final long serialVersionUID = -7936362793263897507L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String time;
	
	@OneToMany
	private List<Seat> seats = new ArrayList<>();
	
	
	public Movie() {
	}	
	public Movie(String title, String time) {
		this.title = title;
		this.time = time;
	}
	public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	@Override
	public String toString() {
        StringBuilder result = new StringBuilder(getClass().getSimpleName() + " " + getTitle() + " " + getTime());
        for (Seat seat : seats) {
        	result.append(" ");
			result.append(seat.getSeatType());
		}
        return result.toString();
    } 
	@Override
	public boolean equals(Object obj){
		if(this == obj) {
			return true;
		}
        if (!(obj instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) obj;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
	}
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
}
