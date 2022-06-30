package com.covid.service;


import com.covid.document.User;

import java.util.List;

public interface UserService {
    //User createUser(User person);
    User updateUser(User person);
    List<User> getAllUsers();
    User getUserById(String personId);
    void deleteUserById(String personId);
}

