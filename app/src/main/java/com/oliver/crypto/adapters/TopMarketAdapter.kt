package com.oliver.crypto.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oliver.crypto.R
import com.oliver.crypto.databinding.TopCurrencyLayoutBinding
import com.oliver.crypto.models.CryptoCurrency
import com.squareup.picasso.Picasso

class TopMarketAdapter(var context: Context, var list: List<CryptoCurrency>) :
    RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    inner class TopMarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Here is the error
        var binding = TopCurrencyLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        return TopMarketViewHolder(
            LayoutInflater.from(context).inflate(R.layout.top_currency_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position]

        holder.binding.topCurrencyNameTextView.text = item.name
        Picasso.get()
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png")
            .placeholder(R.drawable.spinner).into(holder.binding.topCurrencyImageView)

        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.topCurrencyChangeTextView.text =
                "+ ${String.format("%.3f", item.quotes[0].percentChange24h)} %"
        } else {
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.topCurrencyChangeTextView.text =
                "${String.format("%.3f", item.quotes[0].percentChange24h)} %"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}