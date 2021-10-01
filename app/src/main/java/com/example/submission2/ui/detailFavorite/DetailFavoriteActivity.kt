package com.example.submission2.ui.detailFavorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.R
import com.example.submission2.database.Favorite
import com.example.submission2.databinding.ActivityDetailFavoriteBinding
import com.example.submission2.databinding.ActivityFavoriteBinding
import com.example.submission2.helper.ViewModelFactory
import com.example.submission2.model.User
import com.example.submission2.ui.detailUser.DetailUserActivity
import com.example.submission2.ui.detailUser.DetailUserActivity.Companion.EXTRA_USER
import com.example.submission2.ui.favorite.FavoriteActivity
import com.example.submission2.ui.favorite.FavoriteViewModel

class DetailFavoriteActivity : AppCompatActivity() {

    private var _activityDetailFavoriteBinding: ActivityDetailFavoriteBinding? = null
    private val binding get() = _activityDetailFavoriteBinding

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_favorite)

        _activityDetailFavoriteBinding = ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.page_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteViewModel = obtainViewModel(this@DetailFavoriteActivity)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as Favorite;
        binding?.txtName?.text = user.login
        binding?.txtUsername?.text = getString(R.string.username_template, user.login)
        binding?.txtScore?.text = user.score
        binding?.txtUrl?.text = user.url
        binding?.txtDate?.text = user.date

        binding?.imgPhoto?.let {
            Glide.with(this)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(55, 55))
                .into(it)
        }

        binding?.btnDelete?.setOnClickListener{
            showAlertDialog(ALERT_DIALOG_DELETE, user)
        }
        Log.e("user", user.toString())
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
                 finish()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}