package com.wmpaul.scorecard.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.RVTouchListener;
import com.wmpaul.scorecard.adapters.SelectGameRVAdapter;
import com.wmpaul.scorecard.dialogs.InfoDialog;
import com.wmpaul.scorecard.objects.Game;
import com.wmpaul.scorecard.objects.GameComparator;

import java.util.ArrayList;
import java.util.Collections;

public class SelectGameActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private SelectGameRVAdapter adapter;
    private ArrayList<Game> games;

    //private static final String GAME_FILE = "games.json";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_select_game);
        linearLayoutManager = new LinearLayoutManager(this);
        games = new ArrayList<>();
        adapter = new SelectGameRVAdapter(games);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RVTouchListener(this, recyclerView, new RVTouchListener.OnClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Intent intent = new Intent(getApplicationContext(), GameSetupActivity.class);
                intent.putExtra("game", games.get(position));

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position)
            {
                showInfoDialog(position);
            }
        }));
        recyclerView.setAdapter(adapter);

        addGames();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = null;

        switch (item.getItemId())
        {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                break;
        }

        if (intent != null) startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog(int position)
    {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("dialog_info");

        if (fragment != null) fragmentManager.beginTransaction().remove(fragment).commit();

        String gameTitle = games.get(position).getTitle();

        String messageName = "text_game_info_" + gameTitle.toLowerCase().replaceAll(" ", "_");
        int messageID = getResources().getIdentifier(messageName, "string", this.getPackageName());
        String message = getString(messageID);

        Bundle bundle = new Bundle();
        bundle.putString("text_view_select_game_title", gameTitle);
        bundle.putString("message", message);
        bundle.putInt("type", InfoDialog.DIALOG_INFO);

        InfoDialog dialog = new InfoDialog();
        dialog.setArguments(bundle);
        dialog.show(fragmentManager, "dialog_info");
    }

    private void addGames()
    {
        /*GameFileManager manager = new GameFileManager(GAME_FILE);

        manager.deleteFile(GAME_FILE);
        manager.createFile(GAME_FILE);

        if (manager.isFileEmpty(GAME_FILE))
        {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            jsonArray.put(new Game("Rich Cards", 4, -1, 3, 0, -1).toJson());
            jsonArray.put(new Game("Nerts", 2, -1, -1, 0, -1).toJson());
            jsonArray.put(new Game("Euchre", 4, 4, 0, 2, 2).toJson());

            try
            {
                jsonObject.put("games", jsonArray);
            }
            catch (JSONException ex)
            {
                Log.e("json", "addGames: " + ex.toString());
            }

            manager.writeToFile(GAME_FILE, jsonObject);
        }

        ArrayList<Game> listGames = manager.readGameFile(GAME_FILE);*/

        games.add(new Game("Rich Cards", 4, -1, 3, 0, -1));
        games.add(new Game("Nerts", 2, -1, -1, 0, -1));
        games.add(new Game("Euchre", 4, 4, 0, 2, 2));

        Collections.sort(games, new GameComparator());

        adapter.notifyDataSetChanged();
    }
}
