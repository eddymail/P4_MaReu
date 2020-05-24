package com.lousssouarn.edouard.mareu.dialog;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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
    private MeetingRecyclerViewAdapter mAdapter = null;
    private List<Meeting> mMeetings;
    private String mRoomName;
    private String mDate;

    Spinner mRoomInput;
    EditText mDateInput;
    EditText mEndDateInput;
    Button mRoomFilter;
    Button mDateFilter;

    public void setParentAdapter(MeetingRecyclerViewAdapter adapter){
        this.mAdapter = adapter;
    }

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
        //Filter by room when button is click
        mRoomFilter = view.findViewById(R.id.bt_room_filter);
        mRoomFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetings = mApiService.getMeetingsByRoomName(mRoomName);
                if(mAdapter != null) {
                    mAdapter.upDateMeetings(mMeetings);
                }
                dismiss();
            }
        });

        //DateTimePikerDialog
        mDateInput = view.findViewById(R.id.et_date_input);
        mDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(mDateInput);
            }
        });

        //Filter by date when button is click
        mDateFilter = view.findViewById(R.id.bt_date_filter);
        mDateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetings = mApiService.getMeetingsByDate(mDateInput.getText().toString());
                if(mAdapter != null) {
                    mAdapter.upDateMeetings(mMeetings);
                }
                dismiss();
            }
        });

        return view;
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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                editTextToUpdate.setText(simpleDateFormat.format(calendar.getTime()));
                //Recovery the selected date
                mDate = mDateInput.getText().toString();

            }
        };
        new DatePickerDialog(getView().getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }



    public String getRoomName(){
        return mRoomName;
    }
    public String getDate() {return mDate;}



    @Override
    public void onResume() {
        super.onResume();
    }
}
