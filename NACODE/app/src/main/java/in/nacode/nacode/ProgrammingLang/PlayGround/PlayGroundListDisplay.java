package in.nacode.nacode.ProgrammingLang.PlayGround;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.nacode.nacode.ListItems.PlaygroundListPost;
import in.nacode.nacode.PlayGroundFragment;
import in.nacode.nacode.ProgrammingLang.Programs.ProgramsListDisplay;
import in.nacode.nacode.R;

public class PlayGroundListDisplay extends AppCompatActivity {

    private RecyclerView pglRecyclerView;
    private FloatingActionButton pglFloatingActionButton;
    private Dialog pglDialog;

    private FirebaseRecyclerOptions<PlaygroundListPost> pglFirebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<PlaygroundListPost,PlayGroundViewHolder> pglFirebaseRecyclerAdapter;
    private FirebaseAuth pglFirebaseAuth;
    private DatabaseReference pglDatabaseReference;

    private String pglUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_ground_list_display);

        pglDialog = new Dialog(this);

        pglFloatingActionButton = (FloatingActionButton)findViewById(R.id.playground_add_new_pg);
        pglFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        pglRecyclerView = (RecyclerView)findViewById(R.id.playground_recyclerView);
        LinearLayoutManager pglLinearLayoutManager = new LinearLayoutManager(PlayGroundListDisplay.this);
        pglRecyclerView.setLayoutManager(pglLinearLayoutManager);

        pglFirebaseAuth = FirebaseAuth.getInstance();
        pglUserId = pglFirebaseAuth.getCurrentUser().getUid();

        Query pglQuery = FirebaseDatabase.getInstance().getReference().child("users").child(pglUserId).child("playground").child("c");

        pglFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<PlaygroundListPost>()
                .setQuery(pglQuery,PlaygroundListPost.class)
                .build();

        pglFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PlaygroundListPost, PlayGroundViewHolder>(pglFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull PlayGroundViewHolder holder, final int position, @NonNull final PlaygroundListPost model) {

                holder.setName(model.getName());
                holder.setLang(model.getLang());
                holder.setTime(model.getTime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent na = new Intent(PlayGroundListDisplay.this,PlayGroundView.class);
                        na.putExtra("playgroundId",String.valueOf(getRef(position).getKey()));
                        na.putExtra("pgProgramExists",String.valueOf(0));
                        startActivity(na);
                    }
                });
            }

            @NonNull
            @Override
            public PlayGroundViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View pglView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.playground_view_list_item, viewGroup, false);

                return new PlayGroundViewHolder(pglView);
            }
        };

        pglFirebaseRecyclerAdapter.startListening();
        pglRecyclerView.setAdapter(pglFirebaseRecyclerAdapter);
    }

    private void showPopUp() {

        final EditText spuProgramName;
        final Button spuSubmitButton;
        final LinearLayout spuLinearLayout;

        pglDialog.setContentView(R.layout.playground_custom_popup);

        spuProgramName = (EditText)pglDialog.findViewById(R.id.playground_custom_popUp_program_name);
        spuSubmitButton = (Button)pglDialog.findViewById(R.id.playground_custom_popUp_submit);
        spuLinearLayout = (LinearLayout)pglDialog.findViewById(R.id.playground_custom_popUp_warning);

        spuSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pglDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(pglUserId).child("playground").child("c");
                pglDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data :dataSnapshot.getChildren()){
                            if(data.child("name").child(String.valueOf(spuProgramName.getText())).exists()){
                                spuLinearLayout.setVisibility(View.VISIBLE);
                            }else{
                                Intent na = new Intent(PlayGroundListDisplay.this,PlayGroundView.class);
                                na.putExtra("pgNewProgramName",spuProgramName.getEditableText().toString());
                                na.putExtra("pgProgramExists",String.valueOf("1"));
                                startActivity(na);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



        pglDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pglDialog.show();
    }

    class  PlayGroundViewHolder extends RecyclerView.ViewHolder{

        private View vhView;
        private TextView pglNameView,pglLangView,pglDateView;

        public PlayGroundViewHolder(@NonNull View itemView) {
            super(itemView);
            vhView = itemView;
        }

        public void setName(String name) {

            pglNameView = (TextView)vhView.findViewById(R.id.playground_program_name);
            pglNameView.setText(name);

        }

        public void setLang(String lang) {

            pglLangView = (TextView)vhView.findViewById(R.id.playground_lang);
            pglLangView.setText(lang);

        }

        public void setTime(String time) {

            pglDateView = (TextView)vhView.findViewById(R.id.playground_date);
            pglDateView.setText(time);

        }
    }
}



