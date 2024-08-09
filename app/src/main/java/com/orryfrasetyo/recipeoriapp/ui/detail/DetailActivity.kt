package com.orryfrasetyo.recipeoriapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.orryfrasetyo.recipeoriapp.R
import com.orryfrasetyo.recipeoriapp.core.data.Resource
import com.orryfrasetyo.recipeoriapp.core.domain.model.Recipe
import com.orryfrasetyo.recipeoriapp.core.utils.htmlParser
import com.orryfrasetyo.recipeoriapp.core.utils.setImageViewTint
import com.orryfrasetyo.recipeoriapp.core.utils.setTextViewColor
import com.orryfrasetyo.recipeoriapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val recipeId: Int = args.id

        detailViewModel.getDetailRecipe(recipeId).observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBarDetail.visibility = View.VISIBLE
                    binding.frameLayout.visibility = View.GONE
                    binding.constraintLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.viewError.root.visibility = View.GONE
                    binding.progressBarDetail.visibility = View.GONE
                    binding.frameLayout.visibility = View.VISIBLE
                    binding.constraintLayout.visibility = View.VISIBLE
                    setData(it.data)
                }
                is Resource.Error -> {
                    binding.viewError.root.visibility = View.VISIBLE
                    binding.viewError.tvError.text =
                        it.message ?: getString(R.string.something_wrong)
                    binding.progressBarDetail.visibility = View.GONE
                    binding.clDetail.visibility = View.GONE
                    binding.frameLayout.visibility = View.GONE
                }
                else -> Unit
            }
        }

        binding.fabBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setData(data: Recipe?) {
        data?.let {
            Glide.with(this)
                .load(data.image)
                .into(binding.ivDetail)
            binding.apply {
                tvDetailTitle.text = data.title
                setImageViewTint(applicationContext, ivDetailHealthy, data.veryHealthy.toString())
                setTextViewColor(applicationContext, tvDetailHealthy, data.veryHealthy.toString())
                setImageViewTint(applicationContext, ivDetailVegetarian, data.vegetarian.toString())
                setTextViewColor(applicationContext, tvDetailVegetarian, data.vegetarian.toString())
                setImageViewTint(applicationContext, ivDetailVegan, data.vegan.toString())
                setTextViewColor(applicationContext, tvDetailVegan, data.vegan.toString())
                setImageViewTint(applicationContext, ivDetailCheap, data.cheap.toString())
                setTextViewColor(applicationContext, tvDetailCheap, data.cheap.toString())
                setImageViewTint(applicationContext, ivDetailDairyFree, data.dairyFree.toString())
                setTextViewColor(applicationContext, tvDetailDairyFree, data.dairyFree.toString())
                setImageViewTint(applicationContext, ivDetailGlutenFree, data.glutenFree.toString())
                setTextViewColor(applicationContext, tvDetailGlutenFree, data.glutenFree.toString())
                tvDetailDescription.text = htmlParser(data.summary)
            }
            detailViewModel.getRecipeById(data.recipeId).observe(this@DetailActivity) {
                statusFavorite = it.isFavorite == true
                setStatusFavorite(statusFavorite)
            }
            fabFavoriteClicked(data)
        }
    }

    private fun fabFavoriteClicked(data: Recipe) {
        binding.fabFavorite.setOnClickListener {
            statusFavorite = !statusFavorite
            detailViewModel.setFavoriteRecipe(data, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_no_bookmark
                )
            )
        }
    }
}










