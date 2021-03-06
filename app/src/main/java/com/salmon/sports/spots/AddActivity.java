package com.salmon.sports.spots;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Activity used for addding an item (running/walking/..)
 * Created by Mikael Malmqvist on 2015-05-24.
 */
public class AddActivity extends Activity {
    HomeModel model;
    private String[] arraySpinner;


    private Spinner itemSpinner;
    private ImageView itemIcon;
    private EditText durationMinText;
    private EditText durationHourText;
    private EditText distanceText;
    private EditText dateText;
    private Button submitButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        distanceText = (EditText) findViewById(R.id.distanceText);
        submitButton = (Button) findViewById(R.id.submitButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        dateText = (EditText) findViewById(R.id.dateText);
        dateText.setInputType(InputType.TYPE_NULL); // disables ordinary input
        dateText.requestFocus(); // date needs focus to bring up date picker

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDateDialog(v);
            }
        });
        /*ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);

        itemSpinner.setAdapter(spinnerAdapter);*/

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.items.add(new RunningItem(new Date(), 26.14));
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

    public void showStartDateDialog(View v){
        DialogFragment dialogFragment = new StartDatePicker();
        dialogFragment.show(getFragmentManager(), "start_date_picker");
    }

    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);

    class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, this, startYear, startMonth, startDay);
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;

            //TODO set date in edit text
            System.out.println("DATE: " + startDay + startMonth + startDay);
            dateText.setText(startDay + "/" + startMonth + " - " + startYear);


        }
    }
}
