package com.ucd.user.weatherfitness.model;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.ucd.user.weatherfitness.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 11/19/2017.
 */

public class AddEventToCal extends Activity {

    private Context context;
    private String dtTime;

    public AddEventToCal(Context context) {
        this.context = context;
    }
    public AddEventToCal(Context context,String dtTime) {
        this.context = context;
        this.dtTime = dtTime;
    }


    public void AddEvent(Context context,String dtTime) throws Exception {
        String startDateString = dtTime;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:");
        Date startDate;
        startDate = df.parse(startDateString);

        ContentResolver cr = context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE, "WeatherFitness");
        cv.put(CalendarContract.Events.DESCRIPTION, "Description of TestingWeatherEvent1");
        cv.put(CalendarContract.Events.EVENT_LOCATION, "Location TestingWeatherEvent1");
        if(dtTime!=null)
            cv.put(CalendarContract.Events.DTSTART, startDate.getTime());
        else
            cv.put(CalendarContract.Events.DTSTART, Calendar.getInstance().getTimeInMillis());

        cv.put(CalendarContract.Events.DTEND, Calendar.getInstance().getTimeInMillis() + 60 * 60 * 1000);
        cv.put(CalendarContract.Events.CALENDAR_ID, "3");
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
        if (context.checkSelfPermission(Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_CALENDAR}, 0);
        }
        else {

            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);
            Toast.makeText(context, "Event Added to calendar", Toast.LENGTH_SHORT).show();
        }

    }

}
