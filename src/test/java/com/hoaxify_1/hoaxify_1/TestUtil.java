package com.hoaxify_1.hoaxify_1;

import com.hoaxify_1.hoaxify_1.user.User;

public class TestUtil {

	public static User createValidUser() {
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("P4ssword");
		user.setImage("profile-image.png");
		return user;
	}
	
	public static User createValidUser(String username) {
		User user = createValidUser();
		user.setUserName(username);
		return user;
	}
	
//	public static Hoax createValidHoax() {
//		Hoax hoax = new Hoax();
//		hoax.setContent("test content for the test hoax");
//		return hoax;
//	}
}
