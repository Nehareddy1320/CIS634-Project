package project.foodwastemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.objectbox.Box;
import project.foodwastemanagement.database.ObjectBox;
import project.foodwastemanagement.database.User;
import project.foodwastemanagement.database.User_;

public class MainActivity extends AppCompatActivity {

    Button join_us;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectBox.init(this);

        // create Admin user
        User user = new User();

        user.admin = true;
        user.firstName = "Admin";
        user.email = "admin@mail.com";
        user.password = "admin";

        Box<User> box = ObjectBox.get().boxFor(User.class);
        if (box.query(User_.email.equal(user.email)
                .and(User_.password.equal(user.password))).build().findFirst() == null) {
            box.put(user);
        }

        setContentView(R.layout.activity_main);
        join_us = findViewById(R.id.join_us);
        login = findViewById(R.id.login);
        join_us.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        });
        login.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
            startActivity(intent);
        });
    }
}
