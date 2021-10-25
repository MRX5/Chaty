package com.example.chaty.ui.chats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.chaty.R
import com.example.chaty.listeners.OnItemClickListener
import com.example.chaty.model.Chat
import com.example.chaty.model.User
import com.example.chaty.ui.people.PeopleFragmentDirections
import com.example.chaty.utils.Status
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.fragment_chats.*

private const val TAG = "ChatsFragmentmostafa"
class ChatsFragment : Fragment(),OnItemClickListener {

    private lateinit var adapter: ChatsAdapter
    private lateinit var viewModel:ChatsViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(ChatsViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupUI()
        getCurrentUser()
        getChats()
    }

    private fun setupUI() {

        val img=searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        img.setColorFilter(R.color.purple_200)

        adapter= ChatsAdapter(emptyList(),this)
        chats_rv.layoutManager=LinearLayoutManager(context)
        chats_rv.adapter=adapter
    }

    private fun getChats() {
           viewModel.loadChats().observe(requireActivity(),{
                when(it.status) {
                    Status.LOADING->{
                        chats_progressbar?.visibility=VISIBLE
                    }
                    Status.SUCCESS -> {
                        chats_progressbar?.visibility= GONE
                        adapter.chats = it.data!!
                        adapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        chats_progressbar?.visibility= GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    private fun getCurrentUser(){
        viewModel.currentUser().observe(requireActivity(),{
            when(it.status){
                Status.SUCCESS->{
                    Glide.with(this).load(it.data?.userImageUrl)
                        .placeholder(context?.let { it1 -> getDrawable(it1,R.drawable.default_user) })
                        .into(user_image)
                }
                Status.ERROR->{
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    override fun onItemClick(user: User) {
        val directions= ChatsFragmentDirections.actionChatsFragmentToConversationFragment(user)
        navController.navigate(directions)
    }
}