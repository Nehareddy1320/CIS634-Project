package project.foodwastemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import project.foodwastemanagement.database.ObjectBox;
import project.foodwastemanagement.database.User;
import project.foodwastemanagement.database.User_;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Login
        setContentView(R.layout.activity_main3);
        TextView back = findViewById(R.id.back);
        back.setOnClickListener(view -> finish());

        Button login = findViewById(R.id.login);
        login.setOnClickListener(view -> {
            EditText emailEdit = findViewById(R.id.email);
            EditText passwordEdit = findViewById(R.id.password);

            String email = emailEdit.getText().toString();
            String password = passwordEdit.getText().toString();

            boolean err = false;
            if (email.isEmpty()){
                emailEdit.setError("An Email is Required");
                emailEdit.requestFocus();
                err = true;
            }

            if (password.isEmpty()){
                passwordEdit.setError("A password is Required");
                passwordEdit.requestFocus();
                err = true;
            }

            if (err) return;

            User user = ObjectBox.get().boxFor(User.class).query(User_.email.equal(email)
                    .and(User_.password.equal(password))).build().findFirst();
            if (user == null){
                emailEdit.setError("Incorrect Login Email ");
                passwordEdit.setError("Incorrect Login Password ");
                emailEdit.requestFocus();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
            intent.putExtra("user_id", user.id);
            startActivity(intent);
        });

    }
}
