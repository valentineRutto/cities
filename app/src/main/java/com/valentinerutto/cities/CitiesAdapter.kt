package com.valentinerutto.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valentinerutto.cities.databinding.RowCityBinding
import com.valentinerutto.cities.local.CityEntity

class CitiesAdapter : ListAdapter<CityEntity, CitiesAdapter.CityViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val cityResponse = getItem(position)
        holder.bind(cityResponse)
    }

    class CityViewHolder(private val binding: RowCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(city: CityEntity) {
            binding.cityNameTextView.text = city.name
        }
    }

    companion object {
        fun from(parent: ViewGroup): CityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RowCityBinding.inflate(layoutInflater, parent, false)
            return CityViewHolder(binding)
        }

        val diff = object : ItemCallback<CityEntity>() {
            override fun areItemsTheSame(
                oldItem: CityEntity,
                newItem: CityEntity
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CityEntity,
                newItem: CityEntity
            ): Boolean = oldItem.id == newItem.id
        }
    }
}
