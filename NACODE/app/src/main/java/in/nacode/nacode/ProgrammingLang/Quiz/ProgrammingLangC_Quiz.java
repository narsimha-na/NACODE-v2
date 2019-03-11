package in.nacode.nacode.ProgrammingLang.Quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import in.nacode.nacode.R;

public class ProgrammingLangC_Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_lang_c__quiz);

        CardView  set1 = (CardView)findViewById(R.id.set1);
        set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action;
                Intent na = new Intent(ProgrammingLangC_Quiz.this,QuizView.class);
                startActivity(na);
            }
        });
    }
}
