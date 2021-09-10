package com.example.chaty.ui.friends_requests

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chaty.R
import com.example.chaty.listeners.FriendRequestListener
import com.example.chaty.model.Friend_Request
import com.example.chaty.model.User
import com.example.chaty.utils.Status
import kotlinx.android.synthetic.main.fragment_friends_requests.*
import kotlinx.android.synthetic.main.friend_request_layout.*


class FriendsRequestsFragment : Fragment(), FriendRequestListener {
    private val viewModel: FriendsRequestsViewModel by viewModels()
    private lateinit var adapter: FriendsRequestsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friends_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        viewModel.getFriendsRequests().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.requests = it.data!!
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setupRecycler() {
        friends_requests_rv.layoutManager = LinearLayoutManager(context)
        adapter = FriendsRequestsAdapter(emptyList(), this)
        friends_requests_rv.adapter = adapter
    }

    override fun onAcceptClick(request: Friend_Request) {
        viewModel.acceptRequest(request).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    accept_btn.visibility = GONE
                    reject_btn.visibility = GONE
                    accepted_text.visibility = VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onRejectClick(request: Friend_Request) {
        viewModel.rejectRequest(request).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    accept_btn.visibility = GONE
                    reject_btn.visibility = GONE
                    rejected_text.visibility = VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}