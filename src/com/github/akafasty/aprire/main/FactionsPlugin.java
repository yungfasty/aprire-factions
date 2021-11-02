package com.github.akafasty.aprire.main;

import com.github.akafasty.aprire.commands.FactionCommand;
import com.github.akafasty.aprire.configuration.Configuration;
import com.github.akafasty.aprire.factions.controller.FactionController;
import com.github.akafasty.aprire.factions.dao.FactionDAO;
import com.github.akafasty.aprire.lands.controller.LandController;
import com.github.akafasty.aprire.lands.dao.LandDAO;
import com.github.akafasty.aprire.managers.ListenerManager;
import com.github.akafasty.aprire.users.User;
import com.github.akafasty.aprire.users.controller.UserController;
import com.github.akafasty.aprire.users.dao.UserDAO;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Collection;

@Getter
public class FactionsPlugin extends JavaPlugin {

    private static FactionsPlugin plugin;
    private UserDAO userDAO;
    private UserController userController;
    private FactionDAO factionDAO;
    private FactionController factionController;
    private LandDAO landDAO;
    private LandController landController;
    private Configuration configuration;

    public void onEnable() {

        makeDirs();

        plugin = this;
        configuration = new Configuration();

        landDAO = new LandDAO();
        landController = new LandController();
        userDAO = new UserDAO();
        userController = new UserController();
        factionDAO = new FactionDAO();
        factionController = new FactionController();

        landController.init();
        configuration.init();
        userController.init();
        factionController.init();
        ListenerManager.init();

        new FactionCommand();

    }

    public static void main(String[] arguments) {

        Collection<User> usersData = Lists.newLinkedList();

        for (int index = 0; index < 145; index++) usersData.add(User.builder()
                .name(String.format("User%s", index))
                .lastActivity(15)
                .power(15)
                .kills(15)
                .deaths(15)
                .powerMax(15)
                .build());

        usersData.add(User.builder()
                .name("FasTy")
                .build());

        long before = System.currentTimeMillis();
        User user = usersData.stream()
                .filter(context -> context.getName().equalsIgnoreCase("FasTy"))
                .findFirst().orElse(null);
        long after = System.currentTimeMillis();

        System.out.println(String.format("%s | Collection. (%sms | %sb)", user.getName(), after-before, usersData.toString().length()));

        FastCollection<User> fastCollection = new FastCollection<User>(150);

        for (int index = 0; index < 145; index++) fastCollection.put(String.format("User%s", index), User.builder()
                .name(String.format("User%s", index))
                .lastActivity(15)
                .power(15)
                .kills(15)
                .deaths(15)
                .powerMax(15)
                .build());

        fastCollection.put("FasTy", User.builder()
                .name("FasTy")
                .build());

        before = System.currentTimeMillis();
        user = fastCollection.fetch("FasTy");
        after = System.currentTimeMillis();

        System.out.println(String.format("%s | FastCollection. (%sms | %sb)", user.getName(), after-before, fastCollection.usage()));

    }

    public void onDisable() {

        userController.saveAll();
        factionController.saveAll();
        //landController.

        HandlerList.unregisterAll(this);

    }

    public static FactionsPlugin getPlugin() { return plugin; }

    @SneakyThrows
    void makeDirs() {

        new File("aprire/factions").mkdirs();
        new File("aprire/users").mkdirs();
        new File("aprire/lands").mkdirs();

        File file = new File("aprire", "configuration.yml");

        if (!file.exists()) {

            InputStream in = getResource("configuration.yml");

            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];

            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();

            System.out.println("configuration.yml created!");

        }
    }

}
