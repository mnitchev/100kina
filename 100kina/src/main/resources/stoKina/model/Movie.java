package stoKina.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie {
	
	private Long id;
	private String title;
	private String time;
	
	@OneToMany
	private List<Seat> seats = new ArrayList<>();
	
	
	public Movie() {
	}	
	public Movie(String title, String time, double seatPrice) {
		this.title = title;
		this.time = time;
		//идеята е да има "default"-ни 20 билета за всеки филм, които са свободни при първоначално
		//инициализиране на филма, с цена за съответния филм
		for(int i = 0; i < 20; i++) {
			seats.add(new Seat(seatPrice, 0));
		}
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
