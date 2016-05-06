package com.keep.expense.expenesekeep;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by admin on 10/24/2015.
 */
public class AnalyticsApplication extends Application
{ private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker("UA-69243655-1");
            mTracker.enableExceptionReporting(true);

            // Enable Remarketing, Demographics & Interests reports
            // https://developers.google.com/analytics/devguides/collection/android/display-features
            mTracker.enableAdvertisingIdCollection(true);

            // Enable automatic activity tracking for your app
            mTracker.enableAutoActivityTracking(true);
        }
        return mTracker;
    }}