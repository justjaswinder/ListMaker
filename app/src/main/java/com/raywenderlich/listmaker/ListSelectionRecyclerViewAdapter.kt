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

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.sqlitecrudkotlin.DatabaseHelper
import com.example.sqlitecrudkotlin.UserModel
import java.util.ArrayList

class ListSelectionRecyclerViewAdapter(val lists: ArrayList<UserModel>, val clickListener: ListSelectionRecyclerViewClickListener, val context: Context) : RecyclerView.Adapter<ListSelectionViewHolder>() {
  private var databaseHelper: DatabaseHelper? = null


  interface ListSelectionRecyclerViewClickListener {
    fun listItemClicked(list: UserModel , isTrue: Boolean )
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSelectionViewHolder {
    val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.list_selection_view_holder, parent, false)
    databaseHelper = DatabaseHelper(context)
    return ListSelectionViewHolder(view)
  }

  override fun onBindViewHolder(holder: ListSelectionViewHolder?, position: Int) {

    if (holder != null) {
      holder.listPosition.text = (position + 1).toString()
      holder.listTitle.text = lists.get(position).name
      holder.btn_delete.setOnClickListener {

        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle("Delete")

        // Display a message on alert dialog
        builder.setMessage("Are you want to delete this?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
          // Do something when user press the positive button
       //   Toast.makeText(context,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()

          databaseHelper!!.deleteUSer(lists[position]!!.getIds())
          // Change the app background color
          clickListener.listItemClicked(lists.get(position),false)
          dialog.dismiss()
         // root_layout.setBackgroundColor(Color.RED)
        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){dialog,which ->
          dialog.dismiss()
         // Toast.makeText(context,"You are not agree.",Toast.LENGTH_SHORT).show()
        }


        // Display a neutral button on alert dialog
//        builder.setNeutralButton("Cancel"){_,_ ->
//          Toast.makeText(context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
//        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()

      }
      holder.itemView.setOnClickListener({
        clickListener.listItemClicked(lists.get(position),true)
      })
    }
  }

  override fun getItemCount(): Int {
    return lists.size
  }

  fun addList(list: UserModel) {
    lists.add(list)
    notifyDataSetChanged()
  }
}
