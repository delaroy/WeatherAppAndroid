package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.CityItemsBinding
import kotlin.collections.ArrayList

class WeatherAppAdapter(private val recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private val TAG: String = "AppDebug"
    var items: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAppViewHolder {
        return WeatherAppViewHolder(
            CityItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is WeatherAppViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(itemList: List<String>){
        items = itemList as MutableList<String>
        notifyDataSetChanged()
    }


    inner class WeatherAppViewHolder(private val binding: CityItemsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: String?) {

            binding.let {

                it.root.setOnClickListener {
                    recyclerViewClickListener.clickOnItem(data!!)
                }

                binding.city.text = data?: ""

            }
        }
    }

    interface RecyclerViewClickListener {
        fun clickOnItem(data: String)
    }

}
