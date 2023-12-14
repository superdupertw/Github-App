package com.example.GithubApp.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GithubApp.R
import com.example.GithubApp.databinding.ActivityMainBinding
import com.example.GithubApp.ui.adapter.GithubUserAdapter
import com.example.GithubApp.ui.viewmodel.MainViewModel
import com.example.GithubApp.ui.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GithubUserAdapter
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        adapter = GithubUserAdapter{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN,it.login)
            startActivity(intent)
        }
        binding.rvGithub.adapter = adapter
        mainViewModel.UserList.observe(this){
            adapter.submitList(it)
        }
        mainViewModel.IsEmpty.observe(this){
            binding.tvEmpty.isVisible = it
        }
        mainViewModel.isLoad.observe(this){
            binding.progressBar.isVisible = it
        }
        mainViewModel.getThemeforSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    mainViewModel.getUser(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_user -> {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intentSetting = Intent(this, SettingActivity::class.java)
                startActivity(intentSetting)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}