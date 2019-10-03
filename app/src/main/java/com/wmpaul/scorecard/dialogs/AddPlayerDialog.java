package com.wmpaul.scorecard.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wmpaul.scorecard.R;

import java.util.ArrayList;

public class AddPlayerDialog extends DialogFragment implements TextView.OnEditorActionListener
{
    public interface AddPlayerDialogListener
    {
        void onFinishAddPlayer(String name, int team);
    }

    private EditText editPlayerName;
    private Spinner selectTeam;
    private int numTeams;
    private boolean needsTeams = false;

    public AddPlayerDialog()
    {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        numTeams = args.getInt("numTeams");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_player, null);
        editPlayerName = (EditText) view.findViewById(R.id.edit_text_add_player);
        selectTeam = (Spinner) view.findViewById(R.id.spinner_select_team);

        if (numTeams > 0)
        {
            needsTeams = true;
            editPlayerName.setImeOptions(EditorInfo.IME_ACTION_NONE);
            initiateSelectTeam();
        }

        editPlayerName.requestFocus();
        editPlayerName.setOnEditorActionListener(this);

        final Dialog dialog = builder.setView(view)
                .setTitle(R.string.text_add_player_title)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }).create();

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button okButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                okButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        isPlayerValid();
                    }
                });
            }
        });

        return dialog;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event)
    {
        if (EditorInfo.IME_ACTION_DONE == actionId) isPlayerValid();

        return false;
    }

    private void initiateSelectTeam()
    {
        ArrayList<String> teams = new ArrayList<>();

        for (int i = 0; i < numTeams + 1; i++)
        {
            if (i == 0) teams.add(getResources().getString(R.string.text_select_team_spinner_prompt));
            else teams.add(String.format(getResources().getString(R.string.text_select_team_spinner_team), i));
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_row, teams)
        {
            @Override
            public boolean isEnabled(int position)
            {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView spinnerItem = (TextView) view;

                if (position == 0) spinnerItem.setTextColor(Color.GRAY);
                else spinnerItem.setTextColor(Color.BLACK);

                return view;
            }
        };

        selectTeam.setAdapter(adapter);
        selectTeam.setVisibility(View.VISIBLE);
    }

    private void isPlayerValid()
    {
        AddPlayerDialogListener activity = (AddPlayerDialogListener) getActivity();
        String playerName = editPlayerName.getText().toString();
        int team = 0;

        if (needsTeams)
        {
            team = selectTeam.getSelectedItemPosition();

            if (!playerName.equals("") && team != 0)
            {
                activity.onFinishAddPlayer(playerName, team);
                this.dismiss();
            }
        }
        else
        {
            if (!playerName.equals(""))
            {
                activity.onFinishAddPlayer(playerName, team);
                this.dismiss();
            }
        }
    }
}