package com.sasika.mcq.service.impl;

import com.sasika.mcq.dto.UserDTO;
import com.sasika.mcq.entity.User;
import com.sasika.mcq.repo.UserRepository;
import com.sasika.mcq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with Id"+id));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found with email"+email));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = modelMapper.map(userDTO, User.class);
        User saveUser = userRepository.save(user);
        return modelMapper.map(saveUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existUser = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with Id"+id));
        existUser.setEmail(userDTO.getEmail());
        existUser.setName(userDTO.getName());
        User saveUser = userRepository.save(existUser);
        return modelMapper.map(saveUser, UserDTO.class);
    }

    @Override
    public void deleteUserById(Long id) {
        User existUser = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with Id"+id));
        userRepository.delete(existUser);
    }
}
