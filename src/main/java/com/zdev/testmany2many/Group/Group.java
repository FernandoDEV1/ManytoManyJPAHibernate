package com.zdev.testmany2many.Group;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zdev.testmany2many.User.CustomUserSerializer;
import com.zdev.testmany2many.User.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/*
* While naming entities make sure that names
* are not reserved words in mysql or errors may
* arise while manipulating tables
* (Group will create table group which is reserved word in mysql)
* */
@Entity
@Table(name = "group_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idGroup")
public class Group {
    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long idGroup;
    private String name;

    /*
    * FetchType set to EAGER since it is necessary
    * to add elements with commandlinerunner
    * join tables establishes name of foreign keys
    * in the shared table between both entities
    * CustomSerializer is used to avoid infinite
    * recursion problem while getting users
    *see -> https://stackoverflow.com/questions/41407921/eliminate-circular-json-in-java-spring-many-to-many-relationship
    * */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_user_mapping",
            joinColumns = { @JoinColumn(name = "fk_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_user_id")})
    @JsonSerialize(using = CustomUserSerializer.class)
    private Set<User> users = new HashSet<>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /*
    * Method added to update both entities
    * on adding users from groups
    * */
    public void addUser(User user){
        this.users.add(user);
        user.getGroups().add(this);
    }

    /*
     * Method added to update both entities
     * on removing users from groups, exception
     * thrown here since this is owning entity
     * and users will be added or removed from
     * this entity
     * */
    public void removeUser(User user) throws IllegalStateException {
        if(!this.users.contains(user)){
            throw new IllegalStateException("User "+user.getIdUser()+"is not a part this group.");
        }
        user.getGroups().remove(this);
        this.users.remove(user);

    }

    /*
    * Avoid printing to console elements with
    * many to many relationships when not properly
    * set from printing or StackOverflow exception
    * will be present
    * */
    @Override
    public String toString() {
        return "Group{" +
                "idGroup=" + idGroup +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
