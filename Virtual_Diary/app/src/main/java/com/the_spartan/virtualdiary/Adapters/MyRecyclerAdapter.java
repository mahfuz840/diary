package com.the_spartan.virtualdiary.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.the_spartan.virtualdiary.objects_and_others.Note;
import com.the_spartan.virtualdiary.R;

import java.util.ArrayList;

/**
 * Created by the_spartan on 3/11/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    String mDate;
    Context mContext;

    private static ClickListener clickListener;
    private ArrayList<Note> mNotes;
    public MyRecyclerAdapter(Context context, ArrayList<Note> notes){
        mContext = context;
        mNotes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_recycler_main_activity, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String dateWithTime = mNotes.get(position).getDateTimeFormatted(mContext);
        String dateOnly[] = dateWithTime.split("/");

        holder.dateView.setText(dateOnly[0]);
        String monthString;
        int month = mNotes.get(position).getmMonth();
        switch (month){
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
        holder.monthView.setText(monthString);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = 200;
        params.topMargin = 10;
        params.bottomMargin = 10;

        holder.itemView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        if (mNotes == null)
            return 0;
        else
            return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView dateView;
        TextView monthView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            dateView = itemView.findViewById(R.id.note_date);
            monthView = itemView.findViewById(R.id.note_month);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public void setOnItemClickListener(ClickListener clickListener){
        MyRecyclerAdapter.clickListener = clickListener;
    }


    public interface ClickListener{
        void onItemClick(int position, View v);
    }
}
