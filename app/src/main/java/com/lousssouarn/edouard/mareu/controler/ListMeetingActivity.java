package com.lousssouarn.edouard.mareu.controler;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.di.DI;
import com.lousssouarn.edouard.mareu.dialog.FilterDialogFragment;
import com.lousssouarn.edouard.mareu.model.Meeting;
import com.lousssouarn.edouard.mareu.service.MeetingApiService;
import com.lousssouarn.edouard.mareu.views.MeetingRecyclerViewAdapter;

import java.util.List;

import static com.lousssouarn.edouard.mareu.R.id.menu_filter;


public class ListMeetingActivity extends AppCompatActivity {

   private MeetingApiService mApiService;
   private List<Meeting> mMeetings;
   private RecyclerView mRecyclerView;
   private MeetingRecyclerViewAdapter mAdapter;

   public ImageButton mAddMeetingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        this.configureToolbar();

        mApiService = DI.getMeetingApiService();

        mRecyclerView = findViewById(R.id.meeting_list);

        mAdapter = new MeetingRecyclerViewAdapter(mMeetings);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        mAddMeetingButton = findViewById(R.id.add_item);
        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent addMeetingIntent = new Intent(ListMeetingActivity.this, AddMeeting.class);
            startActivity(addMeetingIntent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_list_meeting, menu);
        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Handle actions on menu items
        if (item.getItemId() == menu_filter) {
            FilterDialogFragment dialogFragment = new FilterDialogFragment();
            dialogFragment.show(getSupportFragmentManager(),"Dialog");
            return true;
        }else{
                return super.onOptionsItemSelected(item);
        }
    }

    //Init the list of meeting
    private void initList(){
        mMeetings = mApiService.getMeetings();
        mRecyclerView.setAdapter(new MeetingRecyclerViewAdapter(mMeetings));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }
}
