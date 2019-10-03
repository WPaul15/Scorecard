package com.wmpaul.scorecard.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewStub;

import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.objects.Game;

public class RulesActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
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
        Game game = getIntent().getParcelableExtra("game");

        String contentName = "content_rules_" + game.getTitle().toLowerCase().replaceAll(" ", "_");
        int contentID = getApplicationContext().getResources().getIdentifier(contentName, "layout", this.getPackageName());

        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_rules_text);
        viewStub.setLayoutResource(contentID);
        viewStub.inflate();
    }
}