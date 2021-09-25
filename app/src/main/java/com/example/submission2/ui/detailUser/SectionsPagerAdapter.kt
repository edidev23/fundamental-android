package com.example.submission2.ui.detailUser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission2.ui.detailUser.fragment.FollowerFragment
import com.example.submission2.ui.detailUser.fragment.FollowingFragment
import android.os.Bundle

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowerFragment()

                val args = Bundle()
                args.putString(USERNAME, username)
                fragment.setArguments(args)
            }
            1 -> {
                fragment = FollowingFragment()

                val args = Bundle()
                args.putString(USERNAME, username)
                fragment.setArguments(args)
            }
        }
        return fragment as Fragment
    }

    companion object {
        const val USERNAME = "username"
    }

}
