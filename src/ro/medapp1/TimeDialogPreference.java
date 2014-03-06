package ro.medapp1;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.prefs.Preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;



public class TimeDialogPreference  extends DialogPreference {
        private TimePicker mTimePicker;

        public TimeDialogPreference(Context context, AttributeSet attrs) {
                super(context, attrs);
        }

        @Override
        protected void onBindDialogView(View view) {
                super.onBindDialogView(view);
                mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);

                int time = 0;
                if (getKey().equals("alarm_time")) {
                        time = getPersistedInt(390);
                } else {
                        GregorianCalendar d = new GregorianCalendar();
                        time = Utilities.dateToTimeValue(d.getTime());
                }
                mTimePicker.setCurrentHour(time / 60);
                mTimePicker.setCurrentMinute(time % 60);
        }

        @Override
        protected void onDialogClosed(boolean positiveResult) {
                super.onDialogClosed(positiveResult);

                if (positiveResult) {
                        persistInt(Utilities.dateToTimeValue(mTimePicker.getCurrentHour(),
                                        mTimePicker.getCurrentMinute()));
                }
        }
}

