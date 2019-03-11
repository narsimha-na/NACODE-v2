package in.nacode.nacode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class CreateQuestion extends AppCompatActivity {

    public EditText cqTitle, cqDesc, cqCode,cqTopic;
    public ImageView cqImg;
    private Button qSubmit;
    SimpleDateFormat sdf;

    private CardView cardView;

    public Uri uri;
    private byte[] imgData;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    public DatabaseReference cq_databaseReference;
    private StorageReference firebaseStorage;

    private  boolean na;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        //Getting all the details from user
        cqTitle = (EditText) findViewById(R.id.cq_qTitle);
        cqDesc = (EditText) findViewById(R.id.cq_qDesc);
        cqCode = (EditText) findViewById(R.id.cq_qCode);
        cqTopic = (EditText)findViewById(R.id.cq_qTopic);


        cqImg = (ImageView) findViewById(R.id.cq_qImg);
        qSubmit = (Button) findViewById(R.id.cq_qSubmit);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        cardView = (CardView) findViewById(R.id.cq_qCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(CreateQuestion.this);

            }
        });

        qSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cqTitle.getText().toString().trim().isEmpty() || cqDesc.getText().toString().isEmpty()) {

                    Toast.makeText(CreateQuestion.this, "Please fill details", Toast.LENGTH_SHORT).show();

                } else {

                    final StorageReference mStoragePath = firebaseStorage.child("qa").child(cqTitle.getText().toString() + ".jpg");
                    UploadTask imagePath = mStoragePath.putBytes(imgData);

                    Task<Uri> urlTask = imagePath.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()) {
                                Toast.makeText(CreateQuestion.this, "Please check your Internet", Toast.LENGTH_SHORT).show();
                            }
                            return mStoragePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            Uri downloadUri = task.getResult();
                            HashMap<String, Object> cqHashMap = new HashMap<>();
                            cqHashMap.put("qTitle", cqTitle.getText().toString().trim());
                            cqHashMap.put("qDesc", cqDesc.getText().toString());
                            cqHashMap.put("qCode", cqCode.getText().toString().trim());
                            cqHashMap.put("qTopic", cqTopic.getText().toString().trim());
                            cqHashMap.put("qImgUrl", downloadUri.toString());
                            cqHashMap.put("qTime",sdf.format(new Date()));
                            cqHashMap.put("qUserDetails",firebaseAuth.getCurrentUser().getUid());

                            cq_databaseReference = FirebaseDatabase.getInstance().getReference().child("qa").child(cqTitle.getText().toString());
                            cq_databaseReference.setValue(cqHashMap)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CreateQuestion.this, "Something went Wrong !", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(CreateQuestion.this, "Successful", Toast.LENGTH_SHORT).show();
                                            Intent goToQA = new Intent(CreateQuestion.this, MainActivity.class);
                                            startActivity(goToQA);
                                            finish();
                                        }
                                    });

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

                uri = result.getUri();
                if (uri.toString().isEmpty()) {

                    Toast.makeText(this, "Please insert image !", Toast.LENGTH_SHORT).show();

                } else {

                    cqImg.setImageURI(uri);


                    File actucalImage = new File(result.getUri().getPath());

                    try {
                        Bitmap compressedImage = new Compressor(CreateQuestion.this)
                                .setMaxHeight(250)
                                .setMaxWidth(250)
                                .setQuality(50)
                                .compressToBitmap(actucalImage);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        imgData = baos.toByteArray();


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
