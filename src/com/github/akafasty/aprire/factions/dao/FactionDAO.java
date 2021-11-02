package com.github.akafasty.aprire.factions.dao;

import com.github.akafasty.aprire.factions.FactionData;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FactionDAO {

    public JSONObject factionToJson(@NonNull FactionData faction) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", faction.getName());
        jsonObject.put("tag", faction.getTag());

        return jsonObject;

    }

    /**
     * method used to save faction to file (TAG-Name.json)
     * @param faction to be saved
     */
    @SneakyThrows
    public void factionToFile(@NonNull FactionData faction) {

        File file = new File(String.format("aprire/factions/%s-%s.json", faction.getTag(), faction.getName()));
        JSONObject jsonObject = factionToJson(faction);

        if (file.exists()) file.createNewFile();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) { bufferedWriter.write(jsonObject.toJSONString()); }

    }

    @SneakyThrows
    public FactionData jsonToFaction(@NonNull String jsonString) {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

        return FactionData
                .builder()
                .name((String) jsonObject.get("name"))
                .tag((String) jsonObject.get("tag"))
                .build();

    }

}
