package com.hoaxify_1.hoaxify_1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify_1.hoaxify_1.shared.GenericResponce;
import com.hoaxify_1.hoaxify_1.user.vm.UserVM;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/users")
	GenericResponce createUser(@Validated @RequestBody User user) {
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericResponce body = new GenericResponce();
		body.setMessage("user Seved");
		return body;
	}
	@GetMapping("/users")
	Page<UserVM> getUsers(@PageableDefault(size = 10)Pageable page) {
		return userService.getUsers(page).map((user ->new UserVM(user)));
		
		
	}
}
