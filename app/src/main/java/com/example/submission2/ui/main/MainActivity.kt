package com.example.submission2.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.ui.detailUser.DetailUserActivity
import com.example.submission2.R
import com.example.submission2.adapter.UserGithubAdapter
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.helper.SettingModelFactory
import com.example.submission2.helper.SettingPreferences
import com.example.submission2.model.User
import com.example.submission2.model.UserResponse
import com.example.submission2.ui.favorite.FavoriteActivity
import com.example.submission2.ui.main.MainViewModel.Companion.DEFAULT_USERNAME

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var settingViewModel: SettingViewModel
    private var themeDark : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUser.observe(this, { user -> setUserGithub(user) })
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        mainViewModel.isError.observe(this, {
            Toast.makeText(this, getString(R.string.gagal_muat), Toast.LENGTH_LONG).show()
        })
        binding.rvUsers.setHasFixedSize(true)

        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, SettingModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    themeDark = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    themeDark = false
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

    }

    private fun setUserGithub(user: UserResponse) {
        if (user.items.isEmpty()) {
            binding.resultKosong.visibility = View.VISIBLE
            Toast.makeText(this, "Data tidak ditemukan !", Toast.LENGTH_SHORT).show()
        } else {
            binding.resultKosong.visibility = View.GONE
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUser = UserGithubAdapter(user.items)
        binding.rvUsers.adapter = listUser

        listUser.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val detailUserIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        detailUserIntent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(detailUserIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val titleMode = menu.findItem(R.id.menu2)
        if(themeDark) {
            titleMode.setTitle(R.string.normal_mode)
        } else {
            titleMode.setTitle(R.string.dark_mode)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUsers(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu1 -> {
                mainViewModel.findUsers(DEFAULT_USERNAME)
                Toast.makeText(this@MainActivity, "Reload data berhasil", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu2 -> {
                if(themeDark) {
                    settingViewModel.saveThemeSetting(false)
                } else {
                    settingViewModel.saveThemeSetting(true)
                }
                true
            }
            R.id.favorite -> {
                val favoriteIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
                true
            }

            else -> true
        }
    }

}