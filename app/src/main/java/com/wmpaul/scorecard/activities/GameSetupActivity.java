package com.wmpaul.scorecard.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wmpaul.scorecard.R;
import com.wmpaul.scorecard.RVTouchListener;
import com.wmpaul.scorecard.adapters.GameSetupRVAdapter;
import com.wmpaul.scorecard.dialogs.AddPlayerDialog;
import com.wmpaul.scorecard.dialogs.AskEditPlayerDialog;
import com.wmpaul.scorecard.dialogs.EditPlayerDialog;
import com.wmpaul.scorecard.dialogs.InfoDialog;
import com.wmpaul.scorecard.objects.Game;
import com.wmpaul.scorecard.objects.Player;

public class GameSetupActivity extends AppCompatActivity
        implements AddPlayerDialog.AddPlayerDialogListener,
        AskEditPlayerDialog.AskEditPlayerDialogListener,
        EditPlayerDialog.EditPlayerDialogListener,
        InfoDialog.InfoDialogListener
{
    FloatingActionButton fabAddPlayer;
    FloatingActionButton fabStartGame;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private GameSetupRVAdapter adapter;
    private Game game;
    private int numTeams;
    private int[] indexLastAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        game = getIntent().getParcelableExtra("game");
        numTeams = game.getNumTeams();
        indexLastAdded = new int[numTeams];

        for (int i = 0; i < indexLastAdded.length; i++)
        {
            indexLastAdded[i] = -1;
        }

        Log.d("gameInfo", game.toString());
        Log.d("indexLastAdded", indexLastAddedToString());

        getSupportActionBar().setTitle(String.format(getString(R.string.title_game_setup), game.getTitle()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_game_setup);
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new GameSetupRVAdapter(game.needsTeams(), game.getPlayers());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RVTouchListener(this, recyclerView, new RVTouchListener.OnClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
            }

            @Override
            public void onLongClick(View view, int position)
            {
                showAskEditPlayerDialog(game.getPlayers().get(position).getName(), position);
            }
        }));
        recyclerView.setAdapter(adapter);

        fabAddPlayer = (FloatingActionButton) findViewById(R.id.fab_add_player);
        fabStartGame = (FloatingActionButton) findViewById(R.id.fab_start_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_rules, menu);
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
            case R.id.action_rules:
                intent = new Intent(this, RulesActivity.class);
                break;
        }

        if (intent != null)
        {
            intent.putExtra("game", game);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFABAddPlayerClick(View view)
    {
        showAddPlayerDialog();
    }

    public void onFABStartGameClick(View view)
    {
        String title, message;

        if (game.needsTeams() && !game.areTeamSizesValid())
        {
            title = getResources().getString(R.string.dialog_title_error);
            message = getResources().getString(R.string.text_error_ppt_not_valid);

            showInfoDialog(title, message, InfoDialog.DIALOG_ERROR);
        }
        else if (game.getRecommendedRange() > 0 && !game.isNumPlayersInRecommended())
        {
            title = getResources().getString(R.string.dialog_title_confirm);
            message = String.format(getResources().getString(R.string.text_confirm_np_out_of_range), game.getNumPlayers(), game.getNumRecommendedPlayers());

            showInfoDialog(title, message, InfoDialog.DIALOG_CONFIRM);
        }
        else
        {
            game.consolidateEntities();

            Log.d("gameInfo", game.toString());

            Intent intent = new Intent(this, ScorekeeperActivity.class);
            intent.putExtra("game", game);
            startActivity(intent);
        }
    }

    @Override
    public void onFinishAddPlayer(String name, int team)
    {
        int index;
        int numPlayers = game.getNumPlayers();

        if (game.needsTeams())
        {
            index = indexLastAdded[team - 1] + 1;
            updateIndexLastAdded(team, 1);
        }
        else index = numPlayers;

        game.addPlayer(index, new Player(name, team));

        adapter.notifyItemInserted(index);
        recyclerView.smoothScrollToPosition(index);

        numPlayers = game.getNumPlayers();

        if (numPlayers == game.getMaxPlayers())
            fabAddPlayer.setVisibility(View.GONE);

        if (numPlayers == game.getMinPlayers())
            fabStartGame.setVisibility(View.VISIBLE);

        Log.d("gameInfo", game.toString());
        Log.d("indexLastAdded", indexLastAddedToString());
    }

    @Override
    public void onSelectDeletePlayer(int index)
    {
        int team = game.getPlayer(index).getTeam();
        int numPlayers = game.getNumPlayers();

        game.removePlayer(index);
        adapter.notifyItemRemoved(index);

        if (numPlayers != game.getMaxPlayers())
            fabAddPlayer.setVisibility(View.VISIBLE);

        if (numPlayers == game.getMinPlayers())
            fabStartGame.setVisibility(View.GONE);

        if (game.needsTeams()) updateIndexLastAdded(team, -1);

        Log.d("gameInfo", game.toString());
        Log.d("indexLastAdded", indexLastAddedToString());
    }

    @Override
    public void onSelectEditPlayer(int index)
    {
        showEditPlayerDialog(game.getPlayer(index).getName(), game.getPlayer(index).getTeam(), index);
    }

    @Override
    public void onFinishEditPlayer(String newName, int newTeam, int oldIndex)
    {
        Player player = game.getPlayer(oldIndex);

        player.setName(newName);
        player.setTeam(newTeam);

        if (player.getOldTeam() != player.getTeam()) // Team was changed
        {
            game.removePlayer(oldIndex);
            updateIndexLastAdded(player.getOldTeam(), -1);

            int newIndex = indexLastAdded[newTeam - 1] + 1;

            game.addPlayer(newIndex, player);
            updateIndexLastAdded(newTeam, 1);

            player.setOldTeam(newTeam);

            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(newIndex);
        }
        else
        {
            adapter.notifyItemChanged(oldIndex);
        }

        Log.d("gameInfo", game.toString());
        Log.d("indexLastAdded", indexLastAddedToString());
    }

    @Override
    public void onConfirmSelectYes()
    {
        Intent intent = new Intent(this, ScorekeeperActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    private void showAddPlayerDialog()
    {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("dialog_add_player");

        if (fragment != null) fragmentManager.beginTransaction().remove(fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putInt("numTeams", game.getNumTeams());

        AddPlayerDialog dialog = new AddPlayerDialog();
        dialog.setArguments(bundle);
        dialog.show(fragmentManager, "dialog_add_player");
    }

    private void showAskEditPlayerDialog(String playerName, int index)
    {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("dialog_ask_edit_player");

        if (fragment != null) fragmentManager.beginTransaction().remove(fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString("playerName", playerName);
        bundle.putInt("index", index);

        AskEditPlayerDialog dialog = new AskEditPlayerDialog();
        dialog.setArguments(bundle);
        dialog.show(fragmentManager, "dialog_ask_edit_player");
    }

    private void showEditPlayerDialog(String playerName, int playerTeam, int index)
    {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("dialog_edit_player");

        if (fragment != null) fragmentManager.beginTransaction().remove(fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString("originalPlayerName", playerName);
        bundle.putInt("originalPlayerTeam", playerTeam);
        bundle.putInt("index", index);
        bundle.putInt("numTeams", numTeams);

        EditPlayerDialog dialog = new EditPlayerDialog();
        dialog.setArguments(bundle);
        dialog.show(fragmentManager, "dialog_edit_player");
    }

    private void showInfoDialog(String title, String message, int type)
    {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("dialog_info");

        if (fragment != null) fragmentManager.beginTransaction().remove(fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString("text_view_select_game_title", title);
        bundle.putString("message", message);
        bundle.putInt("type", type);

        InfoDialog dialog = new InfoDialog();
        dialog.setArguments(bundle);
        dialog.show(fragmentManager, "dialog_info");
    }

    private void updateIndexLastAdded(int startValue, int increment)
    {
        for (int i = startValue - 1; i < indexLastAdded.length; i++)
        {
            indexLastAdded[i] += increment;
        }
    }

    private String indexLastAddedToString()
    {
        String output = "[";

        for (int i = 0; i < indexLastAdded.length; i++)
        {
            output += indexLastAdded[i];
            if (i < indexLastAdded.length - 1) output += ",";
        }

        return output + "]";
    }
}