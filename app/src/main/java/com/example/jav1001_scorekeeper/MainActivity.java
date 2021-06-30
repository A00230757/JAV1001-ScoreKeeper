package com.example.jav1001_scorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import android.speech.tts.TextToSpeech;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;// media player used to play back sound in main activity
    TextToSpeech textToSpeech;// it gives welcome message , selected game type and selected teams , and also current score

    String teamselected ="A";  // to store current selected team
    String gametype="Cricket"; // to store selected game type
    String teamaname="Canada"; // to store team A name
    String teambname ="India"; // to store team B name
    int teamascore =0;  //variable to store  team A score , initialized to 0
    int teambscore =0;  //variable to store  team B score , initialized to 0
    int currentselectedpoints = 2; //variable to store  current selected points based on selected radio button

    ArrayList<data> arraygametype = new ArrayList<>(); // array list in which some type of games type name with image  are stored to show in (spinnergametype ).
    myadapter myad; // custom adapter which is linked with  spinner(spinnergametype) to show game type with its image
    Switch switchteamscore; // to swith team which is currently scoring
    Spinner spinnergametype , spinnercountry1 ,spinnercountry2; // three spinners to store gametype(name with image) , team A (names), team B(names) data respectively.
    ImageView imageviewteama , imageviewteamb; // image views used to display selected country flags
    Button btnplus , btnminus; // buttons to increase and decrease score
    RadioButton radiobutton2points,radiobutton4points,radiobutton6points,radiobutton8points; // radio buttons to change score points
    RadioGroup rbg;// radio button group which is binded with radio buttons
    CheckBox checkboxgamemusic;// check box at top on or off background music in app
    TextView textviewteam1score , textviewteam2score; // text view to show teamA and B score

    String country1array[]={"Canada","United States","Australia","England","Spain","India","Pakistan","China","Russia","Ireland"};//to store country names , used for (spinnercountry1)
    String country2array[]={"India","Pakistan","China","Russia","Ireland","Canada","United States","Australia","England"};//to store country names , used for (spinnercountry2)
    int country1[]  = {R.drawable.canada, R.drawable.us, R.drawable.australia,R.drawable.england , R.drawable.spain,R.drawable.india, R.drawable.pakistan, R.drawable.china,R.drawable.russia , R.drawable.ireland};//to store country flags images, to update on imageviewteama as per selected position of spinnercountry1
    int country2[]  = {R.drawable.india, R.drawable.pakistan, R.drawable.china,R.drawable.russia , R.drawable.ireland,R.drawable.canada, R.drawable.us, R.drawable.australia,R.drawable.england , R.drawable.spain};//to store country flags images, to update on imageviewteamb as per selected position of spinnercountry2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() { // initilzation( to give memory) to text to speech
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);// set language as per user choice
                }
            }
        });

          // giving memory to spinners , image views , switch , button , check box , radiobutton , radio button group , text view
        spinnergametype = (Spinner) (findViewById(R.id.spinnergametype));
        spinnercountry1 = (Spinner) (findViewById(R.id.spinnercountry1));
        spinnercountry2 = (Spinner) (findViewById(R.id.spinnercountry2));
        imageviewteama = (ImageView) (findViewById(R.id.imageviewteama));
        imageviewteamb = (ImageView) (findViewById(R.id.imageviewteamb));
        switchteamscore = (Switch) (findViewById(R.id.switchteamscore));
        btnplus=(Button)(findViewById(R.id.btplus));
        btnminus=(Button)(findViewById(R.id.btminus));
        checkboxgamemusic = (CheckBox) (findViewById(R.id.checkboxgamemusic));
        radiobutton2points= (RadioButton) (findViewById(R.id.radiobutton2points));
        radiobutton4points= (RadioButton) (findViewById(R.id.radiobutton4points));
        radiobutton6points= (RadioButton) (findViewById(R.id.radiobutton6points));
        radiobutton8points= (RadioButton) (findViewById(R.id.radiobutton8points));
        textviewteam1score = (TextView) (findViewById(R.id.textviewteam1score));
        textviewteam2score = (TextView) (findViewById(R.id.textviewteam2score));
        rbg = (RadioGroup) (findViewById(R.id.rg1));


        myad = new myadapter(); // to give memory to custom adapter binded with spinnergametype
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,country1array); // adapter binded with spinnercountry1
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,country2array);//adapter binded with spinnercountry2

        //adding some data(game name with image) to arraygametype for spinnergametype
        arraygametype.add(new data(R.drawable.cricket,"Cricket"));
        arraygametype.add(new data(R.drawable.football,"American Football"));
        arraygametype.add(new data(R.drawable.freestylewrestling,"Freestyle Wrestling"));
        arraygametype.add(new data(R.drawable.basketball,"BasketBall"));
        arraygametype.add(new data(R.drawable.hockey,"Hockey"));
        arraygametype.add(new data(R.drawable.badminton,"Badminton"));
        arraygametype.add(new data(R.drawable.soccer,"Soccer"));

        //to bind spinners with adapters
        spinnergametype.setAdapter(myad);
        spinnercountry1.setAdapter(adapter1);
        spinnercountry2.setAdapter(adapter2);


        spinnergametype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// spinner listener when we change game type
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(), al.get(position).name, Toast.LENGTH_SHORT).show();
                gametype = arraygametype.get(position).name;// change gametype value globally
                welcomeMessage();// to give message which game type , team A country and team B country is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnercountry1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// spinner listener when we change Team A country
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageviewteama.setImageResource(country1[position]);// change image of country flag as per country selected for Team A
                teamaname = country1array[position]; //to update team A country name globally
                welcomeMessage();// to give message which game type , team A country and team B country is selected


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnercountry2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // spinner listener when we change team b country
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 imageviewteamb.setImageResource(country2[position]);// change image of country flag as per country selected for Team B
                teambname = country2array[position];//to update team B country name globally
                welcomeMessage();// to give message which game type , team A country and team B country is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switchteamscore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {// switch listener when we switch team from A to B or from B to A for scoring
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    switchteamscore.setText("Team B Selected"); // to update on txg view which team is currently selected
                    teamselected = "B"; // keep currently selected team for scoring  globally , we use it while scoring
                }
                else
                {
                    //Toast.makeText(getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    switchteamscore.setText("Team A Selected");
                    teamselected = "A";
                }
            }
        });

        checkboxgamemusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {// checkbox listener to on / off background music
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.b);// memory given to media player
                    mp.setLooping(true);// to play sound continuously set it true
                    mp.start(); // start sound

                }
                else
                {
                    mp.stop(); // stop sound

                }
            }
        });

        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {// radio button group listener when we change score points value
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radiobutton2points) // chk if radio button of 2 points selected
                {
                    currentselectedpoints =2; // change current selected points to 2 globally , which we use when increasing or decreasing score

                }
                else if(checkedId==R.id.radiobutton4points)//chk if radio button of 4 points selected
                {
                    currentselectedpoints =4;// change current selected points to 4 globally , which we use when increasing or decreasing score
                }
               else  if(checkedId==R.id.radiobutton6points)//chk if radio button of 6 points selected
                {
                    currentselectedpoints =6;// change current selected points to 6 globally , which we use when increasing or decreasing score
                }
                else if(checkedId==R.id.radiobutton8points)//chk if radio button of 8 points selected
                {
                    currentselectedpoints =8;// change current selected points to 8 globally , which we use when increasing or decreasing score
                }
                else{

                }
            }
        });

    }

    public void welcomeMessage(){// this function is used to give message when game type , team A or team B is changed
        //initialize score of both teams to 0, as teams or gametype is changed
        teamascore =0;//
        textviewteam1score.setText("Score: "+teamascore);
        teambscore =0;
        textviewteam2score.setText("Score: "+teambscore);
        textToSpeech.speak("Welcome to score keeper selected game type  is "+ gametype +" and  team A is "+teamaname+" teams B is "+teambname, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void increaseScore(View view){//this function is used to increase score of currently scoring team
        //this function chk with control statements which team is selected for scoring using some global variables value
        if( teamselected.equals("A")){
            teamascore = teamascore + currentselectedpoints;
            textviewteam1score.setText("Score: "+teamascore);//update score on text view
            textToSpeech.speak(teamaname+" team A score is "+teamascore, TextToSpeech.QUEUE_FLUSH, null);//give message of current score

        }
        else{// similarly else case for team B
            teambscore =teambscore + currentselectedpoints;
            textviewteam2score.setText("Score: "+teambscore);
            textToSpeech.speak(teambname+ " team B  score is "+teambscore, TextToSpeech.QUEUE_FLUSH, null);
        }

    }


    //all the functionality of decrease function is same as increase function except it decrease score
    public void decreaseScore(View view){
        if( teamselected.equals("A")){
            teamascore =teamascore - currentselectedpoints;
            textviewteam1score.setText("Score: "+teamascore);
            textToSpeech.speak(teamaname+" team A score is "+teamascore, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            teambscore =teambscore - currentselectedpoints;
            textviewteam2score.setText("Score: "+teambscore);
            textToSpeech.speak(teambname +" team B score is "+teambscore, TextToSpeech.QUEUE_FLUSH, null);
        }
    }



    // this is inner class to store objects data(name and image of game) in arraygametype for spinner game type
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

//this is custom adapter which help to fetch data from  arraygametype and show it in spinnergametype
    class myadapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return arraygametype.size();
        }// to count array size

        @Override
        public Object getItem(int position) {// to get aray objects
            return arraygametype.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position*10;
        }// get item positions

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {// there is a separate layout file spinner_single_row.xml
            // this method of custom adapter first set data depending on position selected on single item of spinner_single_row.xml and then store in spinnergametype
            LayoutInflater l = LayoutInflater.from(getApplicationContext());
            convertView = l.inflate(R.layout.spinner_single_row,parent,false);

            // these image view and text view are from spinner_single_row.xml
            ImageView imageviewgametype = (ImageView) (convertView.findViewById(R.id.imageviewgametype));
            TextView textviewgametype = (TextView) (convertView.findViewById(R.id.textviewgametype));

            //arraygametype data is set on these and then a view is returned to embed it on spinner game type
            imageviewgametype.setImageResource(arraygametype.get(position).image);
            textviewgametype.setText(arraygametype.get(position).name);
            return convertView;
        }
    }


}
