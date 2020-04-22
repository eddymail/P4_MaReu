package com.lousssouarn.edouard.mareu.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.lousssouarn.edouard.mareu.R;
import com.lousssouarn.edouard.mareu.model.Meeting;

import java.util.List;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    //For Data
    private final List<Meeting> mMeetings;
    //Constructor
    public MeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }


    @Override
    @NonNull
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
    public static class ViewHolder extends RecyclerView.ViewHolder{
            public AppCompatImageView mRoomColor;
            public TextView mSubject;
            public TextView mTime;
            public TextView mRoomName;
            public TextView mParticipants;
            public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            mRoomColor = view.findViewById(R.id.item_room_color);
            mSubject = view.findViewById(R.id.item_subject);
            mTime = view.findViewById(R.id.item_time);
            mRoomName = view.findViewById(R.id.item_room);
            mParticipants = view.findViewById(R.id.item_participants);
            mDeleteButton = view.findViewById(R.id.item_delete_button);
        }
        void display(Meeting meeting){
                mRoomColor.setBackgroundColor(meeting.getColor());
                mSubject.setText(meeting.getSubject());
                mTime.setText(meeting.getTime());
                mRoomName.setText(meeting.getRoomName());
                mParticipants.setText(meeting.getParticipants());
        }

    }

}
