package in.nacode.nacode;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import in.nacode.nacode.ProgrammingLang.ProgrammingLangC;


public class ProgrammingLangFragment extends Fragment {

    private CardView plCardViewC;


    public ProgrammingLangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_programming_lang, container, false);

        Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Programming Language");

        plCardViewC = (CardView) fragmentView.findViewById(R.id.programing_lang_c);
        plCardViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent na = new Intent(getActivity(),ProgrammingLangC.class);
                getActivity().startActivity(na);
            }
        });


        return fragmentView;
    }

}

