package in.nacode.nacode.ProgrammingLang.Notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import in.nacode.nacode.ProgrammingLang.ProgrammingLangC;
import in.nacode.nacode.R;

public class ProgrammingLangC_NotesView extends AppCompatActivity {

    private WebView webView;
    private ImageButton pnvBookmarkButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_lang_c__notes_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("C Notes");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na = new Intent(ProgrammingLangC_NotesView.this,ProgrammingLangC_Notes.class);
                startActivity(na);
            }
        });

        pnvBookmarkButton = (ImageButton)findViewById(R.id.c_notes_bookmark);
        pnvBookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_bookmark,(ViewGroup)findViewById(R.id.custom_toast_bookmark));
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM,0,10);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
        webView = (WebView) findViewById(R.id.c_notes_webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

       String stuff = getIntent().getStringExtra("chap");
        switch (stuff) {
            case "1":
                webView.loadUrl("file:///android_asset/c_notes/1.HTML introduction.html");
                break;
            case "2":
                webView.loadUrl("file:///android_asset/c_notes/2. Structure of program.html");
                break;
            case "3":
                webView.loadUrl("file:///android_asset/c_notes/3. Variables.html");

                break;
            case "4":
                webView.loadUrl("file:///android_asset/c_notes/4. Operators.html");

                break;
            case "5":
                webView.loadUrl("file:///android_asset/c_notes/5. Input and Ouput.html");

                break;
            case "6":
                webView.loadUrl("file:///android_asset/c_notes/6. Decision making.html");

                break;
            case "7":
                webView.loadUrl("file:///android_asset/c_notes/7. Loops.html");

                break;
            case "8":
                webView.loadUrl("file:///android_asset/c_notes/8. Functions.html");

                break;
            case "9":
                webView.loadUrl("file:///android_asset/c_notes/9. Arrays.html");

                break;
            case "10":
                webView.loadUrl("file:///android_asset/c_notes/10. Pointer.html");

                break;
            case "11":
                webView.loadUrl("file:///android_asset/c_notes/11. Structure.html");

                break;
            case "12":
                webView.loadUrl("file:///android_asset/c_notes/13. Union.html");

                break;
            case "13":
                webView.loadUrl("file:///android_asset/c_notes/14.Scope.html");

                break;
            case "14":
                webView.loadUrl("file:///android_asset/c_notes/15. File Operations and Functions.html");

                break;
            case "15":
                webView.loadUrl("file:///android_asset/c_notes/16. C Preprocessor directives.html");

                break;
            default:
                webView.loadUrl("file:///android_asset/c_notes/12. HeaderFiles.html");

                break;
        }






    }
}
