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

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import in.nacode.nacode.ListItems.FeedPost;
import in.nacode.nacode.ViewModel.FeedRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    View mainView;
    FloatingActionButton createPost;

    private RecyclerView feed_list_view;
    private List<FeedPost> feed_list;
    private FeedRecyclerAdapter feedRecyclerAdapter;
    private DocumentSnapshot lastVisible;

    private Boolean isFirstPageFirstLoad = true;

    private FirebaseFirestore firebaseFirestore;





    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mainView = inflater.inflate(R.layout.fragment_home, container, false);

        feed_list = new ArrayList<>();
        feed_list_view = (RecyclerView)mainView.findViewById(R.id.feeds_views);

        feedRecyclerAdapter = new FeedRecyclerAdapter(feed_list);
        feed_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        feed_list_view.setAdapter(feedRecyclerAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();

        feed_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Boolean reachedBottom = ! recyclerView.canScrollVertically(1);

                if(reachedBottom){

                    loadMore();
                }

            }
        });


        Query firstQuery = firebaseFirestore.collection("notification").orderBy("timestamp",Query.Direction.DESCENDING).limit(5);
        firstQuery.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(!queryDocumentSnapshots.isEmpty()){

                    if(isFirstPageFirstLoad){

                        lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() -1);
                        feed_list.clear();

                    }

                    for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){

                        if(doc.getType() == DocumentChange.Type.ADDED){

                            FeedPost feedPost = doc.getDocument().toObject(FeedPost.class);

                            if(isFirstPageFirstLoad){

                                feed_list.add(feedPost);

                            }else{

                                feed_list.add(0,feedPost);

                            }


                            feedRecyclerAdapter.notifyDataSetChanged();

                        }
                    }

                    isFirstPageFirstLoad = false;

                }

            }
        });




        createPost = (FloatingActionButton)mainView.findViewById(R.id.createButton);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent na = new Intent(getActivity(),CreatePost.class);
                getActivity().startActivity(na);

            }
        });

        return mainView;
    }


    public void loadMore(){

        Query secondQuery = firebaseFirestore.collection("notification")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .startAfter(lastVisible)
                .limit(3);
        secondQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

              if(!queryDocumentSnapshots.isEmpty()){

                  lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() -1);

                  for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){

                      if(doc.getType() == DocumentChange.Type.ADDED){


                          FeedPost feedPost = doc.getDocument().toObject(FeedPost.class);
                          feed_list.add(feedPost);

                          feedRecyclerAdapter.notifyDataSetChanged();

                      }
                  }
              }

            }
        });

    }

}
