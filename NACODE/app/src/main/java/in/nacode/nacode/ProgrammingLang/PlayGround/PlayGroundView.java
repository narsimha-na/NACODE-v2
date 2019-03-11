package in.nacode.nacode.ProgrammingLang.PlayGround;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import in.nacode.nacode.R;

public class PlayGroundView extends AppCompatActivity {

    private WebView pgvWebView;
    private FloatingActionButton pgvFloatingActionButton;

    private DatabaseReference pgvDatabaseReference;

    private String pgvUserId, setCode, pgvProgramExists = "0";
    private Dialog pgvDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground_view);

        pgvDialog = new Dialog(this);

        pgvWebView = findViewById(R.id.playground_webView);
        pgvWebView.getSettings().setLoadsImagesAutomatically(true);
        pgvWebView.getSettings().setJavaScriptEnabled(true);
        pgvWebView.addJavascriptInterface(new JavaScriptInterface(this), "code");
        pgvWebView.addJavascriptInterface(new CodeAdd(this), "add");
        pgvWebView.loadUrl("file:///android_asset/codeViewPlayGround.html");

        pgvUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        pgvProgramExists = getIntent().getStringExtra("pgProgramExists");

    }

    @Override
    protected void onStart() {
        super.onStart();
        showBetaPopUp();
        pgvWebView.loadUrl("javascript:get()");
    }

    private void showBetaPopUp() {

      pgvDialog.setContentView(R.layout.playground_load_program);
        pgvDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pgvDialog.show();
    }

    //The method is invoked when getCodeFromFireabsae() is called .
    void na(String codeToDisplay) {
        setCode = codeToDisplay;
    }


    private class CodeAdd {
        Context mContext;
        CodeAdd(Context c) {
            mContext = c;
        }
        /**
         * This is used to get the data from Database
         */
        @JavascriptInterface
        public String showCode() {
            String pgKeyId = getIntent().getStringExtra("playgroundId");
            getCodeFromFireabse(pgKeyId);
            return setCode;

        }
    }

    /*
    This code Will help us to get the PlayGround Code from the database
     */

    private void getCodeFromFireabse(final String pgKeyId) {

        if(pgvProgramExists.equals("1")){

            na("//Enter Your Code here ");
        }else{
            pgvDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(pgvUserId).child("playground").child("c");
            pgvDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    // JavaScriptInterface na = new JavaScriptInterface(PlayGroundView.this);
                    //na.pgSetCodeView();
                    na(String.valueOf(dataSnapshot.child(pgKeyId).child("code").getValue()));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(PlayGroundView.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public class JavaScriptInterface {
        Context mContext;
        /**
         * Instantiate the interface and set the context
         */
        JavaScriptInterface(Context c) {
            mContext = c;
        }
        /**
         * This is Used for saving Playground
         */
        @JavascriptInterface
        public void pgGetCodeView(String pgCode) {
            if (pgvProgramExists.equals("1")) {
                //For a new Playground
                String pgProgramName = getIntent().getStringExtra("pgNewProgramName");
                getNewCodeFirebase(pgCode, pgProgramName);
            } else if (pgvProgramExists.equals("0")) {
                //Saving Old Playground
                getCodeFirebase(pgCode);

            }
        }
    }

    //This is used for saving new Playground
    void getNewCodeFirebase(String gcfCode, String gcfProgramName) {

        HashMap newPlaygroundDetails = new HashMap();
        newPlaygroundDetails.put("name", gcfProgramName);
        newPlaygroundDetails.put("code", gcfCode);
        newPlaygroundDetails.put("time", "20 july 2018");
        newPlaygroundDetails.put("lang","c");

        pgvDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(pgvUserId).child("playground").child("c");
        DatabaseReference pgvKeyReference = pgvDatabaseReference.push();
        pgvKeyReference.setValue(newPlaygroundDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(PlayGroundView.this, "Program Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(PlayGroundView.this, "Network Error !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*
    This method will  code from the database and  is used for saving the old playground
     */

    private void getCodeFirebase(String pgCode) {
        String pgKeyId = getIntent().getStringExtra("playgroundId");
        pgvDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(pgvUserId).child("playground").child("c").child(pgKeyId);
        pgvDatabaseReference.child("code").setValue(pgCode)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(PlayGroundView.this, "Program Saved ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PlayGroundView.this, "Network Error !", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}

