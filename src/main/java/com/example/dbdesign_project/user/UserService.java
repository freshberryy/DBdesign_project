package com.example.dbdesign_project.user;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public boolean register(User user) {
        if (userRepository.findByUsername(user.getUserName()).isPresent()) {
            return false; // 사용자 이름 중복
        }
        userRepository.save(user);
        return true;
    }

    // 사용자 조회
    public Optional<User> findUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    // 로그인 처리 (단순 비교)
    public boolean login(String userName, String password) {
        return userRepository.findByUsername(userName)
                .filter(user -> user.getPassword().equals(password))
                .isPresent();
    }
}
