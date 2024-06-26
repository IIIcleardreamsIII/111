package com.example.myblog.demos.Service;

import com.example.myblog.demos.Repository.TUserRepository;
import com.example.myblog.demos.pojo.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private TUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // 存储用户时，使用encodePassword对密码进行加密后存储
    public void saveUser(TUser user) {
        if (user.getPassword() != null) {
            user.setPassword(encodePassword(user.getPassword()));
        }else {
            user.setPassword(encodePassword("123456"));
        }
        userRepository.save(user);
    }

    public Optional<TUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<TUser> findAllUsers() {
        return userRepository.findAll();
    }

    public TUser authenticate(String username, String rawPassword) {
        Optional<TUser> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> {
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user;
            }
            return null;
        }).orElse(null);
    }
}