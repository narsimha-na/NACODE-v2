package in.nacode.nacode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.nacode.nacode.ListItems.QAPost;
import in.nacode.nacode.ViewModel.QAviewHolder;

public class QAFragemnt extends Fragment {


    private FirebaseAuth firebaseAuth;

    private FloatingActionButton createQuestion;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<QAPost> options;
    private FirebaseRecyclerAdapter<QAPost, QAviewHolder> adapter;

    private String currentUserId;
    private Boolean likechecker=false;


    public QAFragemnt() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View fragmentView = inflater.inflate(R.layout.fragment_qafragemnt, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.qa_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);




        createQuestion = (FloatingActionButton)fragmentView.findViewById(R.id.qa_createQuestion);
        createQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent na = new Intent(getActivity(),CreateQuestion.class);
                getActivity().startActivity(na);

            }
        });



        Query query = FirebaseDatabase.getInstance().getReference().child("qa").limitToLast(8);

        FirebaseRecyclerOptions<QAPost> options =
                new FirebaseRecyclerOptions.Builder<QAPost>()
                        .setQuery(query, QAPost.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<QAPost, QAviewHolder>(options) {


            @Override
            public QAviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.qa_list_item, parent, false);

                return new QAviewHolder(view);
            }

            @Override
            protected void onBindViewHolder(QAviewHolder holder, int position, final QAPost model) {
                holder.setqTitle(model.getqTitle());
                holder.setqTopic(model.getqTopic());
                holder.setqTime(model.getqTime());

                holder.setLikeButtonStatus(model.getqTitle());


                holder.qLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        likechecker = true;

                       final DatabaseReference   databaseReference = FirebaseDatabase.getInstance().getReference().child("qa").child(model.getqTitle()).child("likes");

                       databaseReference.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                               if(likechecker){

                                   if (dataSnapshot.hasChild(currentUserId)){

                                       databaseReference.child(currentUserId).removeValue();
                                       likechecker = false;

                                   }else{

                                       databaseReference.child(currentUserId).setValue(true);
                                       likechecker = false;

                                   }
                               }

                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });



                    }
                });


            }
        };



        adapter.startListening();
        recyclerView.setAdapter(adapter);
        // progressBar.clearAnimation();






        return fragmentView;

    }





}

/*




recyclerView = (RecyclerView) fragmentView.findViewById(R.id.qa_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);




        createQuestion = (FloatingActionButton)fragmentView.findViewById(R.id.qa_createQuestion);
        createQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent na = new Intent(getActivity(),CreateQuestion.class);
                getActivity().startActivity(na);

            }
        });



        Query query = FirebaseDatabase.getInstance().getReference().child("qa").limitToLast(4);

        FirebaseRecyclerOptions<QAPost> options =
                new FirebaseRecyclerOptions.Builder<QAPost>()
                        .setQuery(query, QAPost.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<QAPost, QAviewHolder>(options) {


            @Override
            public QAviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.qa_list_item, parent, false);

                return new QAviewHolder(view);
            }

            @Override
            protected void onBindViewHolder(QAviewHolder holder, int position, QAPost model) {
                holder.setqTitle(model.getqTitle());
                holder.setqTopic(model.getqTopic());
                holder.setqTime(model.getqTime());
            }
        };



        adapter.startListening();
        recyclerView.setAdapter(adapter);
       // progressBar.clearAnimation();






        return fragmentView;

    }

    @Override
    public void onStart() {
        super.onStart();

    }



    public class QAviewHolder extends RecyclerView.ViewHolder {

        public TextView qQuestion,qTimeView,qLikeCount,qTopicView;
        public ImageView qLike,qUnlike;
        public View mView;

        public QAviewHolder(View itemView) {

            super(itemView);
            mView = itemView;

        }

        public void setqTitle(String qTitle){

            qQuestion = (TextView) mView.findViewById(R.id.qa_question);
            qQuestion.setText(qTitle);

        }

        public void setqTime(String qTime){

            qTimeView = (TextView) mView.findViewById(R.id.qa_time);
            qTimeView.setText(qTime);

        }

        public void setqTopic(String qTopic){

            qTopicView = (TextView)mView.findViewById(R.id.qa_topic);
            qTopicView.setText(qTopic);

        }

    }



}

 */