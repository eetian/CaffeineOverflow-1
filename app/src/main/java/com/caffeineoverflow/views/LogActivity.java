package com.caffeineoverflow.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;
import com.caffeineoverflow.utils.CalendarEvent;
import com.caffeineoverflow.utils.CalendarEventListAdapter;
import com.caffeineoverflow.utils.OnCalendarEventClickListener;
import com.caffeineoverflow.utils.OnItemClickListener;
import com.caffeineoverflow.utils.Result;
import com.caffeineoverflow.utils.ResultListAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarAnimationListener;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogActivity extends AppCompatActivity  {

    String TAG = "DEBUG-CALENDAR";

    Button btnAddEvent;

    Date currDateClicked = new Date();

    private RecyclerView recyclerView;
    List<CalendarEvent> calendarEvents = new ArrayList<>();
    CalendarEventListAdapter calendarEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // Generate calendar UI
        generateCalendar();

        // Handle recyclerView
        recyclerView = findViewById(R.id.rvCalendarEventList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendarEventAdapter = new CalendarEventListAdapter(calendarEvents, new OnCalendarEventClickListener() {
            @Override
            public void onCalendarEventClick(CalendarEvent calendarEvent) {
                Log.d(TAG, "Calendar event: " + calendarEvent.getEventName());
                // TODO FIXME
                // Show a dialog which will either directs us to the recipe search or amazon
                chooseActionDialog(calendarEvent);

            }
        });
        recyclerView.setAdapter(calendarEventAdapter);

        // Add event listener for the add event button
        btnAddEvent = (Button) findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked date is: " + currDateClicked.toString());
                addEventDialog();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    void addEventToCalendar(String eventName, String eventCount) {
        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.calendarView);
        // We can change color here!!!
        Event event = new Event(Color.GREEN, currDateClicked.getTime(),
                new CalendarEvent(eventName, Integer.valueOf(eventCount)));
        compactCalendarView.addEvent(event);
    }

    private void updateEventListRecycler(List<Event> events) {
        // Handle recyclerView
        calendarEvents.clear();
        for (Event thisEvent : events) {
            calendarEvents.add((CalendarEvent)thisEvent.getData());
        }
        // Notify the recyclerViewAdapter about the data update
        calendarEventAdapter.notifyDataSetChanged();
    }


    private void generateCalendar() {
        CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.calendarView);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                currDateClicked = dateClicked;
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                // Update recycler view
                updateEventListRecycler(events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
    }

    private void addEventDialog(){
        CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.calendarView);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View eventDialogView = inflater.inflate(R.layout.dialog_add_event, null);
        EditText eventName = (EditText)eventDialogView.findViewById(R.id.diaglogEventName);
        EditText eventCount = (EditText)eventDialogView.findViewById(R.id.dialogEventCount);
        builder.setView(eventDialogView)
            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    String eventNameStr = eventName.getText().toString();
                    String eventCountStr = eventCount.getText().toString();
                    Log.d(TAG, "Event Name: " + eventNameStr + ", Event Count: " + eventCountStr);
                    // Add event to calendar
                    addEventToCalendar(eventNameStr, eventCountStr);
                    // Update recycler view
                    updateEventListRecycler(compactCalendarView.getEvents(currDateClicked));
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            })
            .show();
    }

    private void chooseActionDialog(CalendarEvent calendarEvent){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View eventDialogView = inflater.inflate(R.layout.dialog_action_for_event, null);
        builder.setView(eventDialogView)
                .setPositiveButton("get recipes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                        intent.putExtra("eventName", calendarEvent.getEventName());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("go to amazon", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();

    }


}
