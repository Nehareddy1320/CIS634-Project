package project.foodwastemanagement;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import project.foodwastemanagement.database.ObjectBox;
import project.foodwastemanagement.database.User;

public class Main2Activity extends AppCompatActivity {

    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sign up
        setContentView(R.layout.activity_main2);
        back = findViewById(R.id.back);
        back.setOnClickListener(view -> finish());

        Button button = findViewById(R.id.sign_up_button);
        button.setOnClickListener(view -> {
            EditText editText;
            User user = new User();
            editText = findViewById(R.id.first_name);
            user.firstName = editText.getText().toString();

            editText = findViewById(R.id.last_name);
            user.lastName = editText.getText().toString();

            editText = findViewById(R.id.email);
            user.email = editText.getText().toString();

            editText = findViewById(R.id.password);
            String password = editText.getText().toString();

            editText = findViewById(R.id.password_confirm);
            String password_confirm = editText.getText().toString();

            if (!password.equals(password_confirm)) {
                String err = "Passwords Don't Match";
                editText.setError(err);
                editText = findViewById(R.id.password);
                editText.setError(err);
                editText.requestFocus();
                return;
            }

            if (user.email.isEmpty()) {
                String err = "An Email is required";
                editText = findViewById(R.id.email);
                editText.setError(err);
                editText.requestFocus();
                return;
            }
            user.password = password;
            user.admin = false;

            ObjectBox.get().boxFor(User.class).put(user);

            Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
            finish();
            startActivity(intent);

        });
    }
}

//            Dialog d = new Dialog(this);
//            TextView t = new TextView(this);
//
//            t.setText(Arrays.toString(ObjectBox.get().boxFor(User.class).getAll().toArray()));
//            d.setContentView(t);
//            d.show();
//            d.setOnDismissListener(dialogInterface -> {
//                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
//                finish();
//                startActivity(intent);
//            });
