package com.marv42.urlwidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.marv42.urlwidget.UrlWidget.Companion.getPrefKey

internal const val sharedPrefName = "UrlWidgetPrefs"


class UrlWidgetConfigActivity : Activity() {

    private lateinit var editTextUrl: EditText
    private lateinit var editTextDisplayText: EditText
    private lateinit var buttonSave: Button
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_config)
        setResult(RESULT_CANCELED)

        editTextUrl = findViewById(R.id.editTextUrl)
        editTextDisplayText = findViewById(R.id.editTextDisplayText)
        buttonSave = findViewById(R.id.buttonSave)

        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        buttonSave.setOnClickListener {
            saveAndFinish()
        }
    }

    private fun saveAndFinish() {
        val prefs = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val url = editTextUrl.text.toString()
        prefs.edit().putString(getPrefKey(prefPrefixUrl, appWidgetId), url).apply()
        val displayText = editTextDisplayText.text.toString()
        prefs.edit().putString(getPrefKey(prefPrefixDisplayText, appWidgetId), displayText).apply()

        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)

        val appWidgetManager = AppWidgetManager.getInstance(this)
        UrlWidget.updateAppWidget(this, appWidgetManager, appWidgetId)
        finish()
    }
}
