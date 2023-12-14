package com.example.GithubApp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GithubApp.databinding.FragmentFollowBinding
import com.example.GithubApp.ui.adapter.GithubUserAdapter
import com.example.GithubApp.ui.viewmodel.FollowsViewModel
import com.example.GithubApp.ui.viewmodel.ViewModelFactory


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: GithubUserAdapter
    private val followsViewModel: FollowsViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val username = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_LOGIN)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 200)

        binding.rvGithub.layoutManager = layoutManager
        adapter = GithubUserAdapter {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN,it.login)
            startActivity(intent)
        }
        binding.rvGithub.adapter = adapter
        followsViewModel.IsEmpty.observe(viewLifecycleOwner){
            binding.tvEmpty.isVisible = it
        }
        followsViewModel.IsLoad.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
        if(index==1){
            followsViewModel.getFollowers(username.toString())
            followsViewModel.followerList.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }else{
            followsViewModel.getFollowings(username.toString())
            followsViewModel.followingList.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}