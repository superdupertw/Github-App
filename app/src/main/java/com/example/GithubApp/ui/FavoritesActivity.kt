package com.example.GithubApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GithubApp.databinding.ActivityFavoriteBinding
import com.example.GithubApp.ui.adapter.FavoritesUserAdapter
import com.example.GithubApp.ui.viewmodel.FavoritesViewModel
import com.example.GithubApp.ui.viewmodel.ViewModelFactory

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoritesUserAdapter
    private val favoritesViewModel: FavoritesViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        adapter = FavoritesUserAdapter({
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN,it.username)
            startActivity(intent)
        },{
            favoritesViewModel.deleteFavorite(it.username.toString())
            favoritesViewModel.getAllFavorite().observe(this){ result ->
                adapter.submitList(result)
            }
        })
        binding.rvGithub.adapter = adapter
        favoritesViewModel.getAllFavorite().observe(this){ result ->
            binding.progressBar.isGone = true
            adapter.submitList(result)
            if(result.isEmpty()){
                binding.tvEmpty.isVisible = true
            }
        }
    }
}