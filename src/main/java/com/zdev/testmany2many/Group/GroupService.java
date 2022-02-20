package com.zdev.testmany2many.Group;

import com.zdev.testmany2many.User.User;
import com.zdev.testmany2many.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserService userService;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public List<Group> getGroups(){return groupRepository.findAll(); }

    public void addNewGroup(Group group){ groupRepository.save(group);}



    @Transactional
    public void updateGroup(Group group){
        Group groupEdit = groupRepository.findById(group.getIdGroup()).orElseThrow(()-> new IllegalStateException("Group with id "+group.getIdGroup()+" does not exist."));
        groupEdit.setName(group.getName());

        /*
        * Users will be added or removed individually
        * */

    }

    @Transactional
    public void deleteGroup(Long idGroup){ groupRepository.deleteById(idGroup);}

    @Transactional
    public void addUsertoGroup(Long idGroup, Long idUser) {
        Group groupEdit = groupRepository.findById(idGroup).orElseThrow(()-> new IllegalStateException("Group with id "+idGroup+" does not exist"));
        groupEdit.addUser(userService.findUserbyId(idUser));
    }

    @Transactional
    public void removeUserfromGroup(Long idGroup, Long idUser) {
        Group groupEdit = groupRepository.findById(idGroup).orElseThrow(()-> new IllegalStateException("Group with id "+idGroup+" does not exist"));
        groupEdit.removeUser(userService.findUserbyId(idUser));
    }
}
