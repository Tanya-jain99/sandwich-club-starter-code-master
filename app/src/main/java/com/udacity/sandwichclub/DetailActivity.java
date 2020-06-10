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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView mIngredientsView;
    private TextView moriginTextView,mAlsoKnowAsView,mIngredientsTextView,mDescription,mOrigin,knowTv;

    private Sandwich mSandwich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mOrigin=findViewById(R.id.origin);
        mIngredientsView = findViewById(R.id.image_iv);
        knowTv=findViewById(R.id.also_known_tv);
        moriginTextView = findViewById(R.id.origin_tv);
        mAlsoKnowAsView = findViewById(R.id.also_knownas_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mDescription = findViewById(R.id.description_tv);
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
         mSandwich = JsonUtils.parseSandwichJson(json);

        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(mIngredientsView);

        setTitle(mSandwich.getMainName());
        populateUI();



    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if(mSandwich.getAlsoKnownAs().isEmpty())
            knowTv.setVisibility(View.GONE);
        moriginTextView.setText(mSandwich.getPlaceOfOrigin());
        if(mSandwich.getPlaceOfOrigin().isEmpty())
            mOrigin.setVisibility(View.GONE);
        mAlsoKnowAsView.setText(TextUtils.join(", ", mSandwich.getAlsoKnownAs()));
        mIngredientsTextView.setText(TextUtils.join(", ", mSandwich.getIngredients()));
        mDescription.setText(mSandwich.getDescription());
    }
}
