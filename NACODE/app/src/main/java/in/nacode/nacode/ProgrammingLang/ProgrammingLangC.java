package in.nacode.nacode.ProgrammingLang;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import in.nacode.nacode.ListItems.TrendingQuiresLangCList;
import in.nacode.nacode.ProgrammingLang.Bookmarks.ProgrammingLangC_Bookmark;
import in.nacode.nacode.ProgrammingLang.Notes.ProgrammingLangC_Notes;

import in.nacode.nacode.ProgrammingLang.PlayGround.PlayGroundListDisplay;
import in.nacode.nacode.ProgrammingLang.Programs.ProgramsListDisplay;
import in.nacode.nacode.ProgrammingLang.Quiz.ProgrammingLangC_Quiz;
import in.nacode.nacode.R;

public class ProgrammingLangC extends AppCompatActivity {

    private CardView cNotesCard,cBookmarkCard,cProgramsCard;
    private String userId;
    private RecyclerView plcRecyclerView;

    private FirebaseRecyclerOptions<TrendingQuiresLangCList> plcFirebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<TrendingQuiresLangCList, TrendingQueriesViewHolder> plcFirebaseRecyclerAdapter;
    private DatabaseReference plcDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_lang_c);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
       collapsingToolbarLayout.setTitle("C Programming");


        cNotesCard = (CardView) findViewById(R.id.c_notes);
        cNotesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent na = new Intent(ProgrammingLangC.this,ProgrammingLangC_Notes.class);
                startActivity(na);
            }
        });

        cBookmarkCard = (CardView)findViewById(R.id.c_bookmark);
        cBookmarkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na1 = new Intent(ProgrammingLangC.this,ProgrammingLangC_Bookmark.class);
                startActivity(na1);
            }
        });

        cProgramsCard = (CardView)findViewById(R.id.c_programs);
        cProgramsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na2 = new Intent(ProgrammingLangC.this,ProgramsListDisplay.class);
                na2.putExtra("programLang","c");
                startActivity(na2);
            }
        });

        cProgramsCard = (CardView)findViewById(R.id.c_qa);
        cProgramsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na2 = new Intent(ProgrammingLangC.this, ProgrammingLangC_Quiz.class);

                startActivity(na2);
            }
        });

        cProgramsCard = (CardView)findViewById(R.id.c_pg);
        cProgramsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na2 = new Intent(ProgrammingLangC.this, PlayGroundListDisplay.class);

                startActivity(na2);
            }
        });

        plcRecyclerView = (RecyclerView)findViewById(R.id.c_trending_queries_recylerView);
        LinearLayoutManager plcLinearLayoutManager = new LinearLayoutManager(ProgrammingLangC.this,LinearLayoutManager.HORIZONTAL,false);
                plcLinearLayoutManager.setStackFromEnd(true);
        plcRecyclerView.setLayoutManager(plcLinearLayoutManager);


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query plcQuery = FirebaseDatabase.getInstance().getReference().child("qa").child("c").orderByChild("likes");

        plcFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<TrendingQuiresLangCList>()
                .setQuery(plcQuery,TrendingQuiresLangCList.class)
                .build();
        plcFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TrendingQuiresLangCList, TrendingQueriesViewHolder>(plcFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull TrendingQueriesViewHolder holder, int position, @NonNull TrendingQuiresLangCList model) {

                holder.setTitle(model.getTitle());
                holder.setLikes(model.getLikes());
                holder.setTime(model.getTime());

            }

            @NonNull
            @Override
            public TrendingQueriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View plcView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.trendin_quries_list_item, viewGroup, false);

                return new TrendingQueriesViewHolder(plcView);
            }
        };

        plcFirebaseRecyclerAdapter.startListening();
        plcRecyclerView.setAdapter(plcFirebaseRecyclerAdapter);
    }

    class TrendingQueriesViewHolder extends RecyclerView.ViewHolder{

        private TextView tqvhTitleView,tqvhTimeView,tqvhLikeView;
        View tqvhView;

        public TrendingQueriesViewHolder(@NonNull View itemView) {

            super(itemView);
            tqvhView = itemView;
        }

        public void setTitle(String title) {
            tqvhTitleView = (TextView)tqvhView.findViewById(R.id.trend_queries_question);
            tqvhTitleView.setText(title);
        }

        public void setTime(String time) {
            tqvhTimeView = (TextView)tqvhView.findViewById(R.id.trend_queries_time);
            tqvhTimeView.setText(time);
        }

        public void setLikes(int likes) {
            tqvhLikeView = (TextView)tqvhView.findViewById(R.id.trend_queries_likes);
            tqvhLikeView.setText(String.valueOf(likes));
        }

    }
}
