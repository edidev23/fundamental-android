package com.example.submission2.ui.detailUser

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.R
import com.example.submission2.database.Favorite
import com.example.submission2.databinding.ActivityDetailUserBinding
import com.example.submission2.helper.DateHelper
import com.example.submission2.helper.ViewModelFactory
import com.example.submission2.model.User
import com.example.submission2.model.UserDetailResponse
import com.example.submission2.ui.favorite.FavoriteActivity
import com.example.submission2.ui.favorite.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.page_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        favoriteViewModel = obtainViewModel(this@DetailUserActivity)
        favoriteViewModel.getDetail(user.login).observe(this, { res -> checkFavorite(res, user) })

        detailViewModel.getDetailUsers(user.login)

        detailViewModel.detailUser.observe(this, { res -> setDetailUser(res) })
        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, user.login)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


    }

    private fun checkFavorite(favorite: Favorite?, user: User) {

        if(favorite != null) {
            binding.fabAdd.imageTintList = ContextCompat.getColorStateList(this, R.color.red)
        }

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {

                if (favorite == null) {
                    // add
                    val favorite = Favorite()
                    favorite.login = user.login
                    favorite.avatarUrl = user.avatarUrl
                    favorite.score = user.score
                    favorite.url = user.url
                    favorite.idAPI = user.id
                    favorite.date = DateHelper.getCurrentDate()

                    favoriteViewModel.insert(favorite)

                    showToast(getString(R.string.added))
                    binding.fabAdd.imageTintList = ContextCompat.getColorStateList(this, R.color.red)
                } else {
                    // delete
                    showAlertDialog(ALERT_DIALOG_DELETE, favorite)
                }
            }
        }
    }

    private fun showAlertDialog(type: Int, user: Favorite) {
        val isDialogClose = type == FavoriteActivity.ALERT_DIALOG_CLOSE
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

                    binding.fabAdd.imageTintList = ContextCompat.getColorStateList(context, R.color.blue)
                }
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDetailUser(user: UserDetailResponse) {

        binding.txtName.text = user.name
        binding.txtUsername.text = getString(R.string.username_template, user.login)
        binding.txtLocation.text = user.location
        binding.txtCompany.text = user.company
        binding.txtRepository.text = user.reposUrl

        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(55, 55))
            .into(binding.imgPhoto)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val ALERT_DIALOG_DELETE = 20

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }
}
