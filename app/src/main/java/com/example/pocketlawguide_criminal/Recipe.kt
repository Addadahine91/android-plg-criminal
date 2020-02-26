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
import org.json.JSONException
import org.json.JSONObject


class Case (
    val name: String,
    val attribute: String,
    val facts: String,
    val judgment: String,
    val category: String)
{

  companion object {

    fun getRecipesFromFile(filename: String, context: Context): ArrayList<Case> {
      val recipeList = ArrayList<Case>()

      try {
        // Load data
        val jsonString = loadJsonFromAsset("recipes.json", context)
        val json = JSONObject(jsonString)
        val recipes = json.getJSONArray("recipes")

        // Get Recipe objects from data
        (0 until recipes.length()).mapTo(recipeList) {
          Case(recipes.getJSONObject(it).getString("name"),
                recipes.getJSONObject(it).getString("attribute"),
                recipes.getJSONObject(it).getString("facts"),
                recipes.getJSONObject(it).getString("judgment"),
                recipes.getJSONObject(it).getString("category"))
        }
      } catch (e: JSONException) {
        e.printStackTrace()
      }

      return recipeList
    }

    private fun loadJsonFromAsset(filename: String, context: Context): String? {
      var json: String? = null

      try {
        val inputStream = context.assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charsets.UTF_8)
      } catch (ex: java.io.IOException) {
        ex.printStackTrace()
        return null
      }

      return json
    }
  }
}