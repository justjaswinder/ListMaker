/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.listmaker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.sqlitecrudkotlin.DatabaseHelper
import com.example.sqlitecrudkotlin.UserModel
import kotlinx.android.synthetic.main.activity_list.*


class ListDetailActivity : AppCompatActivity() {

  lateinit var list: UserModel
  lateinit var menuu: Menu
  lateinit var et_name: EditText

  private var databaseHelper: DatabaseHelper? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_detail)
    setSupportActionBar(toolbar)
    list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)
    databaseHelper = DatabaseHelper(this)
    title = "Detail View"//list.name

    et_name = findViewById<EditText>(R.id.et_name)

    et_name.setText(list.name)
    et_name.isEnabled = false

    val actionbar = supportActionBar
    //set actionbar title
  //  actionbar!!.title = "New Activity"
    //set back button
    actionbar!!.setDisplayHomeAsUpEnabled(true)
    actionbar.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }


override fun onCreateOptionsMenu(menu: Menu): Boolean {
  // Inflate the menu; this adds items to the action bar if it is present.
  menuInflater.inflate(R.menu.menu_main_new, menu)

   menuu = menu
  val item = menu.findItem(R.id.action_save) as MenuItem
  item.isVisible = false
  return true
}

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.



    val id = item.getItemId()

    if (id == R.id.action_edit) {
      et_name.isEnabled = true
      item.isVisible = false
    menuu.findItem(R.id.action_save).setVisible(true);
      Toast.makeText(this, "Item One Clicked", Toast.LENGTH_SHORT).show()



      return true
    }

    if (id == R.id.action_save) {
      et_name.isEnabled = false
      item.isVisible = false
      menuu.findItem(R.id.action_edit).setVisible(true);
  //    Toast.makeText(this, "Item One Clicked", Toast.LENGTH_SHORT).show()
      databaseHelper!!.updateUser(list!!.getIds(), et_name!!.text.toString(), "KOOL")
      super.onBackPressed()

      return true
    }

    return  super.onOptionsItemSelected(item)

  }

  override fun onBackPressed() {
    val bundle = Bundle()
    bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)

    val intent = Intent()
    intent.putExtras(bundle)
    setResult(Activity.RESULT_OK, intent)
    super.onBackPressed()
  }
}
