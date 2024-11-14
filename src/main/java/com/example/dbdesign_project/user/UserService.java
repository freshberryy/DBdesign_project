package com.example.dbdesign_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 새로운 사용자 등록 메서드
    public String registerUser(String userName, String password) {
        // 사용자 이름 중복 확인
        try {
            if (userRepository.findUserByName(userName) != null) {
                return "이미 존재하는 사용자 이름입니다.";
            }
        } catch (Exception e) {
            // userName으로 조회할 때 결과가 없으면 예외 발생; 신규 사용자로 간주
        }

        // 사용자 등록
        int result = userRepository.addUser(userName, password);
        return result == 1 ? "사용자 등록 성공" : "사용자 등록 실패";
    }

    // ID로 사용자 조회 메서드
    public User getUserById(int userId) {
        return userRepository.findUserById(userId);
    }
}
