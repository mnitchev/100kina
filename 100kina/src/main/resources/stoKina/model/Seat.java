package stoKina.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Seat {
    //от този масив се определя seatType-а, идеята е с индексиране да виждаме дали е свободен, резервиран или зает
	private static final String[] type= {"FREE", "RESERVED", "TAKEN" };
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
