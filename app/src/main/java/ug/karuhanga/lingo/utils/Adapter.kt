package ug.karuhanga.lingo.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ug.karuhanga.lingo.R

class Adapter(var items: MutableList<Listable>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.result, parent, false)
        return ViewHolder(view)
    }

    fun appendData(items: List<Listable>){
        var index = this.items.size
        var length = items.size
        this.items.addAll(items)
        notifyItemRangeInserted(index, length)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(items.get(position))
    }

    fun clearData() {
        this.items.clear()
        notifyDataSetChanged()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var language1TextView: TextView = itemView.findViewById(R.id.text_view_result_big_text)
    var language2TextView: TextView = itemView.findViewById(R.id.text_view_result_small_text)

    fun update(item: Listable){
        language1TextView.text = item.getText1()
        language2TextView.text = item.getText2()
    }
}