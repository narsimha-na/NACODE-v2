package in.nacode.nacode;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayGroundFragment extends Fragment {


    ListView list;

    String[] maintitle ={
            " C Program to add two numbers ","C ++ add hte following float numbers",
            "Java program to add eveything","Python add hte following float numbers", "HTML add hte following float numbers","C ++ add hte following float numbers","C ++ add hte following float numbers","PHP","SQL","Firebase","Swift","Web Development",
    };

    String[] date ={
      "2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018","2 July 2018",
    };

    Integer[] imgid={
            R.drawable.c,R.drawable.cpp,R.drawable.java,R.drawable.sql,
            R.drawable.html,R.drawable.css,R.drawable.js,R.drawable.php,R.drawable.sql,
            R.drawable.firebase,R.drawable.swift,R.drawable.html,
    };

    private Toolbar mTopToolbar;
    public PlayGroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View fragmentView = inflater.inflate(R.layout.fragment_play_ground, container, false);
        // Inflate the layout for this fragment

        MyListAdapter adapter=new MyListAdapter(getContext(), maintitle,date,imgid);
        list=(ListView)fragmentView.findViewById(R.id.list);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // TODO Auto-generated method stub
                if(position == 0) {
                    //code specific to first list item
                    Toast.makeText(getActivity(),"C Program to add two numbers ",Toast.LENGTH_SHORT).show();
                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getActivity(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
                }

                else if(position == 2) {

                    Toast.makeText(getActivity(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
                }
                else if(position == 3) {

                    Toast.makeText(getActivity(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
                }
                else if(position == 4) {

                    Toast.makeText(getActivity(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
                }

            }
        });




        return fragmentView ;
    }

}
