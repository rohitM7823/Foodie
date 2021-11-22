package com.example.foodie.bindingadapters

import android.graphics.BlendModeColorFilter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodie.R

class RecipesCardViewLayout {
    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfFavorites")
        @JvmStatic
        fun setNumberOfFavorites(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("vegOrNonVeg")
        @JvmStatic
        fun vegOrNonVeg(imageView: ImageView, veganOrNot: Boolean){
            if (!veganOrNot){
                imageView.setColorFilter(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.teal_700
                    )
                )
            }
        }


    }
}