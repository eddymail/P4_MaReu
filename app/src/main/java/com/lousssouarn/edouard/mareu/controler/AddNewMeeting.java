package com.lousssouarn.edouard.mareu.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.lousssouarn.edouard.mareu.R;

import static android.net.wifi.rtt.CivicLocationKeys.ROOM;

public class AddNewMeeting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meeting);

        //data

        String[] meetingRooms = getResources().getStringArray(R.array.mettingRooms);

        AutoCompleteTextView ediTextRoom = findViewById(R.id.actv_room);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, meetingRooms );
        ediTextRoom.setAdapter(adapter);

        String inputMeetingRoom = ediTextRoom.getText().toString();



    }
}
