package kr.co.torpedo.dataprocessor.domain;

import javax.persistence.Column;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name = "id", columnDefinition = "int")
	private int id;
	@Column(name = "first_name", columnDefinition = "varchar(100)")
	private String first_name;
	@Column(name = "last_name", columnDefinition = "varchar(100)")
	private String last_name;
	@Column(name = "email", columnDefinition = "varchar(200)")
	private String email;
	@Column(name = "gender", columnDefinition = "varchar(200)")
	private String gender;
	@Column(name = "ip_address", columnDefinition = "varchar(200)")
	private String ip_address;
	@Column(name = "date", columnDefinition = "date")
	private Date date;

	public User() {
	}
	
	public User(int id, String firstName, String lastName, String email, String gender, String ipAddress, Date date) {
		this.id = id;
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.gender = gender;
		this.ip_address = ipAddress;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIpAddress() {
		return ip_address;
	}

	public void setIpAddress(String ipAddress) {
		this.ip_address = ipAddress;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
