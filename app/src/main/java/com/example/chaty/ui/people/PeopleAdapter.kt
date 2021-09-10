package com.example.chaty.ui.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaty.R
import com.example.chaty.model.User
import com.example.chaty.listeners.OnItemClickListener

class PeopleAdapter(listener: OnItemClickListener): RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    private var users= mutableListOf<User>()
    private var mListener=listener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        PeopleViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.user_layout,parent,false))

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {mListener.onItemClick(users[position])}
    }

    override fun getItemCount()=users.size

    fun setList(users:List<User>){
        this.users= users as MutableList<User>
        notifyDataSetChanged()
    }

    class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userImage=itemView.findViewById<ImageView>(R.id.user_image)
        val userName=itemView.findViewById<TextView>(R.id.user_name)

        fun bind(user:User){
            userName.text=user.userName
        }
    }
}