package com.example.nbastats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class findPlayers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView text1;
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_players);

        dropdown = (Spinner) findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{  "Atlanta Hawks", "Boston Celtics", "Brooklyn Nets", "Charlotte Hornets", "Chicago Bulls",
                                        "Cleveland Cavaliers", "Dallas Mavericks", "Denver Nuggets", "Detroit Pistons",
                                        "Golden State Warriors", "Houston Rockets", "Indiana Pacers", "LA Clippers",
                                        "Los Angeles Lakers", "Memphis Grizzlies", "Miami Heat","Milwaukee Bucks",
                                        "Minnesota Timberwolves", "New Orleans Pelicans", "New York Knicks", "Oklahoma City Thunder",
                                        "Orlando Magic", "Philadelphia 76ers", "Phoenix Suns", "Portland Trail Blazers",
                                        "Sacramento Kings", "San Antonio Spurs", "Toronto Raptors", "Utah Jazz",
                                        "Washington Wizards" };
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
        text1 = (TextView) findViewById(R.id.textView2);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (pos) {
            case 0:
                getPlayers("atl/atlanta-hawks");
                break;
            case 1:
                getPlayers("bos/boston-celtics");
                // Whatever you want to happen when the second item gets selected
                break;
            case 2: //
                getPlayers("bkn/brooklyn-nets");
                break;
            case 3:
                getPlayers("cha/charlotte-hornets");
                break;
            case 4:
                getPlayers("chi/chicago-bulls");
                break;
            case 5:
                getPlayers("cle/cleveland-cavaliers");
                break;
            case 6:
                getPlayers("dal/dallas-mavericks");
                break;
            case 7:
                getPlayers("den/denver-nuggets");
                break;
            case 8:
                getPlayers("det/detroit-pistons");
                break;
            case 9:
                getPlayers("gs/golden-state-warriors");
                break;
            case 10:
                getPlayers("hou/houston-rockets");
                break;
            case 11:
                getPlayers("ind/indiana-pacers");
                break;
            case 12:
                getPlayers("lac/la-clippers");
                break;
            case 13:
                getPlayers("lal/los-angeles-lakers");
                break;
            case 14:
                getPlayers("mem/memphis-grizzlies");
                break;
            case 15:
                getPlayers("mia/miami-heat");
                break;
            case 16:
                getPlayers("mil/milwaukee-bucks");
                break;
            case 17:
                getPlayers("min/minnesota-timberwolves");
                break;
            case 18:
                getPlayers("no/new-orleans-pelicans");
                break;
            case 19:
                getPlayers("ny/new-york-knicks");
                break;
            case 20:
                getPlayers("okc/oklahoma-city-thunder");
                break;
            case 21:
                getPlayers("orl/orlando-magic");
                break;
            case 22:
                getPlayers("phi/philadelphia-76ers");
                break;
            case 23:
                getPlayers("phx/phoenix-suns");
                break;
            case 24:
                getPlayers("por/portland-trail-blazers");
                break;
            case 25:
                getPlayers("sac/sacramento-kings");
                break;
            case 26:
                getPlayers("sa/san-antonio-spurs");
                break;
            case 27:
                getPlayers("tor/toronto-raptors");
                break;
            case 28:
                getPlayers("utah/utah-jazzutah/utah-jazz");
                break;
            case 29:
                getPlayers("wsh/washington-wizards");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getPlayers(String word) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> nam = new ArrayList<String>();
                try {
                    Document doc = Jsoup.connect("https://www.espn.com/nba/team/roster/_/name/"+word).timeout(6000).get();
                    Elements temp = doc.select("tbody.Table__TBODY"); //table.vk_tbl.Uekwlc

                    for (Element element : temp) {
                        String test = element.select("a.AnchorLink").text().toString(); //toString
                        String t[] = test.split("  ");
                        for (int j = 0; j < t.length; j++)
                            nam.add(t[j]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //String[] stockArr = new String[nam.size()];
                        //stockArr = nam.toArray(stockArr);
                        StringBuilder jokeStringBuilder = new StringBuilder();
                        for (String s : nam) {
                            jokeStringBuilder.append(s + "\n");
                        }
                        text1.setText(jokeStringBuilder.toString());
                    }
                });
            }
        }).start();
    }
}