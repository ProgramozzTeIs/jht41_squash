package pti.sb_squash_mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "games")
public class Game {
	
	@Id
	@Column(name= "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name= "useroneid")
	private User userOne;
	
	@Column(name= "usertwoid")
	private User userTwo;
	
	@Column(name= "useronepoints")
	private int useronepoint;
	
	@Column(name= "usertwopoints")
	private int usertwopoint;
	
	@Column(name= "location")
	private Location location;
	
	@Column (name= "date")
	private String date;
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUserOne() {
		return userOne;
	}

	public void setUserOne(User userOne) {
		this.userOne = userOne;
	}

	public User getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(User userTwo) {
		this.userTwo = userTwo;
	}

	public int getUseronepoint() {
		return useronepoint;
	}

	public void setUseronepoint(int useronepoint) {
		this.useronepoint = useronepoint;
	}

	public int getUsertwopoint() {
		return usertwopoint;
	}

	public void setUsertwopoint(int usertwopoint) {
		this.usertwopoint = usertwopoint;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", userOne=" + userOne + ", userTwo=" + userTwo + ", useronepoint=" + useronepoint
				+ ", usertwopoint=" + usertwopoint + ", location=" + location + ", date=" + date + "]";
	}
	
	
	
	
}
