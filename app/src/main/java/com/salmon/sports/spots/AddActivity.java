package com.salmon.sports.spots;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Activity used for addding an item (running/walking/..)
 * Created by Mikael Malmqvist on 2015-05-24.
 */
public class AddActivity extends Activity {
    HomeModel model;
    private String[] arraySpinner;

    private final String SAVEDPREF = "SAVEDITEMS";
    private final String SHARED = "SHAREDPREF";

    private Spinner itemSpinner;
    private ImageView itemIcon;
    private EditText durationMinText;
    private EditText durationHourText;
    //private EditText distanceText;
    private EditText dateText;
    private Button submitButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        this.arraySpinner = new String[] {
                "Walking", "Running", "Swimming", "Cycling"
        };

        setContentView(R.layout.layout_add);

        // Fetching UI
        initUI();
    }

    public void setModel(HomeModel model) {
        this.model = model;
    }

    // Initializing UI components
    public void initUI() {
        itemSpinner = (Spinner) findViewById(R.id.itemSpinner);
        itemIcon = (ImageView) findViewById(R.id.itemIcon);
        durationHourText = (EditText) findViewById(R.id.duationHourText);
        durationMinText = (EditText) findViewById(R.id.duationMinText);
        submitButton = (Button) findViewById(R.id.submitButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        dateText = (EditText) findViewById(R.id.dateText);
        dateText.setInputType(InputType.TYPE_NULL); // disables ordinary input
        dateText.requestFocus(); // date needs focus to bring up date picker

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // set icon based on workout type
                if(((TextView) view).getText().equals("Walking")) {
                    itemIcon.setImageResource(WalkingItem.INVICON);

                } else if(((TextView) view).getText().equals("Running")) {
                    itemIcon.setImageResource(RunningItem.INVICON);

                } else if(((TextView) view).getText().equals("Swimming")) {

                } else {
                    // default
                }

                itemIcon.refreshDrawableState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDateDialog(v);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double duration = Integer.parseInt(durationHourText.getText().toString()) + Double.parseDouble(durationMinText.getText().toString())/60;

                System.out.println("ITEM WS: " + itemSpinner.getSelectedItem().toString());

                SportItem sportItem;


                // type was walking
                if(itemSpinner.getSelectedItem().toString().equals("Walking")) {
                    sportItem = new WalkingItem(dateText.getText().toString(), duration);

                } else if(itemSpinner.getSelectedItem().toString().equals("Running")) {
                    sportItem = new RunningItem(dateText.getText().toString(), duration);

                } else {
                    sportItem = new WalkingItem(dateText.getText().toString(), 0);
                }


                saveItem(sportItem);

                model.items.add(sportItem);
                AddActivity.this.finish();
            }
        });

        // Cancel clicked - finish this activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.this.finish();
            }
        });
    }



    public void saveItem(SportItem item) {

        SharedPreferences sharedPref = AddActivity.this.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        Set<String> mySet = new HashSet<>();

        mySet.addAll(sharedPref.getStringSet(SAVEDPREF, new HashSet<String>()));



        System.out.println("SIZE OF mySET: " + mySet.size());
        mySet.add(JsonUtil.toJSon(item));

        SharedPreferences.Editor editor = sharedPref.edit();

        // Overwriting the string set
        editor.putStringSet(SAVEDPREF, mySet);
        // editor.putStringSet(SAVEDPREF, new HashSet<String>());
        //editor.putInt("Act", 99);
        editor.commit();

    }

    public void showStartDateDialog(View v){
        DialogFragment dialogFragment = new StartDatePicker();
        dialogFragment.show(getFragmentManager(), "start_date_picker");
    }

    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);

    // Date picker class
    private class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this,
                    this, startYear, startMonth, startDay);

            return dialog;

        }

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            // Do something with the date chosen by the user
            startYear = year;
            String[] month = DateFormatSymbols.getInstance().getMonths();
            startDay = dayOfMonth;

            //TODO set date in edit text
            dateText.setText(startDay + " " + month[monthOfYear] + " " + startYear);


        }
    }
}
