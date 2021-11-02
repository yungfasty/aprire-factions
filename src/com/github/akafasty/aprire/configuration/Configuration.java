package com.github.akafasty.aprire.configuration;

import com.google.common.collect.Maps;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

public class Configuration {

    private Map<String, Object> values;

    public Configuration() { values = Maps.newHashMap(); }

    public void setValue(String key, Object value) { values.put(key, value); }

    public Object getValue(String key) { return values.get(key); }

    public void init() {
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File("aprire/configuration.yml"));

        setValue("start-power", fileConfiguration.getInt("users.start-power"));
        setValue("max-power", fileConfiguration.getInt("users.max-power"));
        setValue("regen-power", fileConfiguration.getInt("users.regen-power"));

    }

}
