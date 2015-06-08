package com.salmon.sports.spots;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class HomeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private HomeModel model;
    private AddActivity addActivity = new AddActivity();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final String SHARED = "SHAREDPREF";
    private final String SAVEDPREF = "SAVEDITEMS";

    // UI components
    private Button addBtn;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    // specify an adapter (see also next example)


    /**
     * Sets hash in model based on saved json array.
     */
    public int getJsonHash() {
        SharedPreferences sharedPref = HomeActivity.this.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        Set<String> jsonStringSet = sharedPref.getStringSet(SAVEDPREF, new HashSet<String>());
        return jsonStringSet.hashCode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("ON CREATE!!");

        model = new HomeModel();

        // sets hash in model based on saved json array
        model.jsonHash = getJsonHash();

        setContentView(R.layout.layout_home);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(true);

        // setting the layoutmanager if null
        if(mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        addActivity.setModel(model);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Fetching UI components
        initUI();

        // Loads items since there wont be a difference in
        // the hashes due to first run..
        Thread thread = new Thread(new LoaderRunner());
        thread.start();
    }

    // Initializing UI components
    public void initUI() {
        addBtn = (Button) findViewById(R.id.add_button);
        addBtn.setOutlineProvider(new ViewOutlineProvider() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        });

        addBtn.setClipToOutline(true);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens AddActivity with this as parent
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        addBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_workouts);
                break;
            case 2:
                mTitle = getString(R.string.title_settings);
                break;
            case 3:
                mTitle = getString(R.string.title_help);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onPause() {
        super.onPause();

        Thread thread = new Thread(new SaveRunner());
        thread.run();

    }

    // Saving items
    public void saveItems(Set<String> jsonArray) {
        // System.out.println("saving array of size: " + jsonArray.size());

        SharedPreferences sharedPref = HomeActivity.this.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // convert sportItem into gson object
        editor.putStringSet(SAVEDPREF, jsonArray);
        editor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();

        // Loads previously saved items into items in model
        loadItems();

    }

    /**
     * Sets new adapter with fetched items, applies the adapter to
     * the recyclerview and notifies dataset changed.
     * Runs on UI thread.
     */
    public void refreshRecyclerView() {
        HomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new MyAdapter(model.items);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }
        });
    }


    // TODO do this on a new thread
    public void loadItems() {

        // checks if there's an update in the jsonarray based on hash
        if(model.jsonHash != getJsonHash()) {
            Thread thread = new Thread(new LoaderRunner());
            thread.start();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /*public PlaceholderFragment() {
        }
*/
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    /**
     * Runnable for saving items from model into SharedPreferences.
     */
    private class SaveRunner implements Runnable {

        @Override
        public void run() {
            Set<String> jsonArray = new HashSet<>();

            if(model.items != null) {
                for (SportItem item : model.items) {
                    jsonArray.add(JsonUtil.toJSon(item));
                    //System.out.println("ADDED: " + JsonUtil.toJSon(item));
                }

                // Saving the items from model to device
                saveItems(jsonArray);
            }

            // Setting new hash for json
            model.jsonHash = jsonArray.hashCode();
        }
    }


    /**
     * Runnable for fetching saved items.
     * Loads JSON object from SharedPreferences,
     * parses the items to SportItems.
     */
    private class LoaderRunner implements Runnable {

        @Override
        public void run() {
            SharedPreferences sharedPref = HomeActivity.this.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
            Set<String> jsonStringSet = sharedPref.getStringSet(SAVEDPREF, new HashSet<String>());
            /*System.out.println("Got set of size: " + jsonStringSet.size());
            System.out.println("GOT: " + sharedPref.getInt("Act", 0));
*/

                model.items = new ArrayList<>();

                for(String itemString : jsonStringSet) {

                    try {
                        // Parsing JSON Object
                        JSONObject jObj = new JSONObject(itemString);

                        // checks if the jsonobject has been updated based on hash

                        // TODO update
                        double duration = jObj.getDouble("duration");
                        String date = jObj.getString("date");
                        String type = jObj.getString("type");

                        SportItem loadedItem = null;

                        // Recreating item from json
                        if(type.equals("walking")) {
                            loadedItem = new WalkingItem(date, duration);
                        } else if (type.equals("running")) {
                            loadedItem = new RunningItem(date, duration);
                        } else if (type.equals("cycling")) {
                            loadedItem = new CyclingItem(date, duration);
                        } else {
                            loadedItem = new WalkingItem(date, duration);
                        }

                        if(loadedItem != null) {
                            model.items.add(loadedItem);
                        }

                        refreshRecyclerView();



                    } catch (JSONException exp) {
                        System.out.println("Exception when parsing JSON object. ");
                        System.out.println(exp.getStackTrace());
                    }


                }


        }
    }
}
