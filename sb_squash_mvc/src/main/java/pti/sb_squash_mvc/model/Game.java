package pti.sb_squash_mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name= "games")
public class Game {
	
	@Id
	@Column(name= "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name= "useroneid")
	private int userOneId;
	
	@Column(name= "usertwoid")
	private int userTwoId;
	
	@Column(name= "useronepoints")
	private int userOnePoint;
	
	@Column(name= "usertwopoints")
	private int userTwoPoint;
	
	@Column(name= "locationid")
	private int locationId;
	
	@Column (name= "date")
	private String date;
	
	@Transient
	private User userOne;
	
	@Transient
	private User userTwo;
	
	@Transient
	private Location location;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserOneId() {
		return userOneId;
	}

	public void setUserOneId(int userOneId) {
		this.userOneId = userOneId;
	}

	public int getUserTwoId() {
		return userTwoId;
	}

	public void setUserTwoId(int userTwoId) {
		this.userTwoId = userTwoId;
	}

	public int getUserOnePoint() {
		return userOnePoint;
	}

	public void setUserOnePoint(int userOnePoint) {
		this.userOnePoint = userOnePoint;
	}

	public int getUserTwoPoint() {
		return userTwoPoint;
	}

	public void setUserTwoPoint(int userTwoPoint) {
		this.userTwoPoint = userTwoPoint;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", userOneId=" + userOneId + ", userTwoId=" + userTwoId + ", userOnePoint="
				+ userOnePoint + ", userTwoPoint=" + userTwoPoint + ", locationId=" + locationId + ", date=" + date
				+ ", userOne=" + userOne + ", userTwo=" + userTwo + ", location=" + location + "]";
	}


	
	
	

	
	
	
}
