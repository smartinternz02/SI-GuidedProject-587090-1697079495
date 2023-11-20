package com.example.snacksquad

import android.app.AlertDialog
import android.content.Context
import android.widget.RatingBar
import android.widget.Toast

class RatingDialog(private val context: Context) {

    fun showRatingDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Rate Your Experience")
        builder.setMessage("Please rate your experience")

        val ratingBar = RatingBar(context)
        3.also { ratingBar.numStars = it }
        ratingBar.rating = 0f

        builder.setView(ratingBar)

        builder.setPositiveButton("Submit") { _, _ ->
            val rating = ratingBar.rating
            // You can handle the rating here, for example, save it to a database
            Toast.makeText(context, "Rating submitted: $rating", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Later") { dialog, _ ->
            dialog.dismiss()
        }

        builder.setOnCancelListener {
            // Handle cancel if needed
        }

        builder.show()
    }
}
