package com.wmpaul.scorecard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RVTouchListener implements RecyclerView.OnItemTouchListener
{
    public interface OnClickListener
    {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    private GestureDetector gestureDetector;
    private OnClickListener OnClickListener;

    public RVTouchListener(Context context, final RecyclerView recyclerView, final OnClickListener OnClickListener)
    {
        this.OnClickListener = OnClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent event)
            {
                return true;
            }
            @Override
            public void onLongPress(MotionEvent event)
            {
                View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
                if (child != null && OnClickListener != null)
                    OnClickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event)
    {
        View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if (child != null && OnClickListener != null && gestureDetector.onTouchEvent(event))
            OnClickListener.onClick(child, recyclerView.getChildLayoutPosition(child));
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent event)
    {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
    {
    }
}