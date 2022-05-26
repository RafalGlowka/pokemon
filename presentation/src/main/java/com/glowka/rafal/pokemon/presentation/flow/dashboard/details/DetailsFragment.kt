package com.glowka.rafal.pokemon.presentation.flow.dashboard.details

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glowka.rafal.pokemon.presentation.R
import com.glowka.rafal.pokemon.presentation.architecture.BaseFragment
import com.glowka.rafal.pokemon.presentation.binding.ImageModel
import com.glowka.rafal.pokemon.presentation.databinding.DetailsFragmentBinding
import com.glowka.rafal.pokemon.presentation.databinding.DetailsGalleryItemBinding
import com.glowka.rafal.pokemon.presentation.utils.createAdapter

/**
 * Created by Rafal on 13.04.2021.
 */

class DetailsFragment : BaseFragment<DetailsViewModelToViewInterface, DetailsFragmentBinding>() {

  override val layoutResId = R.layout.details_fragment

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewBinding.gallery.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    viewBinding.gallery.adapter = viewModel.images.createAdapter<DetailsGalleryItemBinding, ImageModel>(
      context = requireContext(),
      lifecycleOwner = viewLifecycleOwner,
      layoutResId = R.layout.details_gallery_item,
    )
  }
}