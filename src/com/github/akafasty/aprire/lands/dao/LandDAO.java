package com.github.akafasty.aprire.lands.dao;

import com.github.akafasty.aprire.lands.Land;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashSet;
import java.util.Set;

public class LandDAO {

    /**
     * method used to parse an Set<Land> into an JsonObject
     * @param lands set to be parsed
     * @return lands parsed into an jsonobject
     */
    public JSONObject landsToJson(Set<Land> lands) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        lands.forEach(land -> jsonArray.add(toJSONObject(land)));

        jsonObject.put("lands", jsonArray);
        return jsonObject;

    }

    /**
     * method used to parse one land into an jsonobject
     * @param land to be parsed
     * @return land parsed into jsonobject
     */
    private JSONObject toJSONObject(Land land) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("faction", land.getFaction());
        jsonObject.put("world", land.getWorld());
        jsonObject.put("x", land.getX());
        jsonObject.put("z", land.getZ());
        return jsonObject;
    }

    /**
     * method used to convert an string into an land
     * @param string to be converted
     * @return land that corresponds to string
     */
    @SneakyThrows
    public Land jsonToLand(String string) {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(string);

        return Land.builder()
                .faction((String) jsonObject.get("faction"))
                .world((String) jsonObject.get("world"))
                .x(((Long) jsonObject.get("x")).intValue())
                .z(((Long) jsonObject.get("z")).intValue())
                .build();

    }

    /**
     * method used to convert an jsonboject into an Set<Land>
     * @param jsonString to be converted
     * @return jsonobject into Set<Land>
     */
    @SneakyThrows
    public Set<Land> jsonToLands(String jsonString) {

        HashSet<Land> landSet = Sets.newHashSet();

        if (jsonString == null || jsonString.isEmpty()) return landSet;

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

        if (jsonObject == null) return landSet;

        JSONArray jsonArray = (JSONArray) jsonObject.get("lands");

        jsonArray.forEach(each -> {

            JSONObject landObject = (JSONObject) each;

            landSet.add(Land.builder()
                    .world((String) jsonObject.get("world"))
                    .faction((String) jsonObject.get("faction"))
                    .x(((Long) jsonObject.get("x")).intValue())
                    .z(((Long) jsonObject.get("z")).intValue())
                    .build());

        });

        return landSet;

    }

}
