package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            //Extract the sandwich name from the JsonObject
            String mainName = name.getString("mainName");
            //Extract the sandwich's alternative names from the JsonObject
            JSONArray alsoKnownAsJson = name.getJSONArray("alsoKnownAs");
            //convert the array of alternative sandwich names to a list
            List<String> alsoKnownAs = arrayToList(alsoKnownAsJson);
            //Extract the sandwich's place of origin from the JsonObject
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            //Extract the sandwich's description from the JsonObject
            String description = jsonObject.getString("description");
            //Extract the sandwich's image URL from the JsonObject
            String image = jsonObject.getString("image");
            //Extract the sandwich's ingredients from the JsonObject
            JSONArray ingredientsJson = jsonObject.getJSONArray("ingredients");
            //convert the array of ingredients to a list
            List<String> ingredients = arrayToList(ingredientsJson);
            //construct the sandwich object
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            //on error, construct error log
            Log.e(TAG, "Error occurred while parsing JSON", e);
        }
        return sandwich;
    }

    //converts an array to a list
    private static List<String> arrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
