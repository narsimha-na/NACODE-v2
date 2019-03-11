package in.nacode.nacode;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class CreatePost extends AppCompatActivity {


    private EditText nFeedTitle, nFeedDesc, nFeedCode, nFeedLinks;
    private Button nFeedSubmit;
    private ImageView nFeedImage;

    private FirebaseFirestore firebaseFirestore;
    private StorageReference mStorage;

    private Uri postImageUri;
    private byte[] data1;

    private ProgressBar progressBar;




    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        mStorage = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();


        // Getting text from new feed form
        nFeedTitle = (EditText) findViewById(R.id.feed_title);
        nFeedDesc = (EditText) findViewById(R.id.feed_desc);
        nFeedCode = (EditText) findViewById(R.id.feed_code);
        nFeedLinks = (EditText) findViewById(R.id.feed_link);



        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        // Getting image from the user
        nFeedImage = (ImageView) findViewById(R.id.feed_img);
        nFeedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(CreatePost.this);

            }
        });

        //Getting all the  details from user
        nFeedSubmit = (Button) findViewById(R.id.feeds_submit);
        nFeedSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                if (nFeedTitle.getText().toString().isEmpty() || nFeedDesc.getText().toString().isEmpty() || postImageUri.getPath() == null) {

                    Toast.makeText(CreatePost.this, "Please fill the details", Toast.LENGTH_SHORT).show();

                } else {

                    final StorageReference mStoragePath = mStorage.child("feed").child(nFeedTitle.getText().toString() + ".jpg");
                    UploadTask imagePath = mStoragePath.putBytes(data1);
                    Task<Uri> urlTask = imagePath.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()) {
                                Toast.makeText(CreatePost.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            return mStoragePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                Uri downloadUri = task.getResult();

                                Map<String, Object> nFeedMap = new HashMap<>();
                                nFeedMap.put("title", nFeedTitle.getText().toString());
                                nFeedMap.put("desc", nFeedDesc.getText().toString());
                                nFeedMap.put("img_url", downloadUri.toString());
                                nFeedMap.put("code", nFeedCode.getText().toString());
                                nFeedMap.put("links", nFeedLinks.getText().toString());
                                nFeedMap.put("timestamp", System.currentTimeMillis());

                                firebaseFirestore.collection("notification").document(nFeedTitle.getText().toString()).set(nFeedMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Toast.makeText(CreatePost.this, "Posted Successfully", Toast.LENGTH_LONG).show();
                                                    Intent goToMain = new Intent(CreatePost.this, MainActivity.class);
                                                    startActivity(goToMain);
                                                    progressBar.setVisibility(View.GONE);
                                                    finish();

                                                } else {

                                                    Toast.makeText(CreatePost.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(CreatePost.this, "Somthing Went Wrong ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                postImageUri = result.getUri();
                if (postImageUri.toString().isEmpty()) {

                    Toast.makeText(this, "Please insert image !", Toast.LENGTH_SHORT).show();

                } else {

                    nFeedImage.setImageURI(postImageUri);


                    File actucalImage = new File(result.getUri().getPath());

                    try {
                        Bitmap compressedImage = new Compressor(CreatePost.this)
                                .setMaxHeight(250)
                                .setMaxWidth(250)
                                .setQuality(50)
                                .compressToBitmap(actucalImage);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        data1 = baos.toByteArray();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

}