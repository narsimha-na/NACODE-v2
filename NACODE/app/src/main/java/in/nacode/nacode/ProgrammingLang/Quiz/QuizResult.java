package in.nacode.nacode.ProgrammingLang.Quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import in.nacode.nacode.R;

public class QuizResult extends AppCompatActivity {

    private TextView qrCorrectView,qrWrongView;
    private CardView qrCardViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent qr;
        String qrCorrect =getIntent().getStringExtra("correct");
        String qrWrong = getIntent().getStringExtra("wrong");

        Toast.makeText(QuizResult.this,"Result :"+qrCorrect +" "+qrWrong,Toast.LENGTH_LONG).show();

        qrCorrectView = (TextView)findViewById(R.id.quiz_result_correct);
        qrCorrectView.setText(qrCorrect);

        qrWrongView = (TextView) findViewById(R.id.quiz_result_wrong);
        qrWrongView.setText(qrWrong);

        qrCardViewBack = (CardView) findViewById(R.id.quiz_result_back);
        qrCardViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na = new Intent(QuizResult.this,ProgrammingLangC_Quiz.class);
                startActivity(na);
            }
        });

    }
}
