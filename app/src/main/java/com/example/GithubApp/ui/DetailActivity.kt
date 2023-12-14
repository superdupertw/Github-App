package com.example.GithubApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.GithubApp.R
import com.example.GithubApp.data.local.entity.UserEntity
import com.example.GithubApp.databinding.ActivityDetailBinding
import com.example.GithubApp.ui.adapter.SectionsPagerAdapter
import com.example.GithubApp.ui.viewmodel.DetailsViewModel
import com.example.GithubApp.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private val detailsViewModel: DetailsViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityDetailBinding
    private var user: UserEntity = UserEntity(0,null,null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_LOGIN)
        supportActionBar?.title = "Detail"
        detailsViewModel.getDetailUser(username.toString())
        detailsViewModel.entityUser.observe(this){
            user = it
        }
        detailsViewModel.isLoad.observe(this){
            binding.progressBar.isVisible = it
        }
        detailsViewModel.userDetail.observe(this){
            with(binding){
                progressBar.isGone = true
                if(it!=null){
                    tvNumberFollowers.text = it.followers.toString()
                    tvNumberFollowings.text = it.following.toString()
                    tvFullName.text = it.name.toString()
                    tvUsername.text = it.login.toString()
                    Glide.with(binding.root)
                        .load(it.avatarUrl)
                        .into(binding.ivUser)
                        .clearOnDetach()
                }
            }
        }
        detailsViewModel.isFavorite(username.toString()).observe(this){
            if (it) {
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(binding.btnFavorite.context, R.drawable.baseline_favorite_after_24))
                binding.btnFavorite.setOnClickListener{
                    detailsViewModel.deleteFavorite(username.toString())
                }
            } else {
                binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(binding.btnFavorite.context, R.drawable.baseline_favorite_before_24))
                binding.btnFavorite.setOnClickListener{
                    detailsViewModel.addFavorite(user)
                }
            }
        }

        val viewPager = binding.viewPager
        val tabs = binding.tabs
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
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
    companion object{
        const val EXTRA_LOGIN="login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}