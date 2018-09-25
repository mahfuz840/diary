package com.the_spartan.virtualdiary.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.the_spartan.virtualdiary.objects_and_others.Note;
import com.the_spartan.virtualdiary.R;
import com.the_spartan.virtualdiary.data.NoteContract.NoteEntry;
import com.the_spartan.virtualdiary.data.NoteDbHelper;
import com.the_spartan.virtualdiary.data.NoteProvider;

import java.util.Calendar;

public class CreateNoteActivity extends AppCompatActivity {

    EditText EtTitle;
    EditText EtContent;

    Note mLoadedNote;
    int mDay, mMonth, mYear;
    int id;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCalendar = Calendar.getInstance();
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mMonth = mCalendar.get(Calendar.MONTH);
        mYear = mCalendar.get(Calendar.YEAR);

        EtTitle = findViewById(R.id.title_edit_text);
        EtContent = findViewById(R.id.content_edit_text);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontPath = preferences.getString(getString(R.string.font), null);
        int color = preferences.getInt("color", 0);
        String fontSize = preferences.getString(getString(R.string.font_size), null);

        if (fontPath != null && !fontPath.equals("null")) {
            Typeface myFont = Typeface.createFromAsset(getAssets(), fontPath);
            EtContent.setTypeface(myFont);
            EtTitle.setTypeface(myFont);
        }

        if (color != 0){
            EtContent.setTextColor(color);
            EtTitle.setTextColor(color);
        }

        if (fontSize != null){
            EtContent.setTextSize(Float.parseFloat(fontSize));
            EtTitle.setTextSize(Float.parseFloat(fontSize));
        }

        final TextView dateView = findViewById(R.id.date);
        String monthString;
        switch (mMonth+1){
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "Unknown";
        }
        dateView.setText(mDay + " " + monthString + " " + mYear);

        if (getIntent().getExtras() != null) {
            id = getIntent().getIntExtra(NoteEntry.COLUMN_ID, 10);
            Log.v("ID"," " + id);
            String date = getIntent().getStringExtra("formatted_time");
            String[] dates = date.split("/");
            switch (Integer.parseInt(dates[1])){
                    case 1:
                        monthString = "Jan";
                        break;
                    case 2:
                        monthString = "Feb";
                        break;
                    case 3:
                        monthString = "Mar";
                        break;
                    case 4:
                        monthString = "Apr";
                        break;
                    case 5:
                        monthString = "May";
                        break;
                    case 6:
                        monthString = "Jun";
                        break;
                    case 7:
                        monthString = "Jul";
                        break;
                    case 8:
                        monthString = "Aug";
                        break;
                    case 9:
                        monthString = "Sep";
                        break;
                    case 10:
                        monthString = "Oct";
                        break;
                    case 11:
                        monthString = "Nov";
                        break;
                    case 12:
                        monthString = "Dec";
                        break;
                    default:
                        monthString = "Unknown";
            }
            dateView.setText(dates[0] + " " + monthString + " " + dates[2]);
            String title = getIntent().getStringExtra(NoteEntry.COLUMN_TITLE);
            String content = getIntent().getStringExtra(NoteEntry.COLUMN_DESCRIPTION);

            EtTitle.setText(title);
            EtContent.setText(content);
        }


        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CreateNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        month += 1;
                        String monthString;
                        switch (month + 1){
                            case 1:
                                monthString = "Jan";
                                break;
                            case 2:
                                monthString = "Feb";
                                break;
                            case 3:
                                monthString = "Mar";
                                break;
                            case 4:
                                monthString = "Apr";
                                break;
                            case 5:
                                monthString = "May";
                                break;
                            case 6:
                                monthString = "Jun";
                                break;
                            case 7:
                                monthString = "Jul";
                                break;
                            case 8:
                                monthString = "Aug";
                                break;
                            case 9:
                                monthString = "Sep";
                                break;
                            case 10:
                                monthString = "Oct";
                                break;
                            case 11:
                                monthString = "Nov";
                                break;
                            case 12:
                                monthString = "Dec";
                                break;
                            default:
                                monthString = "Unknown";
                        }
                       dateView.setText(dayOfMonth + " " + monthString + " " + year);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.YEAR, year);
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_note_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.create_note_activity_action_save:
                if (getIntent().getExtras() == null) {
                    saveNote();
                } else {
                    updateNote();
                }
                finish();
                break;

            case R.id.note_delete:
                if (getIntent().getExtras() != null){
                    showDeleteDialog();
                }
                break;
        }

        return true;
    }

    private void saveNote() {

        Long date = mCalendar.getTimeInMillis();
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int year = mCalendar.get(Calendar.YEAR);

        String title = EtTitle.getText().toString();
        String content = EtContent.getText().toString();

        NoteDbHelper helper = new NoteDbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NoteEntry.COLUMN_DATE, date);
        values.put(NoteEntry.COLUMN_TITLE, title);
        values.put(NoteEntry.COLUMN_DESCRIPTION, content);
        values.put(NoteEntry.COLUMN_MONTH, month);
        values.put(NoteEntry.COLUMN_YEAR, year);

        getContentResolver().insert(NoteProvider.CONTENT_URI, values);
    }

    private void updateNote(){
        Long date = mCalendar.getTimeInMillis();
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int year = mCalendar.get(Calendar.YEAR);

        String title = EtTitle.getText().toString();
        String content = EtContent.getText().toString();

        ContentValues values = new ContentValues();

        values.put(NoteEntry.COLUMN_DATE, getIntent().getLongExtra(NoteEntry.COLUMN_DATE, 0));
        values.put(NoteEntry.COLUMN_TITLE, title);
        values.put(NoteEntry.COLUMN_DESCRIPTION, content);
        values.put(NoteEntry.COLUMN_MONTH, month);
        values.put(NoteEntry.COLUMN_YEAR, year);

//        int IntegerID = Integer.parseInt(id);
        getContentResolver().update(Uri.withAppendedPath(NoteProvider.CONTENT_URI, String.valueOf(id)),
                values,
                null,
                null);
    }

    private void deleteNote(){
        getContentResolver().delete(Uri.withAppendedPath(NoteProvider.CONTENT_URI, String.valueOf(id)),
                null,
                null);
    }

    private void showDeleteDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.delete_dialog_message)
                .setTitle(R.string.delete_dialog_title);

        builder.setPositiveButton(R.string.delete_dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteNote();
                finish();
            }
        });

        builder.setNegativeButton(R.string.delete_dialog_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


