package com.hope.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue
  @JsonProperty
  private Long id;

  @Column(nullable = false, length = 20, unique = true)
  @JsonProperty
  private String userId;

  @JsonIgnore
  private String password;

  @JsonProperty
  private String name;

  @JsonProperty
  private String email;

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public boolean matchPassword(String newPassword) {
    if (newPassword == null) {
      return false;
    }

    return newPassword.equals(password);
  }

  @Override
  public String toString() {
    return "User{" +
        "userId='" + userId + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }

  public void update(User newUser) {
    this.password = newUser.password;
    this.name = newUser.name;
    this.email = newUser.email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}
