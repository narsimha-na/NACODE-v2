package in.nacode.nacode.ProgrammingLang.Programs;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

import in.nacode.nacode.R;
import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class ProgramView extends AppCompatActivity {

    private CodeView pvCodeView;
    private String programName, programLang;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private FloatingActionButton pvFloatActionButton;
    private int flag;
    private Toolbar pvToolbar;
    private ImageView pvBookmarkImg;


    private DatabaseReference pvDatabaseReferenceProgram, pvDatabaseReferenceBookmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_view);

        pvToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(pvToolbar);
        getSupportActionBar().setTitle("Program");

        checkBookmark();

        //Getting the Program name and Program Lang
        programName = getIntent().getStringExtra("programName");
        programLang = getIntent().getStringExtra("programLang");

        //Getting the data from Firebase Database
        pvDatabaseReferenceProgram = FirebaseDatabase.getInstance().getReference().child("programs").child(programLang).child(programName);

        pvDatabaseReferenceProgram.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String code = (String) dataSnapshot.child("program").getValue();
                String output = (String) dataSnapshot.child("output").getValue();

                pvCodeView = (CodeView) findViewById(R.id.codeView);
                pvCodeView.setTheme(CodeViewTheme.DARKULA).fillColor();
                pvCodeView.showCode(code);

                TextView programOutputView = (TextView) findViewById(R.id.program_view_output);
                programOutputView.setText(output);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ProgramView.this, "Check your Network Connections", Toast.LENGTH_LONG).show();

            }
        });

        pvBookmarkImg = (ImageView) findViewById(R.id.program_view_bookmark);
        pvBookmarkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBookmark() == 1) {
                    bookmarkRemoveProgram();
                } else {
                    bookmarkProgram();
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkBookmark();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.program_view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
    This is for the Toolbar menu selected on basics of their resources id.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.program_view_change_theme:
                pvCodeView.setTheme(CodeViewTheme.ATELIER_CAVE_LIGHT).fillColor();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This Function is used for checking of the program bookmark ,weather the bookmark is present or not.
     */

    private int checkBookmark() {

        FirebaseAuth pvFirebaseAuth = FirebaseAuth.getInstance();
        String pvUserId = pvFirebaseAuth.getCurrentUser().getUid();

        pvDatabaseReferenceProgram = FirebaseDatabase.getInstance().getReference().child("users").child(pvUserId).child("bookmarks").child("programs");
        pvDatabaseReferenceProgram.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(programName)) {
                    flag = 1;
                    pvBookmarkImg.setImageResource(R.drawable.bookmark_white_fill);
                } else {
                    flag = 0;
                    pvBookmarkImg.setImageResource(R.drawable.bookmark_white);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return flag;

    }

    /*
    This Function is used  for bookmark the bookmark
     */
    private void bookmarkProgram() {

        FirebaseAuth pvFirebaseAuth = FirebaseAuth.getInstance();
        String pvUserId = pvFirebaseAuth.getCurrentUser().getUid();

        pvDatabaseReferenceBookmark = FirebaseDatabase.getInstance().getReference().child("users").child(pvUserId).child("bookmarks").child("programs");

        HashMap bookmarkProgramDetails = new HashMap<>();
        bookmarkProgramDetails.put("programName", programName);
        bookmarkProgramDetails.put("programLang", programLang);

        HashMap bookmarkProgram = new HashMap();
        bookmarkProgram.put(programName, bookmarkProgramDetails);

        pvDatabaseReferenceBookmark.setValue(bookmarkProgram)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pvBookmarkImg.setImageResource(R.drawable.bookmark_white_fill);
                        Toast.makeText(ProgramView.this, "Bookmarked Program", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProgramView.this, "Bookmark Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*
    This Function is used for  removing of the bookmark .
     */
    private void bookmarkRemoveProgram() {

        FirebaseAuth pvFirebaseAuth = FirebaseAuth.getInstance();
        String pvUserId = pvFirebaseAuth.getCurrentUser().getUid();

        pvDatabaseReferenceBookmark = FirebaseDatabase.getInstance().getReference().child("users").child(pvUserId).child("bookmarks").child("programs");


        pvDatabaseReferenceBookmark.child(programName).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pvBookmarkImg.setImageResource(R.drawable.bookmark_white);
                        Toast.makeText(ProgramView.this, "Program Applied", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProgramView.this, "Bookmark Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
