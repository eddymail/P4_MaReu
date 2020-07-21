package com.lousssouarn.edouard.mareu;

import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.DummyMeetingGenerator;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
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
        List<Meeting> meetings = mService.getMeetings();
        int count = meetings.size();
        mService.deleteMeeting(0);
        assertEquals(count-1,mService.getMeetings().size());
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meetingToAdd = new Meeting(0xFFEF5350,"Test meeting", "10-05-2020", "09h00","Bangkok","eddy@lamzon.com");
        mService.addMeeting(meetingToAdd);
        assertTrue(mService.getMeetings().contains(meetingToAdd));
    }

    @Test
    public void addMeetingNotFullCompleted() {
        Meeting meetingToAdd = new Meeting(0xFFEF5350,"Test meeting", "10-05-2020", "","Choisir une salle","");
        try{
        mService.addMeeting(meetingToAdd);
            Assert.fail("Exception should occur");
        }catch (IllegalArgumentException e) {}
    }

    @Test
    public void filterMeetingByRoomWithSuccess() {
        Meeting meetingTakesPlaceInBangkok = new Meeting(0xFFEF5350,"Test meeting", "10-05-2020", "09h00",
                "Bangkok","eddy@lamzon.com");
        mService.addMeeting(meetingTakesPlaceInBangkok);
        assertFalse(mService.getMeetingsByRoomName("Paris").contains(meetingTakesPlaceInBangkok));
        assertTrue(mService.getMeetingsByRoomName("Bangkok").contains(meetingTakesPlaceInBangkok));
    }

    @Test
    public void filterMeetingByDateWithSuccess() {
        Meeting meetingTakesPlaceOnMayEleven = new Meeting(0xFFAB47BC,"Réunion Projet X","11-05-2020","10h00","Paris","leila@lamzone.com,mathieu@lamzone.com");
        mService.addMeeting(meetingTakesPlaceOnMayEleven);
        assertFalse(mService.getMeetingsByDate("10-05-2020").contains(meetingTakesPlaceOnMayEleven));
        assertTrue(mService.getMeetingsByDate("11-05-2020").contains(meetingTakesPlaceOnMayEleven));
    }
}