package com.lousssouarn.edouard.mareu.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    //For Data
    private final List<Meeting> mMeetings;
    //Constructor
    public MeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.display(mMeetings.get(position));
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.item_room_color)
            public ImageView mRoomColor;
            @BindView(R.id.item_subject)
            public TextView mSubject;
            @BindView(R.id.item_time)
            public  TextView mTime;
            @BindView(R.id.item_room)
            public TextView mRoomName;
            @BindView(R.id.item_participants)
            public TextView mParticipants;
            @BindView(R.id.item_delete_button)
            public ImageButton mDeleteButton;


            public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        void display(Meeting meeting){
                mRoomColor.setColorFilter(meeting.getColor());
                mSubject.setText(meeting.getSubject());
                mTime.setText(meeting.getTime());
                mRoomName.setText(meeting.getRoomName());
                mParticipants.setText(meeting.getParticipants());

        }

    }

}
