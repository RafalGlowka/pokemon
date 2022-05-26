package com.glowka.rafal.pokemon.presentation.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.glowka.rafal.pokemon.presentation.BR
import com.glowka.rafal.pokemon.presentation.binding.components.ListBindingModel

class ViewHolderForBinding<VIEW_BINDING : ViewDataBinding, ITEM_MODEL>(
  val binding: VIEW_BINDING,
  val variableId: Int = BR.itemModel,
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(viewModelItem: ITEM_MODEL) {
    bindItem(binding, viewModelItem)
  }

  private fun bindItem(binding: ViewDataBinding, viewModelItem: ITEM_MODEL) {
    binding.setVariable(variableId, viewModelItem)
  }
}

class AdapterForBinding<VIEW_BINDING : ViewDataBinding, ITEM_MODEL>(
  val context: Context,
  var items: List<ITEM_MODEL>,
  val layoutResId: Int,
) : RecyclerView.Adapter<ViewHolderForBinding<VIEW_BINDING, ITEM_MODEL>>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolderForBinding<VIEW_BINDING, ITEM_MODEL> {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<VIEW_BINDING>(inflater, layoutResId, parent, false)
    return ViewHolderForBinding(binding)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(
    holder: ViewHolderForBinding<VIEW_BINDING, ITEM_MODEL>,
    position: Int
  ) {
    holder.bind(items[position])
  }
}

fun <VIEW_BINDING : ViewDataBinding, ITEM_MODEL> ListBindingModel<ITEM_MODEL>.createAdapter(
  context: Context,
  lifecycleOwner: LifecycleOwner,
  layoutResId: Int,
): AdapterForBinding<VIEW_BINDING, ITEM_MODEL> {
  val adapter = AdapterForBinding<VIEW_BINDING, ITEM_MODEL>(
    context = context,
    layoutResId = layoutResId,
    items = items.value ?: emptyList()
  )
  items.observe(lifecycleOwner) { itemModels ->
    adapter.items = itemModels
    adapter.notifyDataSetChanged()
  }

  return adapter
}