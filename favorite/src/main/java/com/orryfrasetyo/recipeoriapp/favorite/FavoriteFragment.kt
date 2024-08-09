package com.orryfrasetyo.recipeoriapp.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.orryfrasetyo.recipeoriapp.core.domain.model.Recipe
import com.orryfrasetyo.recipeoriapp.core.ui.RecipeAdapter
import com.orryfrasetyo.recipeoriapp.di.FavoriteModuleDependencies
import com.orryfrasetyo.recipeoriapp.favorite.databinding.FragmentFavoriteBinding
import com.orryfrasetyo.recipeoriapp.favorite.di.DaggerFavoriteComponent
import com.orryfrasetyo.recipeoriapp.favorite.viewmodel.FavoriteViewModel
import com.orryfrasetyo.recipeoriapp.favorite.viewmodel.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecipe.layoutManager = layoutManager
        binding.rvRecipe.setHasFixedSize(true)

        favoriteViewModel.getFavoriteRecipe().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.rvRecipe.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.rvRecipe.visibility = View.VISIBLE
                binding.tvNoData.visibility = View.GONE
                setData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(it: List<Recipe>) {
        val adapter = RecipeAdapter(it)
        adapter.setOnItemClickCallback(object : RecipeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Recipe) {
                val action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(
                    data.recipeId
                )
                findNavController().navigate(action)
            }
        })
        binding.rvRecipe.adapter = adapter
    }

}















