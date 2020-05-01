package com.lousssouarn.edouard.mareu.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMeeting extends AppCompatActivity {

    private MeetingApiService mApiService;
    private String inputMeetingRoom;
    private int newMeetingColor;

    AutoCompleteTextView mEdiTextRoomInput;
    EditText mEditTextNameInput;
    EditText mEditTextDateTimeInput;
    EditText mEditTextParticipantsInput;
    Button mButtonNewMeeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meeting);

        mApiService = DI.getMeetingApiService();

        // hiding keyboard when EditText is click
        mEditTextDateTimeInput = findViewById(R.id.et_date_time);
        mEditTextDateTimeInput.setInputType(InputType.TYPE_NULL);

        //AutoComplete meeting room list
        String[] meetingRooms = getResources().getStringArray(R.array.nameRooms);
        mEdiTextRoomInput = findViewById(R.id.actv_room);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meetingRooms);
        mEdiTextRoomInput.setAdapter(adapter);


        //add new meeting when button is clicked
        mButtonNewMeeting = findViewById(R.id.bt_new_meeting);
        mButtonNewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMeeting();
            }
        });

        //edit Date and Time

        mEditTextDateTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog(mEditTextDateTimeInput);
            }
        });

    }

    private void showDateTimePickerDialog(EditText editTextDateTimeInput) {
        // creating a calendar instance
        final Calendar calendar = Calendar.getInstance();
        // creating a DatePicker DateSet Listener
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //creating a TimePicker TimeSet Listner
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        //formatting date and time
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        //setting the value in EditText
                        mEditTextDateTimeInput.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };
                new TimePickerDialog(AddMeeting.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

            }
        };

        new DatePickerDialog(AddMeeting.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
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
        mEditTextNameInput = findViewById(R.id.et_name);
        mEditTextDateTimeInput = findViewById(R.id.et_date_time);
        mEditTextParticipantsInput = findViewById(R.id.et_participants);
        int color = getRoomColor(mEdiTextRoomInput);
        Meeting meeting = new Meeting(
                color,
                mEditTextNameInput.getText().toString(),
                mEditTextDateTimeInput.getText().toString(),
                mEdiTextRoomInput.getText().toString(),
                mEditTextParticipantsInput.getText().toString()
        );
        mApiService.addMeeting(meeting);
        finish();
    }


}
