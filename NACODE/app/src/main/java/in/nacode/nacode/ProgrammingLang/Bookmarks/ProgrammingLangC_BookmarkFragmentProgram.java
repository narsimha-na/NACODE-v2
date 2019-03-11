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
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import in.nacode.nacode.ListItems.BookmarkCProgramsPost;
import in.nacode.nacode.R;


public class ProgrammingLangC_BookmarkFragmentProgram extends Fragment  {
    
    private  View pView;
    private String pCurrentUid;
    
    private FirebaseAuth pFirebaseAuth;
    private FirebaseRecyclerOptions<BookmarkCProgramsPost> pFirebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<BookmarkCProgramsPost,BookmarkCProgramsViewHolder> pFirebaseRecyclerAdapter;

   
    private RecyclerView pRecyclerView;
    private OnFragmentInteractionListener mListener;
    

    public ProgrammingLangC_BookmarkFragmentProgram() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pView = inflater.inflate(R.layout.fragment_programming_lang_c__bookmark_fragment_program, container, false);
        
        pRecyclerView = (RecyclerView)pView.findViewById(R.id.bookmark_program_recyclerView);
        LinearLayoutManager pLinearLayoutManager = new LinearLayoutManager(getActivity());
        pRecyclerView.setLayoutManager(pLinearLayoutManager);
        
        pFirebaseAuth = FirebaseAuth.getInstance();
        pCurrentUid = pFirebaseAuth.getCurrentUser().getUid();

        Query pQuery = FirebaseDatabase.getInstance().getReference().child("users").child(pCurrentUid).child("bookmarks").child("programs");

        pFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<BookmarkCProgramsPost>()
                .setQuery(pQuery,BookmarkCProgramsPost.class)
                .build();

        pFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BookmarkCProgramsPost, BookmarkCProgramsViewHolder>(pFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull BookmarkCProgramsViewHolder holder, int position, @NonNull BookmarkCProgramsPost model) {

                holder.setpName(model.getpName());
                holder.setpDate(model.getpDate());
                holder.setpLang(model.getpLang());
            }

            @NonNull
            @Override
            public BookmarkCProgramsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.c_bookmark_programs_list_item,viewGroup,false);
                
                return new BookmarkCProgramsViewHolder(view);
            }
        };

        pFirebaseRecyclerAdapter.startListening();
        pRecyclerView.setAdapter(pFirebaseRecyclerAdapter);
        
        return pView;
    }

    private class  BookmarkCProgramsViewHolder extends RecyclerView.ViewHolder{

        private View pView;
        private TextView pNameView,pDateView,pLangView;

        public BookmarkCProgramsViewHolder(@NonNull View itemView) {
            super(itemView);
            pView = itemView;
        }

        public void setpName(String pName) {

            pNameView = (TextView)pView.findViewById(R.id.bookmark_program_name);
            pNameView.setText(pName);

        }

        public void setpDate(String pDate) {

            pDateView = (TextView)pView.findViewById(R.id.bookmark_program_date);
            pDateView.setText(pDate);

        }

        public void setpLang(String pLang){

            pLangView = (TextView)pView.findViewById(R.id.bookmark_program_lang);
            pLangView.setText(pLang);
            
        }
    }


    



    //For Fragment Purpose in tabbed Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProgrammingLangC_BookmarkFragmentProgram.OnFragmentInteractionListener) {
            mListener = (ProgrammingLangC_BookmarkFragmentProgram.OnFragmentInteractionListener) context;
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
