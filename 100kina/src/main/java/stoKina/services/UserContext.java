package stoKina.services;

import java.io.Serializable;

import stoKina.model.User;

public class UserContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5185469629320384569L;
	
	private User currentUser;
	
	public User getCurrentUser(){
		return currentUser;
	}
	
	public void setCurrentUser(User newUser){
		this.currentUser = newUser;
	}
}
