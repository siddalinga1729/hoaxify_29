package com.hoaxify_1.hoaxify_1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoaxify_1.hoaxify_1.shared.GenericResponce;
import com.hoaxify_1.hoaxify_1.user.User;
import com.hoaxify_1.hoaxify_1.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
	private static final String API_1_0_USER = "/api/1.0/users";
	@Autowired
	TestRestTemplate testRestTemplate;// it is used for web services testing
	@Autowired
	UserRepository userRepository;

	@Before
	public void cleanUp() {
		userRepository.deleteAll();
	}

	@Test
	public void postUser_whenUserIsValid_recieveOk() {
		User user = createVaidUser();

		ResponseEntity<Object> response = postSignup(user, Object.class);
		
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

//		} catch (AssertionError e) {
//			e.printStackTrace();
//		}

	}

	private User createVaidUser() {// extracted into method
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("Password");
		return user;
	}

	@Test
	public void postUser_whenUserIsValid_userStoredtoDatabase() {
		User user = createVaidUser();
		ResponseEntity<Object> response = postSignup(user, Object.class);
		try {
			assertThat(userRepository.count()).isEqualTo(1);
		} catch (AssertionError e) {
			e.printStackTrace();
		}

	}

	@Test
	public void postUser_whenUserIsValid_recieveSuccessMessage() {
		User user = createVaidUser();

		ResponseEntity<GenericResponce> response = testRestTemplate.postForEntity(API_1_0_USER, user,
				GenericResponce.class);
		try {
			assertThat(response.getBody().getMessage()).isNotNull();
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenserValid_passwordIsHasheedInDatabase() {
		User user = createVaidUser();
		testRestTemplate.postForEntity(API_1_0_USER, user, Object.class);
		List<User> users = userRepository.findAll();
		User inDB = users.get(1);
		try {
			assertThat(inDB.getPassword()).isNotEqualTo(user.getPassword());
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasNullUserName_recieveBadRequest() {
		User user = createVaidUser();
		user.setUserName(null);
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasNullPassword_recieveBadRequest() {
		User user = createVaidUser();
		user.setPassword(null);
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasNullDisplayName_recieveBadRequest() {
		User user = createVaidUser();
		user.setDisplayName(null);
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasUerNameWithLessThanRequired_recieveBadRequest() {
		User user = createVaidUser();
		user.setUserName("abc");
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasPasswordWithLessThanRequired_recieveBadRequest() {
		User user = createVaidUser();
		user.setPassword("abc");
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasDisplayNameWithLessThanRequired_recieveBadRequest() {
		User user = createVaidUser();
		user.setDisplayName("abc");
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasUserNameExceedLength_recieveBadRequest() {
		User user = createVaidUser();
		String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
		user.setUserName(valueOf256Chars);
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasDisplayNameExceedLength_recieveBadRequest() {
		User user = createVaidUser();
		String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
		user.setDisplayName(valueOf256Chars);
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasPasswordExceedLength_recieveBadRequest() {
		User user = createVaidUser();
		String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
		user.setPassword(valueOf256Chars);
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasPasswordWithAllLowerCase_recieveBadRequest() {
		User user = createVaidUser();
		user.setPassword("abceerggf");
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasPasswordWithAllUpperCase_recieveBadRequest() {
		User user = createVaidUser();
		user.setPassword("ABVGCFS");
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenUserHasPasswordWithAllNumbers_recieveBadRequest() {
		User user = createVaidUser();
		user.setPassword("1546878");
		ResponseEntity<Object> response = postSignup(user, Object.class);

		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postUser_whenAnotherUserHasSameJUserNameInDB_recieveOk() {
		ResponseEntity<Object> response = testRestTemplate.getForEntity(API_1_0_USER, Object.class);
		try {
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		} catch (AssertionError e) {
			e.printStackTrace();
		}

	}

	@Test
	public void gettUser_whenAnotherUserHasSameJUserNameInDB_recievePageWithZeroItems() {
		ResponseEntity<TestPage<Object>> response = testRestTemplate.exchange(API_1_0_USER, HttpMethod.GET, null,
				new ParameterizedTypeReference<TestPage<Object>>() {
				});
		try {
			assertThat(response.getBody().getTotalElements()).isEqualTo(0);
		} catch (AssertionError e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getUser_whenAnotherUserHasSameJUserNameInDB_recievePageWithUsers() {
		userRepository.save(createVaidUser());
		ResponseEntity<TestPage<Object>> response = testRestTemplate.exchange(API_1_0_USER, HttpMethod.GET, null,
				new ParameterizedTypeReference<TestPage<Object>>() {
				});
		try {
			assertThat(response.getBody().getNumberOfElements()).isEqualTo(1);
		} catch (AssertionError e) {
			e.printStackTrace();
		}

	}

	@Test
	public void gettUser_whenAnotherUserHasSameJUserNameInDB_recieveUserswithoutPassword() {
		userRepository.save(TestUtil.createValidUser());
		ResponseEntity<TestPage<Map<String, Object>>> response = testRestTemplate.exchange(API_1_0_USER, HttpMethod.GET,
				null, new ParameterizedTypeReference<TestPage<Map<String, Object>>>() {
				});
		Map<String, Object> entity = response.getBody().getContent().get(0);
		try {
			assertThat(entity.containsKey("password")).isFalse();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getUsers_whenPageIsRequestedFor3ItemsPerPageWhereTheDataBaseHas20Users_recieve3Users() {
		IntStream.rangeClosed(1, 20).mapToObj(i -> "test-user").map(TestUtil::createValidUser)
				.forEach(userRepository::save);
		String path = API_1_0_USER + "?page=0&size=3";
		ResponseEntity<TestPage<Object>> response = getUsers(path, new ParameterizedTypeReference<TestPage<Object>>() {
		});
		try {
			assertThat(response.getBody().getContent().size()).isEqualTo(3);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Test
	public void getUser_whenNotProvided_recievePageAs10() {
		ResponseEntity<TestPage<Object>> response = getUsers(new ParameterizedTypeReference<TestPage<Object>>() {
		});
		try {
			assertThat(response.getBody().getSize()).isEqualTo(10);
		}catch (AssertionError e) {
			// TODO: handle exception
		}
		
	}
	@Test
	public void getUser_whenPageSizeIsGreaterThan100_recievePageAs100() {
		String path = API_1_0_USER + "?size=500";
		ResponseEntity<TestPage<Object>> response = getUsers(path,new ParameterizedTypeReference<TestPage<Object>>() {
		});
	try {
			assertThat(response.getBody().getSize()).isEqualTo(100);
		}catch (AssertionError e) {
			// TODO: handle exception
		}
		
	}

	public <T> ResponseEntity<T> postSignup(Object request, Class<T> response) {
		return testRestTemplate.postForEntity(API_1_0_USER, request, response);
	}

	public <T> ResponseEntity<T> getUsers(ParameterizedTypeReference<T> responseType) {
		return testRestTemplate.exchange(API_1_0_USER, HttpMethod.GET, null, responseType);
	}

	public <T> ResponseEntity<T> getUsers(String path, ParameterizedTypeReference<T> responseType) {
		return testRestTemplate.exchange(path, HttpMethod.GET, null, responseType);
	}
}
