package com.example.submission2.ui.detailUser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.adapter.UserGithubAdapter
import com.example.submission2.databinding.FragmentFollowerBinding
import com.example.submission2.model.User
import com.example.submission2.ui.detailUser.DetailUserViewModel
import com.example.submission2.ui.detailUser.SectionsPagerAdapter.Companion.USERNAME

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.findFollowerUsers(username.toString())
        detailViewModel.listFollower.observe(viewLifecycleOwner, {
                user -> setFollowerUser(user)
        })
        detailViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun setFollowerUser(user: ArrayList<User>) {

        if(user.isEmpty()) {
            binding.resultKosong.visibility = View.VISIBLE
            Toast.makeText(context, "Data tidak ditemukan !", Toast.LENGTH_SHORT).show()
        } else {
            binding.resultKosong.visibility = View.GONE
        }

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