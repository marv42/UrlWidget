package com.marv42.urlwidget

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews


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

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val uri = Uri.parse("https://ams.lu/Tools/TheSameZuko.ams/5/Halle")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_IMMUTABLE)

        val remoteViews = RemoteViews(context.packageName, R.layout.url_widget)
        remoteViews.setOnClickPendingIntent(R.id.appwidget, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }
}
