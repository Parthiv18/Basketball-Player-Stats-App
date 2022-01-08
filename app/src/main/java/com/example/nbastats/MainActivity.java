package com.example.nbastats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn,btn2;
    TextView text,text2,text3;
    TextInputEditText inputPlayer;
    ImageView imageView;
    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        text = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);
        text3 = (TextView) findViewById(R.id.textView3);
        inputPlayer = findViewById(R.id.input);
        imageView = (ImageView) findViewById(R.id.imageView1);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

        //String edit = inputPlayer.getText().toString();

        inputPlayer.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStats(playerInput());
                getPics(playerInput());
                text3.setText(playerInput().toString() + " 2020-2021 stats");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    setActivityBackgroundColor(Color.DKGRAY); //51-204-255
                    text.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    // The toggle is disabled
                    setActivityBackgroundColor(Color.WHITE); //255-255-255 : 51
                    text.setTextColor(Color.parseColor("#000000")); //OG is a different color
                 }
            }
        });

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
    public void openActivity2(){
        Intent intent = new Intent (this, findPlayers.class);
        startActivity(intent);
    }

    public void getStats(String word){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> nam = new ArrayList<String>();
                ArrayList<String> nam1 = new ArrayList<String>();
                StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect ("https://www.google.com/search?q="+word+"+stats").timeout(6000).get();
                    Elements temp = doc.select ("div#search");
                    int i = 0;
                    for (Element element:temp) {
                        i++;
                        String test=element.select("tr").text().toString(); //toString
                        String t = test.replace("  "," ");
                        String t1[] = t.split(" ");
                        for (int j = 0 ; j < t1.length ; j++) {
                            nam.add(t1[j]);
                            //System.out.print(t1[j] + "\n");
                        }
                        //System.out.print(nam.get(0));
                    }

                    int pos = 0;
                    for (int k = 0 ; k < nam.size(); k++) {
                        pos++;
                        if (nam.get(k).equals("2020")) {
                            for (int p = (pos+2) ; p < (pos + 10) ; p++) { //html data collection 10 is the list
                                //System.out.print(nam.get(p) + "\n");
                                builder.append(nam.get(p) + "\n");
                                nam1.add(nam.get(p));
                            }
                        }
                        //System.out.print(nam.get(k) + "\n");
                    }
                } catch (IOException e) {
                    builder.append("Not found");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("MINS: " + nam1.get(0) + "\n" +
                                "PPG: " + nam1.get(1) + "\n" +
                                "FG%: " + nam1.get(2) + "\n" +
                                "3PT%: " + nam1.get(3) + "\n" +
                                "REB: " + nam1.get(4) + "\n" +
                                "AST: " + nam1.get(5) + "\n" +
                                "STL: " + nam1.get(6) + "\n" +
                                "BLK: " + nam1.get(7)); //builder.toString()
                    }
                });
            }
        }).start();
    }

    public void getPics(String word){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String test = "";
                StringBuilder builder = new StringBuilder();
                ArrayList<String> playerName = new ArrayList<String>();
                try {
                    Document doc1 = Jsoup.connect ("https://www.google.com/search?q="+word+"+stats").timeout(6000).get ();
                    Elements temp1 = doc1.select ("div.QDIKCb"); //table.vk_tbl.Uekwlc

                    String name = "";

                    for (Element element:temp1) {
                        name=element.select("div.zxBuce.Ss2Faf.zbA8Me.V88cHc.qLYAZd").text().toString(); //gets the name of player
                        String newName[] = name.split(" ");
                        for (int j = 0 ; j < newName.length ; j++) {
                            playerName.add(newName[j]);
                        }
                    }
                    playerName.remove(2);
                    name = playerName.get(0) + " " + playerName.get(1);

                    Document doc = Jsoup.connect ("https://www.gettyimages.ca/photos/"+name+"?family=editorial&phrase="+name).timeout(6000).get ();
                    Elements temp = doc.select ("img.MosaicAsset-module__thumb___epLhd"); //table.vk_tbl.Uekwlc - gallery-asset__thumb.gallery-mosaic-asset__thumb - MosaicAsset-module__thumb___YJI_C
                    //ArrayList<String> nam = new ArrayList();
                    int i = 0;
                    for (Element element:temp) {
                        i++;
                        //System.out.print("src: " + temp.attr("src") + "\n");
                        builder.append(temp.attr("src"));
                        //String test = element.select("img").text().toString();
                        //System.out.print(test);
                        break;
                        //System.out.print(test);
                    }
                //System.out.print(test);
                } catch (IOException e) {
                    //builder.append("Not found");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //text.setText(builder.toString());
                        Glide.with(MainActivity.this).load(builder.toString()).into(imageView);
                    }
                });
            }
        }).start();
    }

    private TextWatcher textWatcher = new TextWatcher() { //for input text
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
            playerInput();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private  String playerInput (){
        String s1 = inputPlayer.getText().toString();

        /*if (s1.length() > 0) {
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }*/
        return s1;
    }

    private void checkFieldsForEmptyValues(){
        String s1 = inputPlayer.getText().toString();

        if (s1.length() > 0) {
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }
    }
}