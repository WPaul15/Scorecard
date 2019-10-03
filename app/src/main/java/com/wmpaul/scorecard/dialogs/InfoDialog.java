package com.wmpaul.scorecard.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.wmpaul.scorecard.R;

public class InfoDialog extends DialogFragment
{
    public interface InfoDialogListener
    {
        void onConfirmSelectYes();
    }

    public static final int DIALOG_INFO = 0;
    public static final int DIALOG_ERROR = 1;
    public static final int DIALOG_CONFIRM = 2;

    private String title, message;
    private int type;

    public InfoDialog()
    {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();

        title = args.getString("text_view_select_game_title");
        message = args.getString("message");
        type = args.getInt("type");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title)
                .setMessage(message);

        switch (type)
        {
            case DIALOG_INFO:
            case DIALOG_ERROR:
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                break;

            case DIALOG_CONFIRM:
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        InfoDialogListener activity = (InfoDialogListener) getActivity();

                        activity.onConfirmSelectYes();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                break;

        }

        return builder.create();
    }
}