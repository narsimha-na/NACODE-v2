package in.nacode.nacode.ProgrammingLang.Bookmarks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import in.nacode.nacode.ListItems.BookmarkCNotesPost;
import in.nacode.nacode.R;


public class ProgrammingLangC_BookmarkFragmentNotes extends Fragment  {


    private  View mView;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseRecyclerOptions<BookmarkCNotesPost>  mFirebaseRecyclerOptions ;
    private FirebaseRecyclerAdapter<BookmarkCNotesPost,ProgrammingLangC_BookmarkNotesViewHolder> mFirebaseRecyclerAdapter;
    private RecyclerView mRecyclerView;

    private String currentUserId;
    private OnFragmentInteractionListener mListener;

    public ProgrammingLangC_BookmarkFragmentNotes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_programming_lang_c__bookmark_fragment_notes, container, false);

        // Setting Up the Recycler View
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.c_bookmark_notes_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUserId = mFirebaseAuth.getCurrentUser().getUid();

        //Creating Query for reading the data from Firebase database
        Query mQuery = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("bookmarks").child("notes");

        mFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<BookmarkCNotesPost>()
                .setQuery(mQuery,BookmarkCNotesPost.class)
                .build();

        //Assigning the retrieved from database to given Views
        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BookmarkCNotesPost, ProgrammingLangC_BookmarkNotesViewHolder>(mFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ProgrammingLangC_BookmarkNotesViewHolder holder, int position, @NonNull BookmarkCNotesPost model) {

                holder.setNotesDate(model.getNotesDate());
                holder.setNotesName(model.getNotesName());
                holder.setNotesLang(model.getNotesLang());

            }

            //assigning the layout of list item to  ViewHolder

            @NonNull
            @Override
            public ProgrammingLangC_BookmarkNotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.c_bookmark_notes_list_item,viewGroup,false);

                return new ProgrammingLangC_BookmarkNotesViewHolder(view);
            }
        };

        mFirebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(mFirebaseRecyclerAdapter);


        return mView;
    }




    public static class ProgrammingLangC_BookmarkNotesViewHolder extends RecyclerView.ViewHolder {

        private TextView notesNameView,notesDateView,notesLangView;
        private View mView;

        public ProgrammingLangC_BookmarkNotesViewHolder(View view) {
            super(view);
            mView = view;
        }

        public void setNotesName(String notesName) {

            notesNameView = (TextView)mView.findViewById(R.id.c_bookmark_notes_name);
            notesNameView.setText(notesName);

        }
        public void setNotesDate(String notesDate)
        {
          notesDateView = (TextView) mView.findViewById(R.id.c_bookmark_notes_date);
          notesDateView.setText(notesDate);
        }

        public void setNotesLang(String notesLang){
            notesLangView = (TextView)mView.findViewById(R.id.c_bookmark_notes_lang);
            notesLangView.setText(notesLang);
        }

    }



    //For Fragment Purpose in tabbed Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProgrammingLangC_BookmarkFragmentNotes.OnFragmentInteractionListener) {
            mListener = (ProgrammingLangC_BookmarkFragmentNotes.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
