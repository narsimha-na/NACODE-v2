package in.nacode.nacode;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment

        Button click = (Button) fragmentView.findViewById(R.id.logout);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"Logged Out Successfully !",Toast.LENGTH_SHORT).show();
                Intent na= new Intent(getActivity(),LoginActivity.class);
                getActivity().startActivity(na);

            }
        });


/*


        Button click1 = (Button) fragmentView.findViewById(R.id.sign);
        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent na1 = new Intent(getActivity(),Sign.class);
                getActivity().startActivity(na1);
            }
        });

*/
        return fragmentView ;
    }

}
