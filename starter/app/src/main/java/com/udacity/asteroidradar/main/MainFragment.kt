package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.domain.AsteroidFilter

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private var viewModelAdapter: AsteroidAdapter? = null
    private val viewModelFactory: MainViewModelFactory by lazy {
        MainViewModelFactory(
            requireActivity().application, AsteroidRepository(AsteroidDatabase.getInstance(requireContext())))
    }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        setupAdapter()
        setupListeners()
        setupObserver()
        return binding.root
    }


    private fun setupAdapter() {
        viewModelAdapter = AsteroidAdapter(AsteroidClick {
            viewModel.onAsteroidClicked(it)
        })
        binding.asteroidRecycler.adapter = viewModelAdapter
    }

    private fun setupListeners() {

    }

    private fun setupObserver() {

        viewModel.asteroidList.observe(viewLifecycleOwner) { asteroids ->
            asteroids?.let {
                viewModelAdapter?.submitList(it)
            }
        }

        viewModel.navigateToDetails.observe(viewLifecycleOwner) { asteroid ->
            asteroid?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.navigatedToDetails()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_today_menu -> {
                    AsteroidFilter.DAY
                }
                R.id.show_week_menu -> {
                    AsteroidFilter.WEEK
                }
                else -> {
                    AsteroidFilter.SAVED
                }
            }
        )
        return true
    }
}
