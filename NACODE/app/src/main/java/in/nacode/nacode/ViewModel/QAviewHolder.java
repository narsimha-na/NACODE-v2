package in.nacode.nacode.ViewModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Date;

import in.nacode.nacode.R;

public class
QAviewHolder extends RecyclerView.ViewHolder {

    public TextView qQuestion,qTimeView,qTopicView,qLikesCount;
    public ImageView qLike;
    public View mView;

    private String currentUserID;
    private String likesCount;
    private DatabaseReference likesReference;

    public QAviewHolder(View itemView) {

        super(itemView);
        mView = itemView;

        qLikesCount = (TextView)itemView.findViewById(R.id.qa_like_count);
        qLike = (ImageView)itemView.findViewById(R.id.qa_like);
        qQuestion = (TextView) mView.findViewById(R.id.qa_question);




    }

    public void setLikeButtonStatus(final String qaTitle){

        likesReference = FirebaseDatabase.getInstance().getReference().child("qa").child(qaTitle).child("likes");
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        likesReference.keepSynced(true);

        likesReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChild(currentUserID)){


                    qLike.setImageResource(R.drawable.qa_like_fill);
                    likesCount =  Integer.toString((int) dataSnapshot.getChildrenCount());
                    qLikesCount.setText(likesCount+" likes");


                }else{

                    qLike.setImageResource(R.drawable.qa_like);
                    likesCount = Integer.toString((int) dataSnapshot.getChildrenCount());
                    qLikesCount.setText(likesCount+" likes");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setqTitle(String qTitle){

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
