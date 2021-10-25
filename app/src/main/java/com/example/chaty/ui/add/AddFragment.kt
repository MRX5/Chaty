package com.example.chaty.ui.add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chaty.R
import com.example.chaty.model.User
import com.example.chaty.listeners.OnItemClickListener
import com.example.chaty.ui.chats.ChatsViewModel
import com.example.chaty.utils.Status
import kotlinx.android.synthetic.main.fragment_add.*

private const val TAG = "mostafa"

class AddFragment : Fragment(), OnItemClickListener {
    private lateinit var viewModel: AddViewModel
    private lateinit var adapter: AddFriendAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(AddViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupRecycler()
        setupNavigation()
        loadFriends()
    }

    private fun setupRecycler() {
        adapter = AddFriendAdapter(emptyList(), this)
        friends_rv.layoutManager = LinearLayoutManager(context)
        friends_rv.adapter = adapter
    }

    private fun setupNavigation() {
        friends_request_btn.setOnClickListener {
            navController.navigate(R.id.action_addFragment_to_friendsRequestsFragment)
        }
    }

    private fun loadFriends() {
        viewModel.getPeopleLiveData().observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.people = it.data!!
                    adapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onItemClick(user: User) {
        viewModel.addFriend(user)
        viewModel.getAddFriendState().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}