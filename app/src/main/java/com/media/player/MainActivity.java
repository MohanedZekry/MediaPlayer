package com.media.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> examples = new ArrayList<>();
        examples.add("Simple Audio Player");
        examples.add("Video Player");
        examples.add("Capture Photo");
        examples.add("Media Picker");

        ArrayAdapter<String> AA = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, R.id.textView, examples);
        listView.setAdapter(AA);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent i = null;
                if (position == 0) {
                    i = new Intent(getApplicationContext(), SimpleAudioPlayerActivity.class);
                } else if (position == 1)
                    i = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                else if (position == 2)
                    i = new Intent(getApplicationContext(), CapturePhotoActivity.class);
                else
                    i = new Intent(getApplicationContext(), MediaPickerActivity.class);

                startActivity(i);
            }
        });
    }
}
