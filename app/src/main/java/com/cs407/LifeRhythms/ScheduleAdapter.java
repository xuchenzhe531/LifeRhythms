package com.cs407.LifeRhythms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<ScheduleItem> {

    public ScheduleAdapter(Context context, List<ScheduleItem> scheduleItems) {
        super(context, 0, scheduleItems);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_item, parent, false);
        }

        ScheduleItem scheduleItem = getItem(position);
        if (scheduleItem == null) {
            return convertView;
        }
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvStartTime = convertView.findViewById(R.id.tvStartTime);
        TextView tvEndTime = convertView.findViewById(R.id.tvEndTime);
        TextView tvItem = convertView.findViewById(R.id.tvItem);
        tvDate.setText(scheduleItem.getDate());
        tvStartTime.setText(scheduleItem.getStartTime());
        tvEndTime.setText(scheduleItem.getEndTime());
        tvItem.setText(scheduleItem.getItem());

        return convertView;
    }
}
