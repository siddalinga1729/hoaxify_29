package com.hoaxify_1.hoaxify_1.user.vm;

import com.hoaxify_1.hoaxify_1.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVM {// view model alternative solution for json
	// it is like DTO layer
	private long id;
	private String userName;
	private String displayName;
	private String image;

	public UserVM(User user) {
		this.setId(user.getId());
		this.setUserName(user.getUsername());
		this.setDisplayName(user.getDisplayName());
		this.setImage(user.getImage());
	}
}
