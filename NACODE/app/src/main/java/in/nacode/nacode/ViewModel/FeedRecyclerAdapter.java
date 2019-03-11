package in.nacode.nacode.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import in.nacode.nacode.ListItems.FeedPost;
import in.nacode.nacode.FullFeedView;
import in.nacode.nacode.R;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder> {

    private List<FeedPost> feed_list;

    private Context context;

    private FirebaseFirestore firebaseFirestore;

    private FirebaseAuth firebaseAuth;

    private  String dateString;

    public FeedRecyclerAdapter(List<FeedPost> feed_list) {


        this.feed_list = feed_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        // Getting the details from HomeFragmentView
        final String desc_data = feed_list.get(position).getDesc();

        final String title_data = feed_list.get(position).getTitle();
        holder.setTitleText(title_data);

        final String image_uri = feed_list.get(position).getImg_url();
        holder.setImageView(image_uri);

        try{
            long millisecond = feed_list.get(position).getTimestamp().getTime();
            dateString = TimeAgo.using(millisecond);
            holder.setTimeView(dateString);
        }catch(Exception e){

            e.printStackTrace();
        }

        //For Feed full view

        holder.feed_fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent na = new Intent(context,FullFeedView.class);
                na.putExtra("feed_title",title_data);
                na.putExtra("feed_img",image_uri);
                na.putExtra("feed_time",holder.timeView.getText());
                na.putExtra("feed_desc",desc_data);
                context.startActivity(na);
            }
        });


        /*
        All the three functions are for the likes buttons
        */

        //Get Like Count
        firebaseFirestore.collection("notification").document(title_data).collection("likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (!queryDocumentSnapshots.isEmpty()) {

                    int count = queryDocumentSnapshots.size();
                    holder.updateLikesCount(count);
                } else {
                    holder.updateLikesCount(0);
                }
            }
        });


        //Like Button Change
        firebaseFirestore.collection("notification").document(title_data).collection("likes").document(firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        holder.likeBtn.setImageDrawable(context.getDrawable(R.drawable.like_love_white_fill));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        holder.likeBtn.setImageDrawable(context.getDrawable(R.drawable.like_love_white));
                    }
                }
            }
        });


        //Like Feature
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("notification").document(title_data).collection("likes").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {

                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put("time", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("notification").document(title_data).collection("likes").document(firebaseAuth.getCurrentUser().getUid()).set(likesMap);

                        } else {
                            firebaseFirestore.collection("notification").document(title_data).collection("likes").document(firebaseAuth.getCurrentUser().getUid()).delete();
                        }

                    }
                });
            }
        });




        /*
        All the three functions are for the bookmark buttons
        */




        //Bookmark Button Change
        firebaseFirestore.collection("notification").document(title_data).collection("bookmark").document(firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        holder.bookmarkBtn.setImageDrawable(context.getDrawable(R.drawable.bookmark_white_fill));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        holder.bookmarkBtn.setImageDrawable(context.getDrawable(R.drawable.bookmark_white));
                    }
                }
            }
        });


        //Like Feature
        holder.bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("notification").document(title_data).collection("bookmark").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {

                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put("time", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("notification").document(title_data).collection("bookmark").document(firebaseAuth.getCurrentUser().getUid()).set(likesMap);

                        } else {
                            firebaseFirestore.collection("notification").document(title_data).collection("bookmark").document(firebaseAuth.getCurrentUser().getUid()).delete();
                        }

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return feed_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView descView, titleView;
        private TextView timeView;

        private ImageView imgView;

        private ImageView likeBtn;
        private TextView likesCount;

        private ImageView bookmarkBtn;
        private TextView bookmarkLikes;

        private RelativeLayout feed_fullView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            likeBtn = mView.findViewById(R.id.nfedd_likeBtn);
            bookmarkBtn = mView.findViewById(R.id.nfeed_bookmark);
            feed_fullView = mView.findViewById(R.id.nfeed_fullview);

        }

        /*
        public void setDescText(String desctext){

            descView = mView.findViewById(R.id.nfeed_desc);
            descView.setText(desctext);
        }
        */

        public void setTitleText(String titleText) {

            titleView = mView.findViewById(R.id.nfeed_desc);
            titleView.setText(titleText);
        }

        public void setImageView(String donloadUri) {

            imgView = mView.findViewById(R.id.nfedd_img);
            Glide.with(context).load(donloadUri).into(imgView);

        }

        public void setTimeView(String time) {

            timeView = mView.findViewById(R.id.nfeed_time);
            timeView.setText(time);

        }

        public void updateLikesCount(int count) {

            likesCount = mView.findViewById(R.id.nfeed_like_count);
            likesCount.setText(count + " Likes");
        }

    }


}
