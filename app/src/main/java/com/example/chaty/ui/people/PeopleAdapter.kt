package com.example.chaty.ui.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chaty.R
import com.example.chaty.model.User
import com.example.chaty.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.user_layout.view.*

class PeopleAdapter(var listener: OnItemClickListener): RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    private var users= mutableListOf<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        PeopleViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.user_layout,parent,false))

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {listener.onItemClick(users[position])}
    }

    override fun getItemCount()=users.size

    fun setList(users:List<User>){
        this.users= users as MutableList<User>
        notifyDataSetChanged()
    }

    class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(user:User){
            Glide.with(itemView.context).load(user.userImageUrl)
                .placeholder(R.drawable.default_user)
                .into(itemView.user_image)
            itemView.user_name.text=user.userName
        }
    }
}