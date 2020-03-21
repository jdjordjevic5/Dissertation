package com.example.dissertation;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private EditText destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView speak = findViewById(R.id.btnSpeak);
        destination = findViewById(R.id.etDestination);
        Button search = findViewById(R.id.search);

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_input = destination.getText().toString();
                if (search_input.matches("")) {
                    Menu.super.getIntent();
                    Toast.makeText(getApplicationContext(), "Please enter your destination", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Menu.this, Directions.class);
                    intent.putExtra("destination", search_input);
                    startActivity(intent);
                }
            }
        });

    }

    public void getSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "el-GR");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 100);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Intent intent = new Intent(Menu.this, Directions.class);
                assert result != null;
                intent.putExtra("destination", result.get(0));
                startActivity(intent);
            }
        }
    }
}