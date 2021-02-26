package com.ericbatemandev.doordashlite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ericbatemandev.doordashlite.R
import com.ericbatemandev.doordashlite.adapter.RestaurantsAdapter
import com.ericbatemandev.doordashlite.databinding.FragmentDiscoverBinding
import com.ericbatemandev.doordashlite.network.Result
import com.ericbatemandev.doordashlite.viewmodel.RestaurantsViewModel
import com.ericbatemandev.doordashlite.widget.VerticalItemDecoration
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment to display a basic list of restaurants
 */
class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RestaurantsViewModel by activityViewModels()
    private val adapter = RestaurantsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.fragment_title)
        bindUI()
    }

    private fun bindUI() {
        binding.rvRestaurants.adapter = adapter
        binding.rvRestaurants.addItemDecoration(VerticalItemDecoration(requireContext()))

        viewModel.restaurantsLiveData.observe(viewLifecycleOwner, { result ->
            showProgress(false)
            when (result) {
                is Result.Success -> {
                    adapter.submitList(result.body.restaurantList)
                }
                is Result.Failure -> {
                    Snackbar.make(requireView(), R.string.oops, Snackbar.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.fetchRestaurantData(requireContext())
        showProgress(true)
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}