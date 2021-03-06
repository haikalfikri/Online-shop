package com.fikri.controller;

import com.fikri.dao.UserService;
import com.fikri.model.User;
import com.fikri.utils.PasswordDigest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserService us;
     @RequestMapping()
    public String goToLogin(Model model) {
        LoginBean loginBean = new LoginBean();
        model.addAttribute("loginBean", loginBean);
        return "login";
    }

    @RequestMapping(value = "/check")
    public String checkLogin(HttpSession session, @ModelAttribute("loginBean") LoginBean loginBean, Model model) {
        User user = us.findByUsername(loginBean.getUsername());
        if(user.getUsername()==null) {
            model.addAttribute("errMsg", "Username salah");
            return "login";
        }
        String encryptedPassword = PasswordDigest.createEncryptedPassword(loginBean.getPassword());
        if(!encryptedPassword.equals(user.getPassword())) {
            model.addAttribute("errMsg", "Password salah");
            return "login";
        }
        
        session.setAttribute("user", user);
        
        return "successlogin";
    }
}
