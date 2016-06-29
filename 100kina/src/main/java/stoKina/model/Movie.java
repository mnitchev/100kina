package stoKina.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "findByTitle", query = "SELECT m FROM Movie m WHERE m.title =:title"),
		@NamedQuery(name = "getAllMovies", query = "SELECT m FROM Movie m ")})
public class Movie implements Serializable {

	private static final long serialVersionUID = -7936362793263897507L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String title;
	
	//@OneToMany(fetch=FetchType.EAGER)
	//private Set<Ticket> paidTickets = new HashSet<>();
	
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

	public Integer getId() {
		return id;
	}
	
	//public Set<Ticket> getPaidTickets() {
	//	return paidTickets;
	//}

	//public void setPaidTickets(Set<Ticket> paidTickets) {
	//	this.paidTickets = paidTickets;
	//}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getId().toString());
		sb.append(" ");
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