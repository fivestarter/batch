package ru.fivestarter.user.batch;

import ru.fivestarter.user.controller.UserJson;

import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<UserJson, UserJson> {

    @Override
    public UserJson process(UserJson userJson) {
        UserJson transformedUser = new UserJson();
        transformedUser.setFirstName(userJson.getFirstName() + "her");
        transformedUser.setLastName("her" + userJson.getLastName());
        return transformedUser;
    }
}
