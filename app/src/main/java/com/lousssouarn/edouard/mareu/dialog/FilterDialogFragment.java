package com.lousssouarn.edouard.mareu.dialog;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;
import com.lousssouarn.edouard.mareu.views.MeetingRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FilterDialogFragment extends DialogFragment {
    private MeetingApiService mApiService;
    private MeetingRecyclerViewAdapter mAdapter;
    private List<Meeting> mMeetings;
    private String mRoomName;
    Spinner mRoomInput;
    EditText mStartDate;
    EditText mEndDate;
    Button mRoomFilter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        mApiService = DI.getMeetingApiService();

        //Spinner meeting room list
        mRoomInput = view.findViewById(R.id.sp_room);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.filterRooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoomInput.setAdapter(adapter);

        //Recovery the selected room
        mRoomInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoomName = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mRoomFilter = view.findViewById(R.id.bt_room_filter);
        mRoomFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetings = mApiService.getMeetingsByRoomName();
                MeetingRecyclerViewAdapter mAdapter = new MeetingRecyclerViewAdapter(mMeetings);
                mAdapter.upDateMeetings(mMeetings);
                dismiss();
            }
        });

        //DateTimePikerDialog
        mEndDate = view.findViewById(R.id.et_end);
        mStartDate = view.findViewById(R.id.et_start);

        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(mStartDate);
                mStartDate.isSelected();
            }
        });
        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(mEndDate);
            }
        });

        return view;
    }

    public String getRoomName(){
        return mRoomName;
    }

    private void showDateTimeDialog(final EditText editTextToUpdate) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Set the Date Value into Calendar
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy à HH:mm");

                        if (mStartDate.isSelected() ) {
                            mStartDate.setText(simpleDateFormat.format(calendar.getTime()));
                        }else {
                            mEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                        }
                    }
                };
                new TimePickerDialog(getView().getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        };
        new DatePickerDialog(getView().getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
