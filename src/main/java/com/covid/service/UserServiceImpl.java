package com.covid.service;


import com.covid.config.ResourceNotFoundException;
import com.covid.document.User;
import com.covid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;




@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User updateUser(User user) {
        Optional<User> userDb = this.userRepository.findById(user.getId());

        if(userDb.isPresent()) {
            User userUpdate = userDb.get();
            userUpdate.setId(user.getId());
            userUpdate.setFullname(user.getFullname());
            userRepository.save(userUpdate);
            return userUpdate;
        }else {
            throw new ResourceNotFoundException("User not found with id : " + user.getId());
        }
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUserById(String userId) {
        Optional<User> userDb = this.userRepository.findById(userId);

        if(userDb.isPresent()) {
            return userDb.get();
        }else {
            throw new ResourceNotFoundException("User not found with id : " + userId);
        }
    }

    @Override
    public void deleteUserById(String userId) {
        Optional<User> userDb = this.userRepository.findById(userId);

        if(userDb.isPresent()) {
            this.userRepository.delete(userDb.get());
        }else {
            throw new ResourceNotFoundException("User not found with id : " + userId);
        }

    }

}




