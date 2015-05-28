package ee.sinchukov.sendemail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.apache.http.protocol.HTTP;


import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private EditText editTextTo;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private EditText editTextEvent;
    private EditText editTextDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSubject = (EditText)findViewById(R.id.editTextSubject);
        editTextTo = (EditText)findViewById(R.id.editTextTo);
        editTextMessage = (EditText)findViewById(R.id.editTextMessage);
        editTextEvent =  (EditText)findViewById(R.id.editTextEvent);
        editTextDial =  (EditText)findViewById(R.id.editTextDial);
    }

    public void dial(View view){

        String telNumber = editTextDial.getText().toString();

        String uri = "tel:" + telNumber ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public void sendMessage(View view){

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{editTextTo.getText().toString()}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, editTextSubject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, editTextMessage.getText().toString());
     //   emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));

         // Verify that email application exists
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(emailIntent);
        }

    }

    public void addEvent(View view){

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 5, 28, 22, 0);
        Calendar endTime  = Calendar.getInstance();
        endTime.set(2015, 5, 28, 22, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, editTextEvent.getText().toString());
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Tallinn");

        // Verify that email application exists
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(calendarIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(calendarIntent);
        }

    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
