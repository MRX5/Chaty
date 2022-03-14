package com.example.chaty.ui.people

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
import com.example.chaty.ui.add.AddViewModel
import com.example.chaty.utils.Status
import kotlinx.android.synthetic.main.fragment_people.*

class PeopleFragment : Fragment(), OnItemClickListener {
    private lateinit var viewModel:PeopleViewModel
    private lateinit var adapter: PeopleAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(PeopleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupRecycler()
        viewModel.loadFriends().observe(requireActivity(),{
            when(it.status){
                Status.SUCCESS->{
                    it.data?.let { users -> adapter.setList(users) }
                }
                Status.ERROR->{
                    it.message?.let {error-> Toast.makeText(context,error,Toast.LENGTH_LONG).show() }
                }
            }
        })
    }

    private fun setupRecycler() {
        adapter=PeopleAdapter(this)
        friends_rv.layoutManager=LinearLayoutManager(context)
        friends_rv.adapter=adapter
    }

    override fun onItemClick(user: User) {
        val directions=PeopleFragmentDirections.actionPeopleFragmentToConversationFragment(user)
        navController.navigate(directions)
    }
}