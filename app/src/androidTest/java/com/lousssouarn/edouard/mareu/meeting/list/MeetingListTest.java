package com.lousssouarn.edouard.mareu.meeting.list;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.controler.ListMeetingActivity;
import com.lousssouarn.edouard.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.lousssouarn.edouard.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

/**
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    //This is fixed
    private static  int ITEMS_COUNT = 10;

    private ListMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule = new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp(){
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        onView(allOf(ViewMatchers.withId(R.id.meeting_list),isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void deleteMeetingAction_shouldRemoveItem() {
        //Given : We ensure there are 10 elements in the recyclerview
        onView(withId(R.id.meeting_list)).check(withItemCount(ITEMS_COUNT));
        //When : We perform a click on delete icon
        onView((withId(R.id.meeting_list))).perform(RecyclerViewActions.actionOnItemAtPosition(1,new DeleteViewAction()));
        //Then : the number of element is 9
        onView((withId(R.id.meeting_list))).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void addNewMeeting() {
        //Given : we ensure there are 10 elements in the recyclerview
        onView(withId(R.id.meeting_list)).check(withItemCount(ITEMS_COUNT));
        //When : we click on "add item" floatingActionButton, create a new meeting call "Test meeting", and click on "new meeting button"
        onView(withId(R.id.add_item)).perform(click());
        onView(withId(R.id.et_name)).perform(replaceText("Test meeting"));
        onView(withId(R.id.bt_new_meeting)).perform(click());
        //Then : we ensure there is one more element in the recyclerview
        onView(withId(R.id.meeting_list)).check(withItemCount(ITEMS_COUNT+1));
    }

    @Test
    public void filterMeetingByRoomName(){
        onView(withId(R.id.menu_filter)).perform(click());
        final String item = "Bangkok";
        onData(allOf(is(instanceOf(String.class)), is(item))).perform();
        onView(withId(R.id.bt_room_filter)).perform(click());
        onView(withId(R.id.meeting_list)).check(matches(hasChildCount(1)));
    }
    @Test
    public void filterMeetingByDate(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withId(R.id.et_filter_date)).perform(replaceText("11-05-2020"));
        onView(withId(R.id.bt_date_filter)).perform(click());
        onView(withId(R.id.meeting_list)).check(matches(hasChildCount(3)));
    }

}
