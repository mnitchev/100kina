package stoKina.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Seat {
	private static final String[] type= {"FREE", "RESERVED", "TAKEN" };
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private double price;
	private int seatType;
	
	public Seat() {
		
	}
	public Seat(double price, int seatType) {
		this.price = price;
		this.seatType = seatType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSeatType() {
		return type[seatType];
	}
	public void setSeatType(int seatType) {
		if(seatType >= 0 && seatType <= 2)
		this.seatType = seatType;
	}
	@Override
	public String toString() {
        return getClass().getSimpleName() + " " + getSeatType() + " " + price;
    }    
}
