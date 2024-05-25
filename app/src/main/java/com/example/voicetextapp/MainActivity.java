package com.example.voicetextapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
TextView txt_Voice;
Spinner spn_Lang;
Button btn_SendWA;
ImageButton btn_Voice;
ArrayAdapter<String> adapter;
String[] Lang_code = {"te-IN","kn-IN","hi-IN","ta-IN","gu-IN","en-IN"};
String[] Lang_name = {"Telugu","Kannada","Hindi","Tamil","Gujarathi","English"};
String Lcode ;
static final int RESULT_SPEECH = 1;
ArrayList<String> text ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_Voice = (TextView) findViewById(R.id.txt_voice);
        spn_Lang = (Spinner) findViewById(R.id.spn_lang);
        btn_SendWA = (Button)  findViewById(R.id.btn_sendwa);
        btn_Voice = (ImageButton) findViewById(R.id.btn_voice);
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Lang_name);

        spn_Lang.setAdapter(adapter);

        spn_Lang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int j=spn_Lang.getSelectedItemPosition();

                switch(j){
                    case 0 : Lcode = Lang_code[0];
                        break;
                    case 1 : Lcode = Lang_code[1];
                        break;
                    case 2 : Lcode = Lang_code[2];
                        break;
                    case 3 : Lcode = Lang_code[3];
                        break;
                    case 4 : Lcode = Lang_code[4];
                        break;
                    case 5 : Lcode = Lang_code[5];
                        break;
                    default : Lcode = Lang_code[5];
                        break;
                }
            }
        });
        btn_Voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voiceintent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voiceintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                voiceintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Lcode);
                startActivityForResult(voiceintent,RESULT_SPEECH);

            }
        });
        btn_SendWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String watext=txt_Voice.getText().toString();
            Intent shareintent =new Intent(Intent.ACTION_SEND);
            shareintent.putExtra(Intent.EXTRA_TEXT,watext);
            shareintent.setType("text/plain");
            shareintent.setPackage("com.whatsapp");
            startActivity(shareintent);
            }
        });
    }
    @Override
    protected void  onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
        text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        break;
    }
            if(txt_Voice.findFocus()==txt_Voice){
                txt_Voice.append(text.get(0)+" ");
            }


    }
}
