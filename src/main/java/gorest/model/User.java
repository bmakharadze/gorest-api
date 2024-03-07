package gorest.model;

import gorest.util.Gender;
import gorest.util.Status;

public class User {
    private Integer id;

    private String name;

    private String email;

    private Gender gender;

    private Status status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUserDetails(String name, String email, Gender gender, Status status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }
}
