package com.lousssouarn.edouard.mareu.service;

import com.lousssouarn.edouard.mareu.model.Meeting;

import java.util.List;

/**
 * Meeting API client
 */
public interface MeetingApiService {
    /**
     * Get all my Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Create a meeting
     * @param meeting
     */
    void addMeeting(Meeting meeting);

    /**
     *Delete a meeting
     * @param position
     */
    void deleteMeeting(int position);

    /**
     * Get meetings by room
     * @return List
     */
    List<Meeting> getMeetingsByRoomName(String roomName);

    /**
     * Get meetings by date
     * @return  List
     */
    List<Meeting> getMeetingsByDate(String date);
}