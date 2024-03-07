package gorest;

import gorest.methods.PostAPI;
import gorest.model.Post;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import gorest.model.User;
import gorest.methods.UserAPI;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import gorest.util.Gender;
import gorest.util.Status;

import static org.junit.Assert.assertTrue;

public class GoRestTest {
    private int userId;

    private final String bearer = "c43a2718b88e7198c181221c3118867936c7927d22d33e688a88cfca29aa09fd";

    public String createEmail(){
        return RandomStringUtils.randomAlphabetic(6) + "@gmail.com";
    }

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://gorest.co.in";
        RestAssured.basePath = "/public/v2";

        UserAPI userAPI = new UserAPI(bearer);

        User user = new User();
        user.setUserDetails("Nick".concat(RandomStringUtils.randomAlphabetic(3)), createEmail(), Gender.MALE, Status.ACTIVE);

        Response createUserResponse = userAPI.createUser(user);
        createUserResponse.prettyPrint();
        createUserResponse.then().assertThat().statusCode(201);
        createUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user/_post/rs.json"));

        userId = Integer.parseInt(createUserResponse.jsonPath().getString("id"));
    }

    @Test
    public void createUserTest() {
        UserAPI userAPI = new UserAPI(bearer);

        User user = new User();
        user.setUserDetails("Nick", createEmail(), Gender.MALE, Status.ACTIVE);

        Response createUserResponse = userAPI.createUser(user);
        createUserResponse.prettyPrint();
        createUserResponse.then().assertThat().statusCode(201);
        createUserResponse.then().assertThat().contentType(ContentType.JSON);
        createUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user/_post/rs.json"));
    }

    @Test(description = "negative")
    public void createUserInvalidBearerTest() {
        UserAPI userAPI = new UserAPI("invalid_bearer");

        User user = new User();
        user.setUserDetails("Nick", createEmail(), Gender.MALE, Status.ACTIVE);

        Response createUserResponse = userAPI.createUser(user);
        createUserResponse.then().assertThat().statusCode(401);
    }

    @Test
    public void updateUserTest() {
        UserAPI userAPI = new UserAPI(bearer);

        User user = new User();
        user.setUserDetails("Ken", createEmail(), Gender.MALE, Status.ACTIVE);

        Response updateUserResponse = userAPI.updateUser(userId, user);
        updateUserResponse.prettyPrint();
        updateUserResponse.then().assertThat().statusCode(200);
        updateUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user/_put/rs.json"));
    }

    @Test
    public void deleteUserTest() {
        UserAPI userAPI = new UserAPI(bearer);

        User user = new User();
        user.setUserDetails("John", createEmail(), Gender.MALE, Status.ACTIVE);

        Response createUserResponse = userAPI.createUser(user);
        createUserResponse.prettyPrint();
        createUserResponse.then().assertThat().statusCode(201);

        int userId = Integer.parseInt(createUserResponse.jsonPath().getString("id"));

        Response deleteUserResponse = userAPI.deleteUser(userId);
        deleteUserResponse.prettyPrint();
        deleteUserResponse.then().statusCode(204);
    }

    @Test
    public void getUserByIdTest() {
        UserAPI userAPI = new UserAPI(bearer);

        Response getUserResponse = userAPI.getUserById(userId);
        getUserResponse.prettyPrint();
        getUserResponse.then().assertThat().statusCode(200);
        getUserResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user/_post/rs.json"));
    }

    @Test
    public void getAllUsersTest() {
        UserAPI userAPI = new UserAPI(bearer);

        Response getUsersResponse = userAPI.getAllUsers();
        getUsersResponse.prettyPrint();
        getUsersResponse.then().assertThat().statusCode(200);
        getUsersResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user/_get/rs.json"));
    }

    @Test
    public void createUserPostTest() {
        PostAPI postAPI = new PostAPI(bearer);

        Post post = new Post();
        post.setPost("New post", "Test Post description here.");

        Response response = postAPI.createUserPost(userId, post);
        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        assertTrue(response.getBody().asString().contains("Test Post"));
    }

    @Test
    public void getPostCommentsTest() {
        PostAPI postAPI = new PostAPI(bearer);

        Post post = new Post();
        post.setPost("New post 2", "Test Post description here.");

        Response createPostResponse = postAPI.createUserPost(userId, post);
        createPostResponse.prettyPrint();
        createPostResponse.then().assertThat().statusCode(201);

        int postId = createPostResponse.jsonPath().getInt("id");

        Response response = postAPI.getPostComments(postId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
}
