package in.nacode.nacode;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class FullFeedView extends AppCompatActivity {

    Intent intent;

    private TextView fTitle,fDesc,fTime;
    private ImageView fImage;

    private ImageView ffvBookmark,ffvLike,ffvShare;

    private FirebaseFirestore ffvFirebaseFirestore;
    private FirebaseAuth ffvFirebaseAuth;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_feed_view);

        ffvFirebaseFirestore = FirebaseFirestore.getInstance();
        ffvFirebaseAuth = FirebaseAuth.getInstance();

        res = getResources();


        intent = getIntent();

        //Getting bookmarks from FirebaseFirestore

        ffvFirebaseFirestore.collection("notification").document(intent.getStringExtra("feed_title")).collection("bookmark").document(ffvFirebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if(documentSnapshot.exists()){

                    ffvBookmark.setImageDrawable(res.getDrawable(R.drawable.home_bookmark_true));

                }else{

                    ffvBookmark.setImageDrawable(res.getDrawable(R.drawable.home_bookmark_false_white));
                }
            }
        });


        ffvFirebaseFirestore.collection("notification").document(intent.getStringExtra("feed_title")).collection("likes").document(ffvFirebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if(documentSnapshot.exists()){

                    ffvLike.setImageDrawable(res.getDrawable(R.drawable.home_feed_like));
                }else{
                    ffvLike.setImageDrawable(res.getDrawable(R.drawable.home_feed_unlike_white));
                }

            }
        });

        fTitle = (TextView)findViewById(R.id.fTitle);
        fTime = (TextView)findViewById(R.id.fDate);
        fDesc = (TextView)findViewById(R.id.fDesc);
        fImage = (ImageView) findViewById(R.id.fImg);


        fTitle.setText(intent.getStringExtra("feed_title"));
        fTime.setText(intent.getStringExtra("feed_time"));
        fDesc.setText(intent.getStringExtra("feed_desc"));
        Glide.with(FullFeedView.this).load(intent.getStringExtra("feed_img")).into(fImage);

        ffvBookmark = (ImageView) findViewById(R.id.fv_bookmark);
        ffvLike = (ImageView)findViewById(R.id.fv_like);
        ffvShare = (ImageView)findViewById(R.id.fv_share);

        ffvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                likes();

            }
        });

        ffvBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookmark();

            }
        });


    }

    public  void likes(){

        ffvFirebaseFirestore.collection("notification").document(intent.getStringExtra("feed_title")).collection("likes").document(ffvFirebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(!task.getResult().exists()){

                    Map<String, Object> likesMap = new HashMap<>();
                    likesMap.put("time", FieldValue.serverTimestamp());

                    ffvFirebaseFirestore.collection("notification").document(intent.getStringExtra("feed_title")).collection("likes").document(ffvFirebaseAuth.getCurrentUser().getUid()).set(likesMap)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(FullFeedView.this,"Network Failure",Toast.LENGTH_SHORT).show();

                                }
                            });

                }
            }
        });
    }

    public void bookmark(){

        ffvFirebaseFirestore.collection("notification").document(intent.getStringExtra("feed_title")).collection("bookmark").document(ffvFirebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(!task.getResult().exists()){

                    Map<String,Object> likesMap = new HashMap<>();
                    likesMap.put("time",FieldValue.serverTimestamp());

                    ffvFirebaseFirestore.collection("notification").document(intent.getStringExtra("feed_title")).collection("bookmark").document(ffvFirebaseAuth.getCurrentUser().getUid()).set(likesMap);
                }else{

                    ffvFirebaseFirestore.collection("notification").document(intent.getStringExtra("feed_title")).collection("bookmark").document(ffvFirebaseAuth.getCurrentUser().getUid()).delete();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(FullFeedView.this,"Network Failure",Toast.LENGTH_LONG).show();
            }
        });
    }
}
