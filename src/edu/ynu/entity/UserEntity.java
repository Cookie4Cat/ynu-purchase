package edu.ynu.entity;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "purchase", catalog = "")
public class UserEntity {
	private String userId;
	private String username;
	private String password;
	private String name;
	private String academy;
	private Integer type;

	@Id
	@Column(name = "user_id", nullable = false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "username", nullable = false, length = 45)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "academy", nullable = false, length = 45)
	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	@Basic
	@Column(name = "type", nullable = true)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserEntity that = (UserEntity) o;

		if (!userId.equals(that.userId))
			return false;
		if (username != null ? !username.equals(that.username) : that.username != null)
			return false;
		if (password != null ? !password.equals(that.password) : that.password != null)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;
		if (academy != null ? !academy.equals(that.academy) : that.academy != null)
			return false;
		if (type != null ? !type.equals(that.type) : that.type != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = Integer.valueOf(userId);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (academy != null ? academy.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}
}
