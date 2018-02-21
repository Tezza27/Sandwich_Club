package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Identify all views form the Detail Activity that will be affected
        TextView labelAlsoKnownAs = (TextView) findViewById(R.id.label_also_known);
        TextView mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        TextView labelDescription = (TextView) findViewById(R.id.label_description);
        TextView mDescription = (TextView) findViewById(R.id.description_tv);
        TextView labelOrigin = (TextView) findViewById(R.id.label_origin);
        TextView mOrigin = (TextView) findViewById(R.id.origin_tv);
        TextView labelIngredients = (TextView) findViewById(R.id.label_ingredients);
        TextView mIngredients = (TextView) findViewById(R.id.ingredients_tv);

        //establish the list of alternative names for the selected sandwich
        List<String> listAlsoKnownAs = sandwich.getAlsoKnownAs();
        //convert the list to a comma separated string
        String strAlsoKnownAs = listToString(listAlsoKnownAs);
        //hide the known as label and text views if there are no alternative names
        if (strAlsoKnownAs.trim().equals("")) {
            labelAlsoKnownAs.setVisibility(View.GONE);
            mAlsoKnownAs.setVisibility(View.GONE);
        } else {
            //show the known as label and text views if there are alternative names
            labelAlsoKnownAs.setVisibility(View.VISIBLE);
            mAlsoKnownAs.setVisibility(View.VISIBLE);
            //populate the also known as textview
            mAlsoKnownAs.setText(strAlsoKnownAs);
        }

        //establish the description for the selected sandwich
        String strDescription = sandwich.getDescription();
        //hide the description label and text views if there are no alternative names
        if (strDescription.trim().equals("")) {
            labelDescription.setVisibility(View.GONE);
            mDescription.setVisibility(View.GONE);
        } else {
            //show the description label and text views if the description is available
            labelDescription.setVisibility(View.VISIBLE);
            mDescription.setVisibility(View.VISIBLE);
            //populate the description textview
            mDescription.setText(strDescription);
        }
        //establish the place of origin for the selected sandwich
        String strPlaceOfOrigin = sandwich.getPlaceOfOrigin();
        //hide the place of origin label and text views if there are no alternative names
        if (strPlaceOfOrigin.trim().equals("")) {
            labelOrigin.setVisibility(View.GONE);
            mOrigin.setVisibility(View.GONE);
        } else {
            //show the place of origin label and text views if the place of origin is available
            labelOrigin.setVisibility(View.VISIBLE);
            mOrigin.setVisibility(View.VISIBLE);
            //populate the place of origin textview
            mOrigin.setText(strPlaceOfOrigin);
        }

        //establish the list of ingredients for the selected sandwich
        List<String> listIngredients = sandwich.getIngredients();
        //convert the list to a comma separated string
        String strIngredients = listToString(listIngredients);
        //hide the ingredients label and text views if there are none available
        if (strIngredients.trim().equals("")) {
            labelIngredients.setVisibility(View.GONE);
            mIngredients.setVisibility(View.GONE);
        } else {
            //show the ingredients label and text views if they are available
            labelIngredients.setVisibility(View.VISIBLE);
            mIngredients.setVisibility(View.VISIBLE);
            //populate the ingredients textview
            mIngredients.setText(strIngredients);
        }
    }

    //converts lists to a comma separated string
    public String listToString(List<String> list) {
        return TextUtils.join(", ", list);
    }
}
