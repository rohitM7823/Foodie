package com.example.foodie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.databinding.RecipesRowCardViewBinding
import com.example.foodie.models.source.Recipes
import com.example.foodie.models.source.Result
import com.example.foodie.models.utill.RecipesDiffUtill

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesHolder>() {

    private var recipes = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
        return RecipesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipesHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.binding(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    class RecipesHolder(private val bind: RecipesRowCardViewBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun binding(result: Result) {
            bind.result = result
            bind.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipesHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowCardViewBinding.inflate(inflater, parent, false)
                return RecipesHolder(binding)
            }
        }

    }

    fun setData(newRecipe: Recipes) {
        val recipesDiffUtil = RecipesDiffUtill(recipes, newRecipe.results)
        val diff = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newRecipe.results
        diff.dispatchUpdatesTo(this)
    }

}