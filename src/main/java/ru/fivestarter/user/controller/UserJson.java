package ru.fivestarter.user.controller;

import ru.fivestarter.user.dao.User;

public class UserJson {
    private String firstName;
    private String lastName;

    public UserJson() {
    }

    public UserJson(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User toUser () {
        User newUser = new User();
        newUser.setFirstName(getFirstName());
        newUser.setLastName(getLastName());
        return newUser;
    }
}
