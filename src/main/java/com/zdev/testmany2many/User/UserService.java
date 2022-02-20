package com.zdev.testmany2many.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){return userRepository.findAll(); }

    public void addNewUser(User user){ userRepository.save(user); }

    public User findUserbyId(Long idUser){ return userRepository.findById(idUser).orElseThrow(()->new IllegalStateException("User with id "+idUser+" does not exists."));}

    @Transactional
    public void updateUser(User user){
        User userEdit = userRepository.findById(user.getIdUser()).orElseThrow(()->new IllegalStateException("User with id "+user.getIdUser()+" does not exists."));

        userEdit.setUsername(user.getUsername());
        userEdit.setEmail(user.getEmail());
        userEdit.setPassword(user.getPassword());

    }

    public void deleteUser(Long idUser){ userRepository.deleteById(idUser);}
}
