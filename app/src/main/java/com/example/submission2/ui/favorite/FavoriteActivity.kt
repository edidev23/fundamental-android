package com.example.submission2.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.adapter.UserGithubAdapter
import com.example.submission2.database.Favorite
import com.example.submission2.databinding.ActivityFavoriteBinding
import com.example.submission2.helper.ViewModelFactory
import com.example.submission2.model.User
import com.example.submission2.ui.detailUser.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.favorite)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllFavorite().observe(this, { favList ->
            if (favList != null) {
                adapter.setListFavorite(favList)
            }
        })
        adapter = FavoriteAdapter()

        binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?.setHasFixedSize(true)
        binding?.rvUsers?.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Favorite) {
                showSelectedUser(data)
            }
        })

    }

    private fun showSelectedUser(user: Favorite) {
        showAlertDialog(ALERT_DIALOG_DELETE, user)
    }

    private fun showAlertDialog(type: Int, user: Favorite) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    favoriteViewModel.delete(user)
                    showToast(getString(R.string.deleted))
                }
                // jika finish maka close activity
                // finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}