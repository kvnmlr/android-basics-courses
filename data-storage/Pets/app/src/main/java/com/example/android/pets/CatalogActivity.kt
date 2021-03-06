/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import com.example.android.pets.data.PetContract
import com.example.android.pets.data.PetDBHelper

/**
 * Displays list of pets that were entered and stored in the app.
 */
class CatalogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        // Setup FAB to open EditorActivity
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@CatalogActivity, EditorActivity::class.java)
            startActivity(intent)
        }
        displayDatabaseInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        menuInflater.inflate(R.menu.menu_catalog, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // User clicked on a menu option in the app bar overflow menu
        when (item.itemId) {
        // Respond to a click on the "Insert dummy data" menu option
            R.id.action_insert_dummy_data ->
                // Do nothing for now
                return true
        // Respond to a click on the "Delete all entries" menu option
            R.id.action_delete_all_entries ->
                // Do nothing for now
                return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private fun displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        val mDbHelper = PetDBHelper(this)

        // Create and/or open a database to read from it
        val db = mDbHelper.readableDatabase

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        val cursor = db.rawQuery("SELECT * FROM " + PetContract.PetEntry.TABLE_NAME, null)
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            val displayView = findViewById<View>(R.id.text_view_pet) as TextView
            displayView.text = "Number of rows in pets database table: " + cursor.count
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close()
        }
    }
}
