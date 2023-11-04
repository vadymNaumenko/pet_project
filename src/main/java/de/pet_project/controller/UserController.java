package de.pet_project.controller;

import de.pet_project.entity.User;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    public String cr(@PathVariable Integer id){
        User user = userService.findById(id);
        System.out.println("dssdds");
        return user.toString();
    }

    //JSON for method add
//    {
//        "email": "vagedym@gmail.com",
//            "password": "123456",
//            "firstname": "Vadym",
//            "lastname": "Naumenko",
//            "birth_date": "1992-02-05",
//            "avatar": null,
//            "role": "ADMIN",
//            "dateOfCreat": "2023-08-04"
//    }
    @PostMapping("/add")
    public String add(@RequestBody User user){
        userService.create(user);
        return userService.findById(user.getId()).toString();
    }
    @GetMapping("/all")
    public List<User> all(){

        return userService.findAll() ;
    }

}
