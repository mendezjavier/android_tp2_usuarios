package com.javiermendez.tp2_usuarios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class UsersAdapter(private val context: Context, private val userClick: (User) -> Unit) :
    RecyclerView.Adapter<UserVH>() {

    private var users: List<User> = emptyList()

    fun setUsers(users: List<User>) {
        this.users = users
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.activity_user, parent, false)
        return UserVH(view)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val user = this.users[position]
        holder.name.text = user.name
        holder.country.text = user.country
        holder.age.text = user.age.toString()

        Glide.with(context).load(user.image.small).into(holder.image)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                userClick(user)
            }

        })
    }

    override fun getItemCount(): Int {
        return this.users.size
    }
}