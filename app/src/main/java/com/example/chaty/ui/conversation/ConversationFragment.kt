package com.example.chaty.ui.conversation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.chaty.R
import com.example.chaty.model.User
import com.example.chaty.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.conversation_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_conversation.*


class ConversationFragment : Fragment() {
    private lateinit var user: User
    private val viewModel: ConversationViewModel by viewModels()
    private lateinit var adapter: ConversationAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.bottom_navigation?.visibility = GONE
        val bundle = arguments
        if (bundle != null) {
            val args = ConversationFragmentArgs.fromBundle(bundle)
            user = args.user
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_conversation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupRecycler()
        loadChat()
        sendMessage()
    }

    private fun setupUI() {
        (activity as AppCompatActivity).setSupportActionBar(app_bar.custom_toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
        app_bar.user_name.text=user.userName
        Glide.with(this).load(user.userImageUrl)
            .placeholder(R.drawable.default_user)
            .into(app_bar.user_image)

    }

    private fun loadChat() {
        viewModel.loadChat(user.userID).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.messages = it.data!!
                    adapter.notifyDataSetChanged()
                    conversation_rv?.scrollToPosition(adapter.itemCount - 1)
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun setupRecycler() {
        adapter = ConversationAdapter(emptyList())
        conversation_rv.layoutManager = LinearLayoutManager(context)
        conversation_rv.adapter = adapter
    }


    private fun sendMessage() {
        send_msg_btn.setOnClickListener {
            val messageBody = message_edittext.text.toString()
            if (messageBody.isNotEmpty()) {
                viewModel.sendMessage(user, messageBody).observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            message_edittext.setText("")
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.bottom_navigation?.visibility = VISIBLE
    }
}