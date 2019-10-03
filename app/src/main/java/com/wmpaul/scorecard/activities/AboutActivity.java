package com.wmpaul.scorecard.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.wmpaul.scorecard.R;

import java.util.Calendar;

public class AboutActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayText();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayText()
    {
        TextView versionText = (TextView) findViewById(R.id.text_view_version);
        TextView copyrightText = (TextView) findViewById(R.id.text_view_copyright);

        versionText.setText(String.format(getString(R.string.text_about_version), getString(R.string.app_version)));
        copyrightText.setText(String.format(getString(R.string.text_about_copyright), Calendar.getInstance().get(Calendar.YEAR)));
    }
}