package in.nacode.nacode;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    //defining view objects

   private EditText editUsername, editPassword;
    String username, password1;

    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        // This gets the user and password

        editUsername = (EditText) findViewById(R.id.input_username);

        editPassword = (EditText) findViewById(R.id.input_password);

        //initializing views

        Button loginButton = (Button) findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editUsername.getText().toString().isEmpty() ||  editPassword.getText().toString().isEmpty() ){

                    Toast.makeText(LoginActivity.this,"Enter the credentials !",Toast.LENGTH_SHORT).show();
                }else {

                    mAuth.signInWithEmailAndPassword(editUsername.getText().toString(), editPassword.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }



            }
        });

        TextView na = (TextView) findViewById(R.id.gotosign);
        na.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nar = new Intent(LoginActivity.this, Sign.class);
                startActivity(nar);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {

            Intent na = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(na);
        }

    }

}