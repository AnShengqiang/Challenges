package com.charger.android.dormtoryevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by a1877 on 2016/11/19.
 */

public class EventListFragment extends Fragment{

    private static final int REQUEST_CODE_EVENT_ID = 1;

    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;
    private UUID eventId;
    private int itemPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        mEventRecyclerView = (RecyclerView) view
                .findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode!= Activity.RESULT_OK){
            Log.d(TAG, "result不不不不不ok");
            return;
        }
        if (requestCode==REQUEST_CODE_EVENT_ID){
            if (data==null){
                Log.d(TAG, "data是空的 ");
                return;
            }
            Log.d(TAG, "成功获取有内容的data");
            eventId = EventFragment.getEventId(data);
        }
    }

    private void updateUI(){
        EventLab eventLab = EventLab.get(getActivity());
        List<Event> events = eventLab.getEvents();

        if (mAdapter == null){
            mAdapter = new EventAdapter(events);
            mEventRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyItemChanged(itemPosition);//需要获取等待刷新的行的编号
        }

    }

    private class EventHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Event mEvent;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public EventHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_event_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_event_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_event_solved_check_box);
        }

        @Override
        public void onClick(View v){
            Intent intent = EventActivity.newIntent(getActivity(), mEvent.getId());
            itemPosition = mEventRecyclerView.getChildAdapterPosition(v);//......
            startActivityForResult(intent, REQUEST_CODE_EVENT_ID);
        }

        public void bindEvent(Event event){
            mEvent = event;
            mTitleTextView.setText(mEvent.getTitle());
            mDateTextView.setText(mEvent.getDate().toString());
            mSolvedCheckBox.setChecked(mEvent.isSolved());
        }

    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{

        private List<Event> mEvents;

        public EventAdapter(List<Event> events){
            mEvents = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_event, parent, false);
            return new EventHolder(view);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position){
            Event event = mEvents.get(position);
            holder.bindEvent(event);
        }

        @Override
        public int getItemCount(){
            return mEvents.size();
        }

    }

}
