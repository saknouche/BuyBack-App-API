package com.projet.buyback.service.user;

import com.projet.buyback.model.User;
import com.projet.buyback.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User update(User user) {
        return userRepository.save(user);
    }
}
