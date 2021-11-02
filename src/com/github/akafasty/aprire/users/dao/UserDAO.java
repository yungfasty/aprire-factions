package com.github.akafasty.aprire.users.dao;

import com.github.akafasty.aprire.users.User;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class UserDAO {

    /**
     * method used to convert a user into an json object
     * @param user to be converted
     * @return json object that matches to user
     */
    public JSONObject userToJson(@NonNull User user) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("faction", user.getFactionTag());
        jsonObject.put("role", user.getRoleStr());
        jsonObject.put("power", user.getPower());
        jsonObject.put("powerMax", user.getPowerMax());
        jsonObject.put("kills", user.getKills());
        jsonObject.put("deaths", user.getDeaths());
        jsonObject.put("lastActivity", user.getLastActivity());

        return jsonObject;

    }

    /**
     * method used to save user to file (username.json)
     * @param user to be saved
     */
    @SneakyThrows
    public void userToFile(@NonNull User user) {

        File file = new File(String.format("aprire/users/%s.json", user.getName()));
        JSONObject jsonObject = userToJson(user);

        if (file.exists()) file.createNewFile();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) { bufferedWriter.write(jsonObject.toJSONString()); }

    }

    /**
     * method used to convert a string into an user.
     * @param name user's name
     * @param jsonString raw string to be parsed into json
     * @return string converted into User.java
     */
    @SneakyThrows
    public User jsonToUser(String name, String jsonString, boolean startup) {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

        if (startup && jsonObject.get("faction") == null) return null;

        return User
                .builder()
                .name(name)
                .invites(Lists.newArrayList())
                .factionTag((String) jsonObject.get("faction"))
                .roleStr((String) jsonObject.get("role"))
                .power((double) jsonObject.get("power"))
                .powerMax((double) jsonObject.get("powerMax"))
                .kills(((Long) jsonObject.get("kills")).intValue())
                .deaths(((Long) jsonObject.get("deaths")).intValue())
                .lastActivity((long) jsonObject.get("lastActivity"))
                .build();

    }

}
