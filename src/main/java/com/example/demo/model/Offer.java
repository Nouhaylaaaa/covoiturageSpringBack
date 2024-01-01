package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Offer {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String destination;
	    private String startingPoint;
	    private String dateDeparture;
	    private int numberOfPassengers;
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public String getStartingPoint() {
			return startingPoint;
		}
		public void setStartingPoint(String startingPoint) {
			this.startingPoint = startingPoint;
		}
		public String getDateDeparture() {
			return dateDeparture;
		}
		public void setDateDeparture(String dateDeparture) {
			this.dateDeparture = dateDeparture;
		}
		public int getNumberOfPassengers() {
			return numberOfPassengers;
		}
		public void setNumberOfPassengers(int numberOfPassengers) {
			this.numberOfPassengers = numberOfPassengers;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
	    

}
