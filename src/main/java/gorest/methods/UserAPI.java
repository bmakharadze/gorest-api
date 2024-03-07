package gorest.methods;


import gorest.model.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserAPI {

    public static final String GET_JSON_SCHEMA_PATH = "/test/resources/user/_get/rs.json";

    private String bearer;

    public UserAPI(String bearer) {
        this.bearer = bearer;
    }

    public Response createUser(User user) {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body(user)
                .post("/users");
    }

    public Response updateUser(int userId, User user) {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body(user)
                .put("/users/" + userId);
    }

    public Response deleteUser(int userId) {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .delete("/users/" + userId);
    }

    public Response getUserById(int userId) {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .when()
                .get("/users/" + userId);
    }
    public Response getAllUsers() {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .when()
                .get("/users");
    }
}
