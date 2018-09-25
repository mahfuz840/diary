package com.the_spartan.virtualdiary.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.the_spartan.virtualdiary.settings_activities.FontColorActivity;
import com.the_spartan.virtualdiary.settings_activities.FontStyleActivity;
import com.the_spartan.virtualdiary.Adapters.MyRecyclerAdapter;
import com.the_spartan.virtualdiary.objects_and_others.Note;
import com.the_spartan.virtualdiary.R;
import com.the_spartan.virtualdiary.data.NoteContract.NoteEntry;
import com.the_spartan.virtualdiary.data.NoteDbHelper;
import com.the_spartan.virtualdiary.data.NoteProvider;
import com.the_spartan.virtualdiary.settings_activities.NotificationActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ListView noteListView;
    RecyclerView noteView;
    Spinner monthSpinner;
    Spinner yearSpinner;
    ArrayList<Note> notes;
    int month, year;
    ArrayAdapter<CharSequence> yearAdapter;
    ArrayAdapter<CharSequence> monthAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private int backPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        noteListView = findViewById(R.id.notes_list_view);
        noteView = findViewById(R.id.notes_grid_view);
        navigationView = findViewById(R.id.navigation_view);
        monthSpinner = findViewById(R.id.month_spinner);
        yearSpinner = findViewById(R.id.year_spinner);

        monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        setSpinnerDefaults();

        month = monthSpinner.getSelectedItemPosition();
        year = Integer.parseInt(yearSpinner.getSelectedItem().toString());

        FloatingActionButton newFAB = findViewById(R.id.new_fab);
        newFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });

        displayDatabaseInfo(month, year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.action_search:
                final EditText searchTerm = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Enter search term: ")
                        .setMessage("This is the message")
                        .setView(searchTerm)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String searchTermString = searchTerm.getText().toString();
                                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                                intent.putExtra("term", searchTermString);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                dialog.show();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, " " + parent.getItemAtPosition(position),
//                        Toast.LENGTH_SHORT).show();

                int month = position;
                int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());

                displayDatabaseInfo(month, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,
//                        "" + parent.getItemAtPosition(position),
//                        Toast.LENGTH_SHORT)
//                        .show();

                int year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                int month = monthSpinner.getSelectedItemPosition();

                displayDatabaseInfo(month, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // set item as selected to persist highlight
                item.setChecked(true);

                switch (item.getItemId()) {
                    case R.id.nav_about:
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_font_style:
                        Intent intent1 = new Intent(MainActivity.this, FontStyleActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_font_color:
                        Intent intent2 = new Intent(MainActivity.this, FontColorActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_notification:
                        Intent notificationActivityIntent = new Intent(MainActivity.this, NotificationActivity.class);
                        startActivity(notificationActivityIntent);
                        break;
                }
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                return true;
            }
        });

        month = monthSpinner.getSelectedItemPosition();
        year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
        displayDatabaseInfo(month, year);

    }

    private void displayDatabaseInfo(int month, int year) {
        NoteDbHelper mDbHelper = new NoteDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                NoteEntry.COLUMN_ID,
                NoteEntry.COLUMN_DATE,
                NoteEntry.COLUMN_TITLE,
                NoteEntry.COLUMN_DESCRIPTION,
                NoteEntry.COLUMN_MONTH,
                NoteEntry.COLUMN_YEAR
        };

        Cursor cursor = getContentResolver().query(
                NoteProvider.CONTENT_URI,
                projection,
                NoteEntry.COLUMN_MONTH + "=? AND " + NoteEntry.COLUMN_YEAR + " =?",
                new String[]{String.valueOf(month + 1), String.valueOf(year)},
                NoteEntry.COLUMN_DATE + " ASC");
        notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getInt(cursor.getColumnIndex(NoteEntry.COLUMN_ID)),
                    cursor.getLong(cursor.getColumnIndex(NoteEntry.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(NoteEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(NoteEntry.COLUMN_DESCRIPTION)));

            notes.add(note);
        }

        cursor.close();

        noteView.setLayoutManager(new GridLayoutManager(this, 3));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, notes);

        adapter.setOnItemClickListener(new MyRecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Note note = notes.get(position);
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
//                intent.putExtra(NoteEntry.COLUMN_ID, String.valueOf(position + 1));
                intent.putExtra(NoteEntry.COLUMN_ID, note.getID());
                intent.putExtra(NoteEntry.COLUMN_DATE, note.getmDateTime());
                intent.putExtra("formatted_time", note.getDateTimeFormatted(MainActivity.this));
                intent.putExtra(NoteEntry.COLUMN_TITLE, note.getMtitle());
                intent.putExtra(NoteEntry.COLUMN_DESCRIPTION, note.getmContent());
                startActivity(intent);
            }
        });

        noteView.setAdapter(adapter);
    }

    private void setSpinnerDefaults() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        monthSpinner.setSelection(month);

        int year = calendar.get(Calendar.YEAR);

        for (int i = 0; i < yearAdapter.getCount(); i++) {
            if (yearAdapter.getItem(i).equals(String.valueOf(year))) {
                yearSpinner.setSelection(i);
                break;
            }
        }

    }

      @Override
    public void onBackPressed() {
        backPressed++;
        if (backPressed >= 2)
            super.onBackPressed();
        else
            Toast.makeText(this, "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
    }
}
