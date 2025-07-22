package com.sasika.mcq.service;

import com.sasika.mcq.dto.UserDTO;
import com.sasika.mcq.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;



public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);
}
