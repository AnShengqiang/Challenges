package com.charger.android.dormtoryevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by a1877 on 2016/11/7.
 */

public class EventFragment extends Fragment {

    private static final String ARG_EVENT_ID = "event_id";
    private static final String EXTRA_EVENT_ID =
            "com.charger.android.dormtoryevents.event_id";

    private Event mEvent;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSovedCheckBox;

    public static EventFragment newInstance(UUID eventId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventId);

        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);

        return fragment;
    }




    public void setResultEventId() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EVENT_ID,  getActivity().getIntent()
                .getSerializableExtra(EXTRA_EVENT_ID));
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

    public static UUID getEventId(Intent result){
        return (UUID)result.getSerializableExtra(EXTRA_EVENT_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID eventId = (UUID) getArguments().getSerializable(ARG_EVENT_ID);

        mEvent = EventLab.get(getActivity()).getEvent(eventId);

        setResultEventId();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        mTitleField = (EditText) v.findViewById(R.id.event_title);
        mTitleField.setText(mEvent.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //这里故意留白
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //这里故意留白
            }
        });

        mDateButton = (Button)v.findViewById(R.id.event_date);
        mDateButton.setText(mEvent.getDate().toString());
        mDateButton.setEnabled(false);

        mSovedCheckBox = (CheckBox)v.findViewById(R.id.event_solved);
        mSovedCheckBox.setChecked(mEvent.isSolved());
        mSovedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEvent.setSolved(isChecked);
            }
        });

        return v;
    }

}
