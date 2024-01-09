package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Feedback {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 private String feedback;
	 @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
	    
	    @ManyToOne
	    @JoinColumn(name = "offer_id")
	    private Offer offer;
	    

		public String getFeedback() {
			return feedback;
		}

		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Offer getOffer() {
			return offer;
		}

		public void setOffer(Offer offer) {
			this.offer = offer;
		}
	    
	    

}
