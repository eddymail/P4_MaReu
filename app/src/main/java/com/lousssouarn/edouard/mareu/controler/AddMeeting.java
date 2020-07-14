package com.lousssouarn.edouard.mareu.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.DummyMeetingGenerator;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MeetingApiService mApiService;
    private int newMeetingColor;
    private String room;
    private int color;
    private String date;
    private String time;
    private String name;
    private String participants;


    Spinner mSpinner;
    EditText mNameInput;
    EditText mDateInput;
    EditText mTimeInput;
    EditText mParticipantsInput;
    Button mButtonNewMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meeting);

        mApiService = DI.getMeetingApiService();

        mDateInput = findViewById(R.id.et_date_input);
        mTimeInput = findViewById(R.id.et_time_input);
        mSpinner = findViewById(R.id.sp_room);
        mNameInput = findViewById(R.id.et_name);
        mParticipantsInput = findViewById(R.id.et_participants);
        mButtonNewMeeting = findViewById(R.id.bt_new_meeting);

        // hiding keyboard when EditText is click
        mDateInput.setInputType(InputType.TYPE_NULL);
        mTimeInput.setInputType(InputType.TYPE_NULL);

        //Spinner meeting room list
        List<Meeting> meetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        List<String> result = new ArrayList<>();
        for (Meeting meeting : meetings) {
           String roomName = meeting.getRoomName();
           result.add(roomName);
        }
        result.add(0,"Choisir une salle");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, result);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        //edit Date and Time
        mDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(mDateInput);
            }
        });
        mTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimeDialog(mTimeInput);
            }
        });

        //add new meeting when button is clicked
        mButtonNewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //collect user input
                name = mNameInput.getText().toString();
                date = mDateInput.getText().toString();
                time = mTimeInput.getText().toString();
                participants = mParticipantsInput.getText().toString();

                //check if all data have been completed
                if(room.matches("Choisir une salle"))
                    Toast.makeText(AddMeeting.this, "Vous devez renseigner une salle de réunion !", Toast.LENGTH_SHORT).show();
                else if (name.matches(""))
                    Toast.makeText(AddMeeting.this, "Vous devez renseigner un nom de réunion !", Toast.LENGTH_SHORT).show();
                else if (date.matches(""))
                    Toast.makeText(AddMeeting.this, "Vous devez renseigner une date de réunion !", Toast.LENGTH_SHORT).show();
                else if (time.matches(""))
                    Toast.makeText(AddMeeting.this, "Vous devez renseigner une heure de réunion !", Toast.LENGTH_SHORT).show();
                else if (participants.matches(""))
                    Toast.makeText(AddMeeting.this, "Vous devez renseigner un participant !", Toast.LENGTH_SHORT).show();
                else {
                    createMeeting();
                    finish();
                }
            }
        });
    }

    //Get the meeting room chose
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        room = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDateDialog(EditText editTextDateInput) {
        final Calendar calendar = Calendar.getInstance();
        //creating a DateSet Listener
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Set the Date Value into Calendar
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //Formatting the Date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                //Setting Date into EditText
                mDateInput.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        //creating DatePicker Dialog
        new DatePickerDialog(AddMeeting.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void ShowTimeDialog(EditText editTextTimeInput) {
        final Calendar calendar = Calendar.getInstance();
        //creating a TimeSet Listener
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Set the Time Value into Calendar
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                //Formatting the Time
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm");
                //Setting Time into EditText
                mTimeInput.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        //creating TimePiker Dialog
        new  TimePickerDialog(AddMeeting.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    //Get the color of the room according to the chosen room
    public int getRoomColor(Spinner mSpinner) {
        switch (room) {
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

    //Add the created meeting to meetings list
    public void createMeeting() {

        color = getRoomColor(mSpinner);

        Meeting meeting = new Meeting(color,name, date, time, room, participants);

        try {
            mApiService.addMeeting(meeting);
        }
        catch (IllegalArgumentException e)
        {
            // todo error toast
            Toast.makeText(getBaseContext(),"Input Field is Empty", Toast.LENGTH_LONG).show();
        }
    }


}
