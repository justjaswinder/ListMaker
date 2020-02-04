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

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.example.sqlitecrudkotlin.DatabaseHelper
import com.example.sqlitecrudkotlin.UserModel

import kotlinx.android.synthetic.main.activity_list.*

class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

  companion object {
    val INTENT_LIST_KEY = "list"
    val LIST_DETAIL_REQUEST_CODE = 123
  }


  lateinit var listsRecyclerView: RecyclerView

  private var databaseHelper: DatabaseHelper? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)
    setSupportActionBar(toolbar)
    title = "List"
    databaseHelper = DatabaseHelper(this)
    fab.setOnClickListener { view ->
      showCreateListDialog()
    }
    
    val lists = databaseHelper!!.getAllUsers// listDataManager.readLists()


    
    listsRecyclerView = findViewById<RecyclerView>(R.id.lists_recyclerview)
    listsRecyclerView.layoutManager = LinearLayoutManager(this)
    listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this,this)
  }



  private fun showCreateListDialog() {

    val dialogTitle = getString(R.string.name_of_list)
    val positiveButtonTitle = getString(R.string.create_list)

    val builder = AlertDialog.Builder(this)
    val listTitleEditText = EditText(this)

    listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT
    builder.setTitle(dialogTitle)
    builder.setView(listTitleEditText)

    builder.setPositiveButton(positiveButtonTitle, { dialog, i ->

//    i  val list = TaskList(listTitleEditText.text.toString())
//      listDataManager.saveList(list)

//      val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
//      recyclerAdapter.addList(list)


      databaseHelper!!.addUserDetail(listTitleEditText!!.text.toString(), "SPorts")// ethobby!!.text.toString())


      dialog.dismiss()
  //    showListDetail(list)
      updateLists()
    })

    builder.create().show()
  }

  private fun showListDetail(list: UserModel) {

    val listDetailIntent = Intent(this, ListDetailActivity::class.java)
    listDetailIntent.putExtra(INTENT_LIST_KEY, list)

    startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
  }

  override fun listItemClicked(list: UserModel, isTrue: Boolean) {
  //  showListDetail(list)
    updateLists()
    if(isTrue){
      showListDetail(list)

    }else {

    }
  }

  override fun onResume() {
    super.onResume()
    updateLists()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == LIST_DETAIL_REQUEST_CODE) {
      data?.let {
      //  listDataManager.saveList(data.getParcelableExtra(INTENT_LIST_KEY))
        updateLists()
      }
    }
  }

  private fun updateLists() {

    val lists = databaseHelper!!.getAllUsers
    //val lists = listDataManager.readLists()
    listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this,this)
  }
}
