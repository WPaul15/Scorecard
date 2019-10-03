package com.wmpaul.scorecard.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.wmpaul.scorecard.R;

public class AskEditPlayerDialog extends DialogFragment
{
    public interface AskEditPlayerDialogListener
    {
        void onSelectDeletePlayer(int index);
        void onSelectEditPlayer(int index);
    }

    public AskEditPlayerDialog()
    {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();

        final String playerName = args.getString("playerName");
        final int index = args.getInt("index");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(String.format(getString(R.string.text_ask_edit_player_title), playerName))
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        AskEditPlayerDialogListener activity = (AskEditPlayerDialogListener) getActivity();

                        activity.onSelectDeletePlayer(index);
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(R.string.edit, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        AskEditPlayerDialogListener activity = (AskEditPlayerDialogListener) getActivity();

                        activity.onSelectEditPlayer(index);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
