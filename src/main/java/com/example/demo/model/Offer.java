package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Offer {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String destination;
	    private String startingPoint;
	    @Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern = "dd/MM/yyyy")
	    @JsonFormat(pattern = "dd/MM/yyyy")
	    private Date dateDeparture;
	    private int numberOfPassengers;
	    private String vehicule;
	    private double price;
	    private Boolean book = false;
	   
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
	    
	    @ManyToOne
	    @JoinColumn(name = "userBooker_id")
	    private User userBooker =null;
	    
	    
	    
	    public User getUserBooker() {
			return userBooker;
		}

		public void setUserBooker(User userBooker) {
			this.userBooker = userBooker;
		}

		public Boolean getBook() {
			return book;
		}

		public void setBook(Boolean book) {
			this.book = book;
		}

		public String getDateDepartureAsString() {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        return sdf.format(this.dateDeparture);
	    }
	    
	    public String getUserEmail() {
	        return this.user != null ? this.user.getEmail() : null;
	    }
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
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
		
		public Date getDateDeparture() {
			return dateDeparture;
		}
		public void setDateDeparture(Date dateDeparture) {
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
		public String getVehicule() {
			return vehicule;
		}
		public void setVehicule(String vehicule) {
			this.vehicule = vehicule;
		}
	    
		

}
