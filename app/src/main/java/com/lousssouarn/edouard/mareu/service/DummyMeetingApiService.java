package com.lousssouarn.edouard.mareu.service;
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
       if(!meeting.getSubject().equals("") && !meeting.getDate().equals("") && !meeting.getTime().equals("") && !meeting.getRoomName().equals("Choisir une salle") && !meeting.getParticipants().equals("")) {
           meetings.add(meeting);
       }else{
           throw new IllegalArgumentException();
       }
    }

    @Override
    public void deleteMeeting(int position) {
        meetings.remove(position);
    }

    @Override
    public List<Meeting> getMeetingsByRoomName(String roomName) {
        List<Meeting> result = new ArrayList<>();
        if(roomName.equals("Toutes les salles")){
            return meetings;
        }
        for(Meeting meeting : meetings) {
            if(meeting.getRoomName().equals(roomName)){
                result.add(meeting);
            }
        }
        return result;
    }

    @Override
    public List<Meeting> getMeetingsByDate(String date) {
        List<Meeting> result = new ArrayList<>();
        for(Meeting meeting : meetings) {
            if(meeting.getDate().equals(date)){
                result.add(meeting);
            }
        }
        return result;
    }
}
