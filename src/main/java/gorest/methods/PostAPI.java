package gorest.methods;

import gorest.model.Post;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostAPI {

    private String bearer;

    public PostAPI(String bearer) {
        this.bearer = bearer;
    }

    public Response createUserPost(int userId, Post post) {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body(post)
                .post("/users/" + userId + "/posts");
    }

    public Response getPostComments(int postId) {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .when()
                .get("/posts/" + postId + "/comments");
    }

    public Response createUserTodo(int userId, Post todo) {
        return given()
                .header("Authorization", "Bearer " + bearer)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body(todo)
                .post("/users/" + userId + "/todos");
    }
}
