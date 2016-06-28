package stoKina.services;

import stoKina.model.Ticket;


public class CountDownThread extends Thread{
	public static final int millsToSleep = 2000;
	
	private Ticket reservedTicket;
	
	private TicketMaster tm;
	
	public CountDownThread(Ticket ticketToReserve , TicketMaster ticketManager){
		this.reservedTicket = ticketToReserve;
		this.tm = ticketManager;
	}
	
	public void run(){
		try {
			Thread.sleep(millsToSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(tm.isTicketReserved(this.reservedTicket)){
			//remove
			tm.removeTicket(this.reservedTicket);
		}
	}

}
