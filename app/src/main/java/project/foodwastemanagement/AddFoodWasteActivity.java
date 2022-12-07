package project.foodwastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import io.objectbox.Box;
import project.foodwastemanagement.database.FoodWaste;
import project.foodwastemanagement.database.Location;
import project.foodwastemanagement.database.Location_;
import project.foodwastemanagement.database.ObjectBox;
import project.foodwastemanagement.database.User;

public class AddFoodWasteActivity extends AppCompatActivity {

    boolean allGood = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = this.getIntent().getLongExtra("user_id", -1);
        Box<User> userBox = ObjectBox.get().boxFor(User.class);
        User user = userBox.get(id);
        if (user == null)
            finish();

        assert user != null;
        setContentView(R.layout.activity_add_food_waste);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(view -> {
            Box<FoodWaste> foodWasteBox = ObjectBox.get().boxFor(FoodWaste.class);
            Box<Location> locationBox = ObjectBox.get().boxFor(Location.class);

            EditText nameEditText = findViewById(R.id.name);
            EditText stateEditText = findViewById(R.id.state);
            EditText countyEditText = findViewById(R.id.county);
            EditText townEditText = findViewById(R.id.town);

            String name = nameEditText.getText().toString();
            String state = stateEditText.getText().toString();
            String county = countyEditText.getText().toString();
            String town = townEditText.getText().toString();

            allGood = true;
            check(name, nameEditText);
            check(state, stateEditText);
            check(county, countyEditText);
            check(town, townEditText);
            if (!allGood) return;

            Location location = locationBox.query(
                    Location_.state.equal(state).and(
                            Location_.county.equal(county)
                    ).and(
                            Location_.town.equal(town)
                    )
            ).build().findFirst();

            if (location == null){
                location = new Location();
                location.state = state;
                location.county = county;
                location.town = town;

                locationBox.put(location);
            }

            FoodWaste foodWaste = new FoodWaste();
            foodWaste.name = name;
            foodWaste.location_id = location.id;
            foodWaste.user_id = user.id;
            foodWaste.status = "Pending..";
            foodWaste.status_message = "Pending..";

            foodWasteBox.put(foodWaste);
            finish();
        });
    }

    private void check(String s, EditText editText){
        if (s.isEmpty()){
            editText.setError("This field is required");
            editText.requestFocus();
            allGood = false;
        }
    }
}