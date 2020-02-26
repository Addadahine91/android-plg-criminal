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
 *
 */

package com.example.pocketlawguide_criminal

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Filterable


class RecipeAdapter(private val context: Context,
                    private val dataSource: ArrayList<Case>) : BaseAdapter() {

  private val inflater: LayoutInflater
          = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

  override fun getCount(): Int {
    return dataSource.size
  }

  override fun getItem(position: Int): Any {
    return dataSource[position]
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view: View
    val holder: ViewHolder

    // 1
    if (convertView == null) {

      // 2
      view = inflater.inflate(R.layout.list_item_recipe, parent, false)

      // 3
      holder = ViewHolder()
      holder.titleTextView = view.findViewById(R.id.recipe_list_title) as TextView
      holder.subtitleTextView = view.findViewById(R.id.recipe_list_subtitle) as TextView

      // 4
      view.tag = holder
    } else {
      // 5
      view = convertView
      holder = convertView.tag as ViewHolder
    }

    // 6
    val titleTextView = holder.titleTextView
    val subtitleTextView = holder.subtitleTextView

    val recipe = getItem(position) as Case

    titleTextView.text = recipe.name
    subtitleTextView.text = recipe.attribute

    val titleTypeFace = ResourcesCompat.getFont(context, R.font.brandon_med)
    titleTextView.typeface = titleTypeFace

    val subtitleTypeFace = ResourcesCompat.getFont(context, R.font.brandon_med)
    subtitleTextView.typeface = subtitleTypeFace

    return view
  }

  private class ViewHolder {
    lateinit var titleTextView: TextView
    lateinit var subtitleTextView: TextView



  }

}
