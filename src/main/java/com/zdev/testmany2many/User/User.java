package com.zdev.testmany2many.User;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zdev.testmany2many.Group.CustomGroupSerializer;
import com.zdev.testmany2many.Group.Group;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long idUser;
    private String username;
    private String password;
    private String email;

    /*
    * While defining bidirectional many to many relationships
    * the non owning side will add mappedBy to the ManytoMany
    * annotation with the name of the set or list on the
    * owning class
    * */
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "users")
    @JsonSerialize(using = CustomGroupSerializer.class)
    private Set<Group> groups = new HashSet<>();


    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", groups=" + groups +
                '}';
    }
}
