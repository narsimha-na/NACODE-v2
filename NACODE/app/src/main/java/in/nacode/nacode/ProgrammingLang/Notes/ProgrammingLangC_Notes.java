package in.nacode.nacode.ProgrammingLang.Notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import in.nacode.nacode.ProgrammingLang.ProgrammingLangC;
import in.nacode.nacode.R;

public class ProgrammingLangC_Notes extends AppCompatActivity {

    private CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_lang_c__notes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("C Notes");

        c1 = (CardView) findViewById(R.id.c_notes_1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("1");
            }
        });

        c2 = (CardView) findViewById(R.id.c_notes_2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("2");
            }
        });

        c3 = (CardView) findViewById(R.id.c_notes_3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("3");
            }
        });

        c4 = (CardView) findViewById(R.id.c_notes_4);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("4");
            }
        });

        c5 = (CardView) findViewById(R.id.c_notes_5);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("5");
            }
        });

        c6 = (CardView) findViewById(R.id.c_notes_6);
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("6");
            }
        });

        c7 = (CardView) findViewById(R.id.c_notes_7);
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("7");
            }
        });

        c8 = (CardView) findViewById(R.id.c_notes_8);
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("8");
            }
        });

        c9 = (CardView) findViewById(R.id.c_notes_9);
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("9");
            }
        });

        c10 = (CardView) findViewById(R.id.c_notes_10);
        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("10");
            }
        });

        c11 = (CardView) findViewById(R.id.c_notes_11);
        c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("11");
            }
        });
        c12 = (CardView) findViewById(R.id.c_notes_12);
        c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("12");
            }
        });
        c13 = (CardView) findViewById(R.id.c_notes_13);
        c13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("13");
            }
        });
        c14 = (CardView) findViewById(R.id.c_notes_14);
        c14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("14");
            }
        });
        c15 = (CardView) findViewById(R.id.c_notes_15);
        c15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("15");
            }
        });
        c16 = (CardView) findViewById(R.id.c_notes_16);
        c16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotesName("16");
            }
        });



    }

    public void sendNotesName(String chapterNo){

        Intent na = new Intent(ProgrammingLangC_Notes.this,ProgrammingLangC_NotesView.class);
        na.putExtra("chap",chapterNo);
        startActivity(na);

    }

}
