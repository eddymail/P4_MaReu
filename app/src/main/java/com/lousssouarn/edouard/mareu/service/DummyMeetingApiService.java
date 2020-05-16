package com.lousssouarn.edouard.mareu.service;
import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.dialog.FilterDialogFragment;
import com.lousssouarn.edouard.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public List<Meeting> getMeetingsByRoomName() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        String roomName = fragment.getRoomName();
        List<Meeting> result = new ArrayList<>();
        for(Meeting meeting : meetings) {
            if(meeting.getRoomName() == roomName ){
                result.add(meeting);
            }if(meeting.getRoomName().equals("Toutes les salles")){
                result.addAll(meetings);
            }
        }
        return result;
    }

    @Override
    public List<Meeting> getMeetingsByDate() {
        FilterDialogFragment fragment = new FilterDialogFragment();

        return null;
    }


}
