package com.example.dbdesign_project.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // 회원가입 페이지
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@RequestParam String userName, @RequestParam String password, Model model) {
        int result = userDAO.registerUser(userName, password);
        if (result > 0) {
            model.addAttribute("message", "회원가입 성공!");
            return "login"; // 회원가입 성공 시 로그인 페이지로 이동
        } else {
            model.addAttribute("message", "회원가입 실패: 이미 존재하는 사용자입니다.");
            return "register"; // 실패 시 다시 회원가입 페이지로 이동
        }
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginUser(@RequestParam String userName, @RequestParam String password, Model model, HttpSession session) {
        boolean loginSuccess = userDAO.loginUser(userName, password);
        if (loginSuccess) {
            int userId = userDAO.getUserIdByUsername(userName); // userId 가져오기
            session.setAttribute("userId", userId); // 세션에 userId 저장
            model.addAttribute("message", "로그인 성공!");
            return "redirect:/playlists";
        } else {
            model.addAttribute("message", "로그인 실패: 사용자 이름 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }
}
