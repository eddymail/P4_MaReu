package com.lousssouarn.edouard.mareu.controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.fragments.TimePickerFragment;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;

public class AddMeeting extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private MeetingApiService mApiService;
    private String inputMeetingRoom;
    private int newMeetingColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meeting);

        mApiService = DI.getMeetingApiService();

        //AutoComplete meeting room list
        String[] meetingRooms = getResources().getStringArray(R.array.nameRooms);
        AutoCompleteTextView mEdiTextRoomInput = findViewById(R.id.actv_room);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meetingRooms);
        mEdiTextRoomInput.setAdapter(adapter);



        //open TimePickerFragment when a click is performed
        Button mTimeButton = findViewById(R.id.bt_time_piker);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //add new meeting when button is clicked
        Button mButtonNewMeeting = findViewById(R.id.bt_new_meeting);
        mButtonNewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMeeting();
            }
        });

    }
    //Set the chosen time in the TextView
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView mTimeTextView = findViewById(R.id.tv_time);
        mTimeTextView.setText(hourOfDay + " : " + minute);
    }

    //Get the color of the room according to the chosen room
    public int getRoomColor(AutoCompleteTextView mEdiTextRoomInput) {
        switch (inputMeetingRoom = mEdiTextRoomInput.getText().toString()) {
            case "Bangkok":
                newMeetingColor = 0xFFEF5350;
                break;
            case "Londres":
                newMeetingColor = 0xFFEC407A;
                break;
            case "Paris":
                newMeetingColor = 0xFFAB47BC;
                break;
            case "Dubaï":
                newMeetingColor = 0xFF7E57C2;
                break;
            case "Singapour":
                newMeetingColor = 0xFF5C6BC0;
                break;
            case "New York":
                newMeetingColor = 0xFF42A5F5;
                break;
            case "Kuala Lampour":
                newMeetingColor = 0xFF26C6DA;
                break;
            case "Tokyo":
                newMeetingColor = 0xFF26A69A;
                break;
            case "Istanbul":
                newMeetingColor = 0xFF66BB6A;
                break;
            case "Séoul":
                newMeetingColor = 0xFFFFEB3B;
                break;
        }

        return newMeetingColor;
    }

    //Add the created meeting
    public void addMeeting() {
        AutoCompleteTextView mEdiTextRoomInput = findViewById(R.id.actv_room);
        EditText mEditTextNameInput = findViewById(R.id.et_name);
        TextView mEditTextDateInput = findViewById(R.id.tv_time);
        EditText mEditTextParticipantsInput = findViewById(R.id.et_participants);
        int color = getRoomColor(mEdiTextRoomInput);
        Meeting meeting = new Meeting(
                color,
                mEditTextNameInput.getText().toString(),
                mEditTextDateInput.getText().toString(),
                mEdiTextRoomInput.getText().toString(),
                mEditTextParticipantsInput.getText().toString()
        );
        mApiService.addMeeting(meeting);
        finish();
    }


}
