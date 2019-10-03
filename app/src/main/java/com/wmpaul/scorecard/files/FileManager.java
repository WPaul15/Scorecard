package com.wmpaul.scorecard.files;

import android.content.Context;
import android.util.Log;

import com.wmpaul.scorecard.App;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager
{
    private static final File DIRECTORY = App.getContext().getFilesDir();

    FileManager(String fileName)
    {
        createFile(fileName);
    }

    public void createFile(String fileName)
    {
        boolean result = false;

        try
        {
            result = new File(DIRECTORY, fileName).createNewFile();
        }
        catch (IOException ex)
        {
            Log.e("json", "createFile:" + ex.toString());
        }

        //Log.d("json", "createFile: File created: " + result);
    }

    String readFile(String fileName)
    {
        String result = null;

        try
        {
            FileInputStream fileInputStream = App.getContext().openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null)
            {
                builder.append(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();

            result = builder.toString();

            //Log.d("json", "readFile: " + result + "\n");
        }
        catch (IOException ex)
        {
            Log.e("json", "readFile: " + ex.toString());
        }

        return result;
    }

    public void writeToFile(String fileName, JSONObject data)
    {
        FileOutputStream outputStream;

        try
        {
            outputStream = App.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.toString().getBytes());
            outputStream.close();
        }
        catch (IOException ex)
        {
            Log.e("json", "writeToFile: " + ex.toString());
        }
    }

    boolean isFilePresent(String fileName)
    {
        File file = new File(DIRECTORY, fileName);
        return file.exists();
    }

    public boolean isFileEmpty(String fileName)
    {
        try
        {
            FileInputStream fileInputStream = App.getContext().openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            //Log.d("json", "isFileEmpty: " + (line == null));

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();

            return line == null;
        }
        catch (IOException ex)
        {
            Log.e("json", "isFileEmpty: " + ex.toString());
        }

        return false;
    }

    public void deleteFile(String fileName)
    {
        File file = new File(DIRECTORY, fileName);
        file.delete();
    }
}