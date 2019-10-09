package com.cs360.timothyfreyberger.efolio.other;

import android.content.Intent;
import android.icu.util.Calendar;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEvent {


    public static Intent addEvent(Contact contact) throws IOException, ParseException {
        Calendar calendar = Calendar.getInstance();
        Date startTime = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
                .parse(contact.getDate()+ " " + contact.getTime());

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", startTime.getTime());
        intent.putExtra("allDay", false);
        intent.putExtra("endTime", startTime.getTime()+60*60*1000);
        intent.putExtra("title", "Interview w/ " + contact.getName());
        return intent;
    }

}
