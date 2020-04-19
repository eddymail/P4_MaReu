package com.lousssouarn.edouard.mareu.controler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;
import com.lousssouarn.edouard.mareu.views.MeetingRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;

public class ListMeetingActivity extends AppCompatActivity {

   private MeetingApiService mApiService;
   private List<Meeting> mMeetings;
   private RecyclerView mRecyclerView;
   private MeetingRecyclerViewAdapter mAdapter;

    //UI Components
    @BindView(R.id.toolbar)
    Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        mRecyclerView = findViewById(R.id.meeting_list);

        mAdapter = new MeetingRecyclerViewAdapter(mMeetings);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        initList();
    }

    //Init the list of meeting
    private void initList(){
        mMeetings = mApiService.getMeetings();
        mRecyclerView.setAdapter(new MeetingRecyclerViewAdapter(mMeetings));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_list_meeting,menu);
        return true;
    }
}
