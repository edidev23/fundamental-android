package com.example.submission2.ui.detailUser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.adapter.UserGithubAdapter
import com.example.submission2.databinding.FragmentFollowingBinding
import com.example.submission2.model.User
import com.example.submission2.ui.detailUser.DetailUserViewModel

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        viewModel.listFollowing.observe(viewLifecycleOwner, { user -> setFollowingUser(user)})
        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        viewModel.listFollowing.observe(viewLifecycleOwner, { user -> setFollowingUser(user)})
        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun setFollowingUser(user: ArrayList<User>) {

        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        val listUser = UserGithubAdapter(user)
        binding.rvUsers.adapter = listUser

        listUser.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Toast.makeText(context, "Anda memilih " + data.login, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}