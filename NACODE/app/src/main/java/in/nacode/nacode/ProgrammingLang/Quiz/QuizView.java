package in.nacode.nacode.ProgrammingLang.Quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.nacode.nacode.ListItems.QuizPost;
import in.nacode.nacode.R;
import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class QuizView extends AppCompatActivity {

    private TextView qvQuestionView,qvProgressView;
    private RadioButton qvOptButton1,qvOptButton2,qvOptButton3,qvOptButton4,qvSelectedButton;
    private RadioGroup qvRadioGroup;
    private Button qvNextButton,qvPreviousButton,qvSubmitButton;
    private CodeView qvCodeView;
    private ProgressBar qvProgressBar,qvProgressBarCircular;
    private Handler qvHandler = new Handler();

    private DatabaseReference qvDatabaseReference;

    private int total=1,correct=0,wrong=0,progress =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_view);

        qvRadioGroup = (RadioGroup)findViewById(R.id.quiz_radioGroup);
        qvOptButton1 = (RadioButton)findViewById(R.id.quiz_opt1);
        qvOptButton2 = (RadioButton)findViewById(R.id.quiz_opt2);
        qvOptButton3 = (RadioButton)findViewById(R.id.quiz_opt3);
        qvOptButton4 = (RadioButton)findViewById(R.id.quiz_opt4);

        qvNextButton = (Button)findViewById(R.id.quiz_next);
        qvPreviousButton = (Button)findViewById(R.id.quiz_previous);
        qvPreviousButton.setBackgroundColor(getResources().getColor(R.color.lightGrey));


        qvQuestionView = (TextView) findViewById(R.id.quiz_question);
        qvProgressView = (TextView) findViewById(R.id.quiz_progress_view);
        qvCodeView = (CodeView)findViewById(R.id.quiz_code);

        qvProgressBar = (ProgressBar)findViewById(R.id.quiz_progressBar);
        qvProgressBarCircular = (ProgressBar)findViewById(R.id.quiz_circle_progress);


        loadQuestion();

    }

    private void loadQuestion() {



        if (total > 10) {

            Toast.makeText(QuizView.this,"Correct :"+correct+"\t Wrong :"+wrong,Toast.LENGTH_LONG).show();
            Intent qv = new Intent(QuizView.this,QuizResult.class);
            qv.putExtra("correct",String.valueOf(correct));
            qv.putExtra("wrong",String.valueOf(wrong));
            startActivity(qv);

        } else {

            if(total == 10){
                qvNextButton.setText(getResources().getString(R.string.submit));
                qvPreviousButton.setVisibility(View.GONE);
            }

            qvProgressBarCircular.setVisibility(View.VISIBLE);

            qvDatabaseReference = FirebaseDatabase.getInstance().getReference().child("quiz").child("c").child("set1").child(String.valueOf(total));
            qvDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final QuizPost quizPost = dataSnapshot.getValue(QuizPost.class);

                    qvQuestionView.setText(quizPost.getQuestion());
                    qvOptButton1.setText(quizPost.getOpt1());
                    qvOptButton2.setText(quizPost.getOpt2());
                    qvOptButton3.setText(quizPost.getOpt3());
                    qvOptButton4.setText(quizPost.getOpt4());

                    qvCodeView.setTheme(CodeViewTheme.DARKULA).fillColor();
                    qvCodeView.setVisibility(View.VISIBLE);
                    qvCodeView.showCode(quizPost.getCode());


                    qvProgressBarCircular.setVisibility(View.INVISIBLE);

                    try
                    {
                        setProgressValue(progress);

                        qvNextButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                    int selectedOptionId =  qvRadioGroup.getCheckedRadioButtonId();
                                    qvSelectedButton = (RadioButton)findViewById(selectedOptionId);

                                    if (selectedOptionId <= 0){
                                        Toast.makeText(QuizView.this,"Please Select a Option",Toast.LENGTH_LONG).show();
                                    }else{
                                        if(qvSelectedButton.getText().equals(quizPost.getAns())){
                                            correct++;total++;
                                            progress=progress+10;
                                            loadQuestion();
                                            qvOptButton1.setChecked(false);
                                            qvOptButton2.setChecked(false);
                                            qvOptButton3.setChecked(false);
                                            qvOptButton4.setChecked(false);
                                            qvPreviousButton.setBackgroundColor(getResources().getColor(R.color.mainPurple));
                                        } else{
                                            wrong++;total++;
                                            progress=progress+10;
                                            loadQuestion();
                                            qvOptButton1.setChecked(false);
                                            qvOptButton2.setChecked(false);
                                            qvOptButton3.setChecked(false);
                                            qvOptButton4.setChecked(false);
                                            qvPreviousButton.setBackgroundColor(getResources().getColor(R.color.mainPurple));
                                        }
                                    }

                            }

                        });

                        qvPreviousButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(total == 1){
                                    qvPreviousButton.setBackgroundColor(getResources().getColor(R.color.lightGrey));
                                    Toast.makeText(QuizView.this,"Reached the Beginning !",Toast.LENGTH_SHORT).show();
                                }else{
                                    total--;progress=progress-10;
                                    loadQuestion();
                                }

                            }
                        });

                    }catch(Exception e){

                        Toast.makeText(QuizView.this,"Please Select a Option",Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }

    void setProgressValue(final int progressValue){

        qvProgressBar.setProgress(progressValue);
        //This thread is used to change the value of  progress value
        Thread qvThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        qvThread.start();
    }
}

