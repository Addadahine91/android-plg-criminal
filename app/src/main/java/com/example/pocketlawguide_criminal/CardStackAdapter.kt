package com.example.pocketlawguide_criminal

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.text.method.ScrollingMovementMethod



class CardStackAdapter(
         private var spots: List<Spot> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot: Spot = spots[position]
        holder.name.text = spot.facts
        holder.title.text = spot.title
        holder.category.text = spot.category

        holder.name.movementMethod = ScrollingMovementMethod()
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<Spot>) {
        this.spots = spots
    }

    fun getSpots(): List<Spot> {
        return spots
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.item_facts)
        var title: TextView = view.findViewById(R.id.item_title)
        var category: TextView = view.findViewById(R.id.item_category)

    }


}
