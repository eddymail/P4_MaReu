package com.lousssouarn.edouard.mareu.views;

import android.content.res.ColorStateList;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.display(mMeetings.get(position));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
                mMeetings.remove(position);
                notifyItemRangeChanged(position, mMeetings.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }


    //ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
            public AppCompatImageView mRoomColor;
            public TextView mParticipants;
            public ImageButton mDeleteButton;
            public TextView mInfo;

     //Constructor
        public ViewHolder(View view) {
            super(view);
            mRoomColor = view.findViewById(R.id.item_room_color);
            mInfo = view.findViewById(R.id.info);
            mParticipants = view.findViewById(R.id.item_participants);
            mDeleteButton = view.findViewById(R.id.item_delete_button);
        }

        //item setText and color
        void display(Meeting meeting){
                mRoomColor.setImageTintList(ColorStateList.valueOf(meeting.getColor()));
                mInfo.setText(meeting.getSubject() + " - " + meeting.getTime() + " - " + meeting.getRoomName());
                mParticipants.setText(meeting.getParticipants());
        }

    }

}
