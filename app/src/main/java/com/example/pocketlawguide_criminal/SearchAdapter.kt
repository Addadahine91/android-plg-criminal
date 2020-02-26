package com.example.pocketlawguide_criminal

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter(context: Context, arrayList: ArrayList<HashMap<String, String>>) : RecipeAdapter() {

    //Passing Values to Local Variables
    var arrayList = arrayList
    var context = context

    //Store image and arraylist in Temp Array List we Required it later
    var tempNameVersionList = ArrayList(arrayList)


    //Auto Generated Method
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myview = convertView
        val holder: ViewHolder

        if (convertView == null) {

            //If Over View is Null than we Inflater view using Layout Inflater
            val mInflater = (context as Activity).layoutInflater

            //Inflating our list_row.
            myview = mInflater!!.inflate(R.layout.list_row, parent, false)

            //Create Object of ViewHolder Class and set our View to it
            holder = ViewHolder()

            //Find view By Id For all our Widget taken in list_row.

            /*Here !! are use for non-null asserted two prevent From null.
             you can also use Only Safe (?.)

            */
            holder.mHeader = myview!!.findViewById<TextView>(R.id.header) as TextView
            holder.mSubHeader = myview!!.findViewById<TextView>(R.id.subHeader) as TextView

            //Set A Tag to Identify our view.
            myview.setTag(holder)

        } else {

            //If Ouer View in not Null than Just get View using Tag and pass to holder Object.
            holder = myview!!.getTag() as ViewHolder
        }

        //Getting HasaMap At Perticular Position
        val map = arrayList.get(position)

        //Setting name to TextView it's Key from HashMap At Position
        holder.mHeader!!.setText(map.get("name"))

        //Setting version to TextView it's Key from HashMap At Position
        holder.mSubHeader!!.setText(map.get("version"))


        return myview

    }

    //Auto Generated Method
    override fun getItem(p0: Int): Any {

        //Return the Current Position of ArrayList.
        return arrayList.get(p0)

    }

    //Auto Generated Method
    override fun getItemId(p0: Int): Long {
        return 0
    }

    //Auto Generated Method

    override fun getCount(): Int {

        //Return Size Of ArrayList
        return arrayList.size
    }


    //Create A class To hold over View like we taken in list_row.xml
    class ViewHolder {

        var mHeader: TextView? = null
        var mSubHeader: TextView? = null
    }


    //Function to set data according to Search Keyword in ListView
    fun filter(text: String?) {


        //Our Search text
        val text = text!!.toLowerCase(Locale.getDefault())


        //Here We Clear Both ArrayList because We update according to Search query.
        image.clear()
        arrayList.clear()


        if (text.length == 0) {

            /*If Search query is Empty than we add all temp data into our main ArrayList

            We store Value in temp in Starting of Program.

            */

            arrayList.addAll(tempNameVersionList)


        } else {


            for (i in 0..tempNameVersionList.size - 1) {

                /*
                If our Search query is not empty than we Check Our search keyword in Temp ArrayList.
                if our Search Keyword in Temp ArrayList than we add to our Main ArrayList
                */

                if (tempNameVersionList.get(i).get("name")!!.toLowerCase(Locale.getDefault()).contains(text)) {

                    arrayList.add(tempNameVersionList.get(i))


                }

            }
        }

        //This is to notify that data change in Adapter and Reflect the changes.
        notifyDataSetChanged()


    }


}