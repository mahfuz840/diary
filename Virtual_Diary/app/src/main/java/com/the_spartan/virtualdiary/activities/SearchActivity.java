package com.the_spartan.virtualdiary.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.the_spartan.virtualdiary.objects_and_others.Note;
import com.the_spartan.virtualdiary.Adapters.NoteAdapter;
import com.the_spartan.virtualdiary.R;
import com.the_spartan.virtualdiary.data.NoteContract;
import com.the_spartan.virtualdiary.data.NoteDbHelper;
import com.the_spartan.virtualdiary.data.NoteProvider;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Note> notes;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.search_list_view);

        search();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = notes.get(position);
                Intent intent = new Intent(SearchActivity.this, CreateNoteActivity.class);
                intent.putExtra(NoteContract.NoteEntry.COLUMN_ID, note.getID());
                intent.putExtra(NoteContract.NoteEntry.COLUMN_DATE, note.getmDateTime());
                intent.putExtra("formatted_time", note.getDateTimeFormatted(SearchActivity.this));
                intent.putExtra(NoteContract.NoteEntry.COLUMN_TITLE, note.getMtitle());
                intent.putExtra(NoteContract.NoteEntry.COLUMN_DESCRIPTION, note.getmContent());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }


    private void search(){

        String target = getIntent().getStringExtra("term");
        String removedSpaces = removeUnnecessarySpaces(target);

        String searchTerm = "%" + removedSpaces + "%";
        System.out.println(searchTerm);
        NoteDbHelper mDbHelper = new NoteDbHelper(this);

        String[] projection = {
                NoteContract.NoteEntry.COLUMN_ID,
                NoteContract.NoteEntry.COLUMN_DATE,
                NoteContract.NoteEntry.COLUMN_TITLE,
                NoteContract.NoteEntry.COLUMN_DESCRIPTION,
                NoteContract.NoteEntry.COLUMN_MONTH,
                NoteContract.NoteEntry.COLUMN_YEAR
        };

        Cursor cursor = getContentResolver().query(
                NoteProvider.CONTENT_URI,
                projection,
                NoteContract.NoteEntry.COLUMN_DESCRIPTION + " LIKE ?",
                new String[]{searchTerm},
                NoteContract.NoteEntry.COLUMN_DATE + " ASC");
        Log.v("CURSOR", cursor.getCount() + " ");
        notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getInt(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_ID)),
                    cursor.getLong(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_DESCRIPTION)));

            notes.add(note);
        }

        Log.v("SIZE", " " + notes.size());

        cursor.close();

        NoteAdapter adapter = new NoteAdapter(SearchActivity.this, notes);
        listView.setAdapter(adapter);
    }

    private String removeUnnecessarySpaces(String target){

        StringBuilder builder = new StringBuilder();
        boolean spaceFound = true;
        for (int i = 0; i < target.length(); i++){
            if (target.charAt(i) == ' ' && !spaceFound){
                builder.append(target.charAt(i));
                spaceFound = true;
            } else if (target.charAt(i) != ' '){
                builder.append(target.charAt(i));
                spaceFound = false;
            }
        }
        int builderSize = builder.length();
        if (builder.charAt(builderSize - 1) == ' '){
            builder.deleteCharAt(builderSize - 1);
        }
        String resultingString = builder.toString();

        return resultingString;
    }
}
