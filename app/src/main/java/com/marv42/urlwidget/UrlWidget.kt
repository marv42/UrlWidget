package com.marv42.urlwidget

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

internal const val prefPrefixUrl = "url"
internal const val prefPrefixDisplayText = "displayText"


class UrlWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val prefs = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
            val url = prefs.getString(getPrefKey(prefPrefixUrl, appWidgetId), "https://www.example.com")
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_IMMUTABLE)

            val remoteViews = RemoteViews(context.packageName, R.layout.url_widget)
            val defaultDisplayText = context.getString(R.string.appwidget_text_default)
            val displayText = prefs.getString(getPrefKey(prefPrefixDisplayText, appWidgetId), defaultDisplayText)
            remoteViews.setTextViewText(R.id.appwidget_text, displayText)
            remoteViews.setContentDescription(R.id.appwidget_text, displayText)
            remoteViews.setOnClickPendingIntent(R.id.appwidget, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }

        internal fun getPrefKey(prefix: String, appWidgetId: Int) = "${prefix}_$appWidgetId"
    }
}
