package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {
    // 1. User list
    // 2. User create -> 회원 가입
    // 3. User update
    // 4. User delete -> level: 0 => 아무 것도 못 한다.
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getUsers();
        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(
        @RequestBody User resource
    ) throws URISyntaxException {
        String email = resource.getEmail();
        String name = resource.getName();
        User user = userService.addUser(email, name);

        String url = "/users/" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{userId}")
    public String update(
            @PathVariable("userId") Long id,
            @RequestBody User resource
    ){
        String email = resource.getEmail();
        String name = resource.getName();
        Long level = resource.getLevel();

        userService.updateUser(id, email, name, level);

        return "{}";
    }

    @DeleteMapping("/users/{userId}")
    public String delete(
            @PathVariable("userId") Long id
    ){
        userService.deactiveUser(id);
        return "{}";
    }
}
