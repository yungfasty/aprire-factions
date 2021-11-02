package com.github.akafasty.aprire.users.controller;

import com.github.akafasty.aprire.main.FactionsPlugin;
import com.github.akafasty.aprire.users.User;
import com.github.akafasty.aprire.users.dao.UserDAO;
import com.github.yungfasty.fastcaching.collections.FastCollection;
import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {

    private FastCollection<User> USERS_DATA;

    public UserController() {

        USERS_DATA = new FastCollection<User>(5000);

    }

    /**
     * method used to fetch an user by name
     * @param name to be searched
     * @return user that matches with name.
     */
    public User fetchByName(@NonNull String name) {

        return USERS_DATA.fetch(name);

    }

    /**
     * method used to cache an user
     * @param user to be inserted
     */
    public void insert(@NonNull User user) { USERS_DATA.put(user.getName(), user); }

    /**
     * method used to remove a user from the cache
     * @param name to be removed
     */
    public void remove(@NonNull String name) { USERS_DATA.remove(name); }

    /**
     * method used to check if player exists in storage
     * @param name user's name
     * @return boolean that corresponds
     */
    public boolean exists(@NonNull String name) { return new File(String.format("aprire/users/%s.json", name)).exists(); }

    /**
     * method used to load an exact user
     * @param name user name
     */
    @SneakyThrows
    public void load(@NonNull String name) {

        File file = new File(String.format("aprire/users/%s.json", name));
        UserDAO userDAO = FactionsPlugin.getPlugin().getUserDAO();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        User user = userDAO.jsonToUser(file.getName().replace(".json", ""), bufferedReader.readLine(), false);

        if (user != null) USERS_DATA.put(user.getName(), user);

        bufferedReader.close();

    }

    /**
     * load all users
     */
    @SneakyThrows
    public void init() {

        UserDAO userDAO = FactionsPlugin.getPlugin().getUserDAO();

        Arrays.asList(new File("aprire/users").listFiles()).forEach(file -> {

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

                User user;

                if ((user = userDAO.jsonToUser(file.getName().replace(".json", ""), bufferedReader.readLine(), true)) != null) USERS_DATA.put(user.getName(), user);

            } catch (Exception exception) { exception.printStackTrace(); }

        });

        System.out.println(String.format("carregados %s users.", USERS_DATA.size()));

    }

    @SneakyThrows
    public void saveAll() {

        UserDAO userDAO = FactionsPlugin.getPlugin().getUserDAO();

        USERS_DATA.forEach(userDAO::userToFile);

    }

    /**
     * method used to list all faction members by tag
     * @param faction tag to sort
     * @return collection with all faction members
     */
    public List<User> fetchMembersByTag(@NonNull String faction) {

        return USERS_DATA.stream()
                .filter(context -> context.getFactionTag() != null)
                .filter(context -> context.getFactionTag().equalsIgnoreCase(faction))
                .collect(Collectors.toList());

    }

}
