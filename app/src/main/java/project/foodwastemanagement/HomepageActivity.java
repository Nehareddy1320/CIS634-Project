package project.foodwastemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import io.objectbox.Box;
import project.foodwastemanagement.database.FoodWaste;
import project.foodwastemanagement.database.FoodWaste_;
import project.foodwastemanagement.database.ObjectBox;
import project.foodwastemanagement.database.User;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = this.getIntent().getLongExtra("user_id", -1);
        Box<User> userBox = ObjectBox.get().boxFor(User.class);
        User user = userBox.get(id);
        if (user == null)
            finish();

        assert user != null;
        if (user.admin){
            setContentView(R.layout.admin_home_page);

            TextView textView = findViewById(R.id.user_welcome);
            textView.setText("Welcome " + user.firstName + " " + (user.lastName == null ? "" : user.lastName) );

            LinearLayout linearLayout = findViewById(R.id.container_food_waste);

            Box<FoodWaste> foodWasteBox = ObjectBox.get().boxFor(FoodWaste.class);
            List<FoodWaste> foodWastes = foodWasteBox.getAll();

            for (final FoodWaste foodWaste: foodWastes) {
                CardView cardView = (CardView) this.getLayoutInflater()
                        .inflate(R.layout.admin_food_waste_list_item, null);

                cardView.setCardElevation(20);
                cardView.setContentPadding(10, 5, 10, 5);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                linearLayout.addView(cardView, lp);

                TextView descriptionTextView = cardView.findViewById(R.id.description);
                descriptionTextView.setText(foodWaste.name);

                TextInputLayout adminEditTextLayout = cardView.findViewById(R.id.admin_message_input);

                cardView.setClickable(true);
                cardView.setFocusable(true);

                cardView.setOnClickListener(view -> {
                    adminEditTextLayout.setVisibility(View.VISIBLE);
                });

                AppCompatButton acceptButton = cardView.findViewById(R.id.accept);
                AppCompatButton cancelButton = cardView.findViewById(R.id.cancel);

                EditText editText = cardView.findViewById(R.id.admin_edittext);
                acceptButton.setOnClickListener(view -> {
                    foodWaste.status = "Accepted";
                    foodWaste.status_message = editText.getText().toString();
                    foodWasteBox.put(foodWaste);
                });

                cancelButton.setOnClickListener(view -> {
                    foodWaste.status = "Cancelled";
                    foodWaste.status_message = editText.getText().toString();
                    foodWasteBox.put(foodWaste);
                });
            }
        } else {
            setContentView(R.layout.user_home_page);
            TextView textView = findViewById(R.id.user_welcome);
            textView.setText("Welcome " + (user.firstName == null ? "" : user.firstName) + " " +
                    (user.lastName == null ? "" : user.lastName ));

            LinearLayout linearLayout = findViewById(R.id.container_food_waste);

            Box<FoodWaste> foodWasteBox = ObjectBox.get().boxFor(FoodWaste.class);
            List<FoodWaste> foodWastes = foodWasteBox.query(FoodWaste_.user_id.equal(user.id)).build().find();

            for (FoodWaste foodWaste: foodWastes) {
                CardView cardView = (CardView) this.getLayoutInflater()
                        .inflate(R.layout.user_food_wastage_list_item, null);

                cardView.setCardElevation(20);
                cardView.setContentPadding(10, 5, 10, 5);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                linearLayout.addView(cardView, lp);

                TextView descriptionTextView = cardView.findViewById(R.id.description);
                descriptionTextView.setText(foodWaste.name);

                TextView statusTextView = cardView.findViewById(R.id.status);
                statusTextView.setText(foodWaste.status);

                TextView status_messageTextView = cardView.findViewById(R.id.status_message);
                status_messageTextView.setText(foodWaste.status_message);
            }

            Button button = findViewById(R.id.add_food_wastage);
            button.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), AddFoodWasteActivity.class);
                intent.putExtra("user_id", user.id);
                startActivity(intent);
            });
        }
    }
}