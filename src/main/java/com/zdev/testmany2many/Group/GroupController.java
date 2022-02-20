package com.zdev.testmany2many.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getGroups(){
        return groupService.getGroups();
    }

    @PostMapping
    public void registerNewGroup(@RequestBody Group group){
        groupService.addNewGroup(group);
    }

    @DeleteMapping(path = "{groupId}")
    public void deleteGroup(@PathVariable("groupId") Long idGroup){
        groupService.deleteGroup(idGroup);
    }

    @PutMapping(path = "{groupId}")
    public void updateGroup(@RequestBody Group group){
        groupService.updateGroup(group);
    }

    @PutMapping(path = "adduser/{groupId}/{userId}")
    public void addUsertoGroup(@PathVariable("groupId") Long idGroup, @PathVariable("userId") Long idUser){
        groupService.addUsertoGroup(idGroup,idUser);
    }

    @PutMapping(path = "removeuser/{groupId}/{userId}")
    public void removeUserfromGroup(@PathVariable("groupId") Long idGroup, @PathVariable("userId") Long idUser){
        groupService.removeUserfromGroup(idGroup,idUser);
    }

}
