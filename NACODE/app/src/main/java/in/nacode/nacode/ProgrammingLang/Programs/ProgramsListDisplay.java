package in.nacode.nacode.ProgrammingLang.Programs;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import in.nacode.nacode.ListItems.ProgramListPost;
import in.nacode.nacode.R;

public class ProgramsListDisplay extends AppCompatActivity {

    private RecyclerView lpRecyclerView;

    private FirebaseRecyclerOptions<ProgramListPost> lpFirebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<ProgramListPost, ProgramListViewHolder> lpFirebaseRecyclerAdapter;

    private Toolbar lpToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_list_display);

        lpToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(lpToolbar);
        lpToolbar.setTitle("Programs");

        lpRecyclerView = (RecyclerView) findViewById(R.id.program_list_recyclerView);
        LinearLayoutManager lpLinearLayoutManager = new LinearLayoutManager(ProgramsListDisplay.this);
        lpRecyclerView.setLayoutManager(lpLinearLayoutManager);

        String lpLang = getIntent().getStringExtra("programLang");
        Toast.makeText(ProgramsListDisplay.this, lpLang, Toast.LENGTH_LONG).show();


        if (lpLang.equalsIgnoreCase("c")) {

            lpClickLang("c");

        }else if (lpLang.equalsIgnoreCase("cpp")){

            lpClickLang("cpp");
        }else if (lpLang.equalsIgnoreCase("java")){

            lpClickLang("java");
        }else if (lpLang.equalsIgnoreCase("python")){

            lpClickLang("python");
        }else if (lpLang.equalsIgnoreCase("html")){

            lpClickLang("html");
        }else if (lpLang.equalsIgnoreCase("css")){

            lpClickLang("css");
        }else if (lpLang.equalsIgnoreCase("js")){

            lpClickLang("js");
        }else {

            lpClickLang("sql");
        }


    }

    private void lpClickLang(final String lpClickLang) {

        //Setting Lang for ToolBar


        Query lpQuery = FirebaseDatabase.getInstance().getReference().child("programs").child(lpClickLang);

        lpFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ProgramListPost>()
                .setQuery(lpQuery, ProgramListPost.class)
                .build();

        lpFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ProgramListPost, ProgramListViewHolder>(lpFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ProgramListViewHolder holder, int position, @NonNull final ProgramListPost model) {
                holder.setName(model.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent na = new Intent(ProgramsListDisplay.this,ProgramView.class);
                        na.putExtra("programName",model.getName());
                        na.putExtra("programLang",lpClickLang);
                        startActivity(na);
                    }
                });
            }

            @NonNull
            @Override
            public ProgramListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View lpView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.program_list_item, viewGroup, false);

                return new ProgramListViewHolder(lpView);
            }
        };

        lpFirebaseRecyclerAdapter.startListening();
        lpRecyclerView.setAdapter(lpFirebaseRecyclerAdapter);


    }


    public class ProgramListViewHolder extends RecyclerView.ViewHolder {

        private TextView lpProgramName;
        private View lpView;

        public ProgramListViewHolder(@NonNull View itemView) {
            super(itemView);
            lpView = itemView;
        }

        public void setName(String name) {

            lpProgramName = (TextView) lpView.findViewById(R.id.program_list_programName);
            lpProgramName.setText(name);

        }

    }

}


