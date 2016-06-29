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
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "findByTitle", query = "SELECT m FROM Movie m WHERE m.title =:title"),
		@NamedQuery(name = "getAllMovies", query = "SELECT m FROM Movie m ")})
public class Movie implements Serializable {

	private static final long serialVersionUID = -7936362793263897507L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	
	@OneToMany
	@JsonIgnore
	private List<Ticket> paidTickets = new ArrayList<>();
	
	public Movie() {
	}
	
	public Movie(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}
	
	public List<Ticket> getPaidTickets() {
		return paidTickets;
	}

	public void setPaidTickets(List<Ticket> paidTickets) {
		this.paidTickets = paidTickets;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTitle());
		sb.append(" ");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
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