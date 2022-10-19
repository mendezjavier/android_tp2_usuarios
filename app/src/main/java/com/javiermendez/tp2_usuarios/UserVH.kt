package com.javiermendez.tp2_usuarios

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserVH(view: View): RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.user_image)
    val name: TextView = view.findViewById(R.id.user_name)
    val country: TextView = view.findViewById(R.id.user_country)
    val age: TextView = view.findViewById(R.id.user_age)
}

