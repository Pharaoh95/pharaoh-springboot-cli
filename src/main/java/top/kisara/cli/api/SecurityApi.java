package top.kisara.cli.api;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SecurityApi {

    @PostMapping("/login")
    public String login(@RequestBody HashMap<String, String> map) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                map.get("username"), map.get("password"));
        try {
            subject.login(usernamePasswordToken);
        } catch (RuntimeException re) {
            return "ex:" + re.getMessage();
        }
        return "login success";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout success";
    }


}
