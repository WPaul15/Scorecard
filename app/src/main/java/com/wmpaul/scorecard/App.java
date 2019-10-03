package com.wmpaul.scorecard;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.lang.ref.WeakReference;

public class App extends Application
{
    private static WeakReference<Context> context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = new WeakReference<Context>(this);
    }

    public static Context getContext()
    {
        return context.get();
    }

    public static Resources getContextResources()
    {
        return context.get().getResources();
    }
}