# ManytoManyJPAHibernate
Many to Many implementation with Spring Boot, JPA and Hiberate

Project example of many to many relationship implementation in Spring Boot using JPA and Hibernate.




### Defining the user entity
```java

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
    
    // .. omitted constructor,getter,setters and other methods from brevity

}

```


### Defining the group entity
```java

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
    
    // .. omitted constructor,getter,setters and other methods from brevity

}

```

### Custom serializer annotation

Serializer obtained by this answer ---> [Answer](https://stackoverflow.com/questions/41407921/eliminate-circular-json-in-java-spring-many-to-many-relationship)
```java
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_user_mapping",
        joinColumns = { @JoinColumn(name = "fk_group_id") },
        inverseJoinColumns = { @JoinColumn(name = "fk_user_id")})
    @JsonSerialize(using = CustomUserSerializer.class)
    private Set<User> users = new HashSet<>();
```

### Json get request with serializer
#### Formatted json objects
##### User
```json
{
        "idUser": 4,
        "username": "user4",
        "password": "pass",
        "email": "user4@mail.com",
        "groups": [
            9,
            10
        ]
    }
```
##### Group
```json
{
  "idGroup": 10,
  "name": "Group 2",
  "users": [
    {
      "idUser": 6,
      "username": "user6",
      "password": "pass",
      "email": "user6@mail.com",
      "groups": null
    },
    {
      "idUser": 4,
      "username": "user4",
      "password": "pass",
      "email": "user4@mail.com",
      "groups": null
    }
  ]
}
```