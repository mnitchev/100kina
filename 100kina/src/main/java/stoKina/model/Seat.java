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
	private int seatNumber;
	
	public Seat() {
		
	}
	public Seat(double price, int seatType, int seatNumber) {
		this.price = price;
		this.seatType = seatType;
		this.seatNumber = seatNumber;
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
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	@Override
	public String toString() {
        return getClass().getSimpleName() + " " + getSeatType() + " " + price;
    }    
}
