package com.portal.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;


@Entity
public class Blogger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
/*	@OneToMany(cascade=CascadeType.ALL, mappedBy="blogger")
	//@JoinColumn(name="BLOGGER_ID")
	private Collection<Post> postList = new LinkedHashSet<Post>();*/
	
	@ManyToOne
	@JoinColumn(name="GROUP_ID")
	private Group group;
	
	@NotEmpty
	private String login;

	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	@Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
			message="Invalid email address.")
	private String email;

	@Range(min = 2, max = 150)
	private String password;
	
	private Date lastUpdated;
	private boolean isSubscribedByEmail;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUpdateByEmail() {
		return isSubscribedByEmail;
	}

	public void setUpdateByEmail(boolean isSubscribedByEmail) {
		this.isSubscribedByEmail = isSubscribedByEmail;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

/*	public Collection<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}*/
}
