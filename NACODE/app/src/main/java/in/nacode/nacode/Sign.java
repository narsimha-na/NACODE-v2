package in.nacode.nacode;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Sign extends AppCompatActivity {

    private EditText nameEdit, emailEdit, passwordEdit;
    private String name, email, password;

    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mAuth = FirebaseAuth.getInstance();

        nameEdit = (EditText) findViewById(R.id.name);
        name = nameEdit.getText().toString().trim();

        emailEdit = (EditText) findViewById(R.id.email);
        email = emailEdit.getText().toString().trim();

        passwordEdit = (EditText) findViewById(R.id.password);
        password = passwordEdit.getText().toString().trim();

        Button na = (Button) findViewById(R.id.btn_signup);
        na.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameEdit.getText().toString().isEmpty() || passwordEdit.getText().toString().isEmpty()) {

                    Toast.makeText(Sign.this, "Enter the details", Toast.LENGTH_SHORT).show();

                } else if (passwordEdit.getText().toString().length() < 8) {

                    Toast.makeText(Sign.this, "Password should be minimum 8 characters ", Toast.LENGTH_LONG).show();

                } else {

                    mAuth.createUserWithEmailAndPassword(emailEdit.getText().toString(), passwordEdit.getText().toString())
                            .addOnCompleteListener(Sign.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(Sign.this, "Authentication Success !", Toast.LENGTH_SHORT).show();

                                        current_user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = current_user.getUid();
                                        mDataBase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);


                                        //Creating HashMap for the  database of the user

                                        HashMap user_notification = new HashMap<>();

                                        HashMap user_notification_details = new HashMap<>();

                                        user_notification_details.put("notification-name", "Welcome To NA CODE ");
                                        user_notification_details.put("notification-matter", "This NA CODE welcome all of you to the world of programming ");
                                        user_notification_details.put("notification-link", " null ");

                                        user_notification.put("Welcome To NA CODE ", user_notification_details);


                                        HashMap user_details = new HashMap<>();

                                        user_details.put("name", nameEdit.getText().toString().trim());
                                        user_details.put("email", emailEdit.getText().toString());
                                        user_details.put("password", passwordEdit.getText().toString());
                                        user_details.put("profile pic", " null");

                                        //Master HashMap
                                        HashMap user = new HashMap<>();

                                        user.put("user-details", user_details);
                                        user.put("user-notification ", user_notification);

                                        //Inserting the values to firebase Database
                                        mDataBase.setValue(user);


                                    } else {
                                        Toast.makeText(Sign.this, "Authentication Failed ! +", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sign.this,e+"  Error" , Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        });


    }
}
