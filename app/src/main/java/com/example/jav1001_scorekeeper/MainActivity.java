package com.example.jav1001_scorekeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String teamselected ="A";

    ArrayList<data> al = new ArrayList<>();
    myadapter myad;
    Switch switchteamscore;
    Spinner spinnergametype , spinnercountry1 ,spinnercountry2;
    ImageView imageviewteama , imageviewteamb;
    String country1array[]={"Canada","United States","Australia","England","Spain","India","Pakistan","China","Russia","Ireland"};
    String country2array[]={"India","Pakistan","China","Russia","Ireland","Canada","United States","Australia","England"};
    int country1[]  = {R.drawable.canada, R.drawable.us, R.drawable.australia,R.drawable.england , R.drawable.spain,R.drawable.india, R.drawable.pakistan, R.drawable.china,R.drawable.russia , R.drawable.ireland};
    int country2[]  = {R.drawable.india, R.drawable.pakistan, R.drawable.china,R.drawable.russia , R.drawable.ireland,R.drawable.canada, R.drawable.us, R.drawable.australia,R.drawable.england , R.drawable.spain};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnergametype = (Spinner) (findViewById(R.id.spinnergametype));
        spinnercountry1 = (Spinner) (findViewById(R.id.spinnercountry1));
        spinnercountry2 = (Spinner) (findViewById(R.id.spinnercountry2));
        imageviewteama = (ImageView) (findViewById(R.id.imageviewteama));
        imageviewteamb = (ImageView) (findViewById(R.id.imageviewteamb));
        switchteamscore = (Switch) (findViewById(R.id.switchteamscore));


        myad = new myadapter();
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,country1array);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,country2array);
        al.add(new data(R.drawable.cricket,"cricket"));
        al.add(new data(R.drawable.hockey,"hockey"));
        al.add(new data(R.drawable.football,"football"));
        al.add(new data(R.drawable.badminton,"badminton"));
        al.add(new data(R.drawable.soccer,"soccer"));

        spinnergametype.setAdapter(myad);
        spinnercountry1.setAdapter(adapter1);
        spinnercountry2.setAdapter(adapter2);


        spinnergametype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(), al.get(position).name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnercountry1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageviewteama.setImageResource(country1[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnercountry2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 imageviewteamb.setImageResource(country2[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switchteamscore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    switchteamscore.setText("Team B Selected");
                    teamselected = "B";
                }
                else
                {
                    //Toast.makeText(getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    switchteamscore.setText("Team A Selected");
                    teamselected = "A";
                }
            }
        });

    }


    class data
    {
        int image;
        String name;

        data(int image,String name)
        {
            this.image = image;
            this.name = name;
        }
    }


    class myadapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public Object getItem(int position) {
            return al.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position*10;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater l = LayoutInflater.from(getApplicationContext());
            convertView = l.inflate(R.layout.spinner_single_row,parent,false);

            ImageView imageviewgametype = (ImageView) (convertView.findViewById(R.id.imageviewgametype));
            TextView textviewgametype = (TextView) (convertView.findViewById(R.id.textviewgametype));

            imageviewgametype.setImageResource(al.get(position).image);
            textviewgametype.setText(al.get(position).name);
            return convertView;
        }
    }


}
