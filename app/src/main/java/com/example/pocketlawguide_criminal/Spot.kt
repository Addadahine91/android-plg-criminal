package com.example.pocketlawguide_criminal

data class Spot(
        val id: Long = counter++,
        val facts: String,
        val judgment: String,
        val title: String,
        val category: String
) {
    companion object {
        private var counter = 0L
    }
}
