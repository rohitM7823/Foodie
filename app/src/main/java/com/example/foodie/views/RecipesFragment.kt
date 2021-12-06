package com.example.foodie.views

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.R
import com.example.foodie.adapters.RecipesAdapter
import com.example.foodie.databinding.FragmentRecipesBinding
import com.example.foodie.models.utill.ConnectionResult
import com.example.foodie.models.utill.Constaints.Companion.API_KEY
import com.example.foodie.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var recipesBinding: FragmentRecipesBinding
    private val rAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipesBinding = FragmentRecipesBinding.bind(
            inflater.inflate(R.layout.fragment_recipes, container, false)
        )

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // This will initialize shimmer effect
        // recipesBinding.recipesShimmerRecyclerView.showShimmer()

        setupRecyclerView()
        requestData()

        return recipesBinding.root
    }

    private fun setupRecyclerView() {
        recipesBinding.recipesShimmerRecyclerView.adapter = rAdapter
        recipesBinding.recipesShimmerRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        showShimmerEffect()
    }


    private fun requestData() {
        mainViewModel.getRecipes(requiredQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is ConnectionResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { rAdapter.setData(it) }
                }
                is ConnectionResult.Failure -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(), Toast.LENGTH_LONG
                    ).show()
                }
                is ConnectionResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun showShimmerEffect() {
        recipesBinding.recipesShimmerRecyclerView.showShimmer()
    }

    private fun requiredQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    private fun hideShimmerEffect() {
        recipesBinding.recipesShimmerRecyclerView.hideShimmer()
    }

}