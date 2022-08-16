package com.oliver.crypto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.oliver.crypto.R
import com.oliver.crypto.databinding.CurrencyItemLayoutBinding
import com.oliver.crypto.fragments.HomeFragmentDirections
import com.oliver.crypto.fragments.MarketFragmentDirections
import com.oliver.crypto.fragments.WatchlistFragmentDirections
import com.oliver.crypto.models.CryptoCurrency
import com.squareup.picasso.Picasso

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>, var type: String) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    inner class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CurrencyItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(
            LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false)
        )
    }

    fun updateData(dataItem: List<CryptoCurrency>) {
        list = dataItem
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]

        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol
        holder.binding.currencyPriceTextView.text =
            "${String.format("$ %.07f", item.quotes[0].price)}"


        Picasso.get()
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png")
            .placeholder(R.drawable.spinner).into(holder.binding.currencyImageView)

        Picasso.get()
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png")
            .placeholder(R.drawable.spinner).into(holder.binding.currencyChartImageView)

        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.currencyChangeTextView.text =
                "+ ${String.format("%.3f", item.quotes[0].percentChange24h)} %"
        } else {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.currencyChangeTextView.text =
                "${String.format("%.3f", item.quotes[0].percentChange24h)} %"
        }

        holder.itemView.setOnClickListener {
            if (type == "home") {
                findNavController(it).navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
                )
            } else if (type == "market") {
                findNavController(it).navigate(
                    MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item)
                )
            } else if (type == "watchfragment") {
                findNavController(it).navigate(
                    WatchlistFragmentDirections.actionWatchlistFragmentToDetailsFragment(item)
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}