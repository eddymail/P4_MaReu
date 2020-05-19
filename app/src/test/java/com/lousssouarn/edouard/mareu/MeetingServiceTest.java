package com.lousssouarn.edouard.mareu;

import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.DummyMeetingGenerator;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;


import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService mService;

    @Before
    public  void setup() {
        mService = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = mService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {

    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meetingToAdd = new Meeting(0xFFEF5350,"Test meeting", "10-05-2020", "09h00","Bangkok","eddy@lamzon.com");
        mService.addMeeting(meetingToAdd);
        assertTrue(mService.getMeetings().contains(meetingToAdd));
    }

    @Test
    public void filterMeetingByRoom() {
        Meeting meetingTakesPlaceInBangkok = new Meeting(0xFFEF5350,"Test meeting", "10-05-2020", "09h00","Bangkok","eddy@lamzon.com");
        mService.addMeeting(meetingTakesPlaceInBangkok);
        assertFalse(mService.getMeetingsByRoomName("Paris").contains(meetingTakesPlaceInBangkok));
    }
    @Test
    public void filterMeetingByDate() {
        Meeting meetingTakesPlaceOnMayEleven = new Meeting(0xFFAB47BC,"Réunion Projet X","11-05-2020","10h00","Paris","leila@lamzone.com,mathieu@lamzone.com");
        mService.addMeeting(meetingTakesPlaceOnMayEleven);
        assertFalse(mService.getMeetingsByDate("10-05-2020").contains(meetingTakesPlaceOnMayEleven));
    }
}