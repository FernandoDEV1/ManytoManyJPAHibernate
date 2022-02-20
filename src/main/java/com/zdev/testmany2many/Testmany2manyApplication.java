package com.zdev.testmany2many;

import com.zdev.testmany2many.Group.Group;
import com.zdev.testmany2many.Group.GroupService;
import com.zdev.testmany2many.User.User;
import com.zdev.testmany2many.User.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Testmany2manyApplication {

	public static void main(String[] args) {
		SpringApplication.run(Testmany2manyApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService, GroupService groupService){
		return args -> {
			userService.addNewUser(new User("user1","pass","user1@mail.com"));
			userService.addNewUser(new User("user2","pass","user2@mail.com"));
			userService.addNewUser(new User("user3","pass","user3@mail.com"));
			userService.addNewUser(new User("user4","pass","user4@mail.com"));
			userService.addNewUser(new User("user5","pass","user5@mail.com"));
			userService.addNewUser(new User("user6","pass","user6@mail.com"));
			userService.addNewUser(new User("user7","pass","user7@mail.com"));
			userService.addNewUser(new User("user8","pass","user8@mail.com"));


			groupService.addNewGroup(new Group("Group 1"));
			groupService.addNewGroup(new Group("Group 2"));
			groupService.addNewGroup(new Group("Group 3"));
			groupService.addNewGroup(new Group("Group 4"));



		};

	}

}
