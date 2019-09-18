package sola.martin.invariantapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class ItemAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items = emptyList<Item>() // Cached copy of Items

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.itemTitle_textView)
        val startTimeItemView: TextView = itemView.findViewById(R.id.startTime_textView)
        val endTimeItemView: TextView = itemView.findViewById(R.id.endTime_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = items[position]

        holder.titleItemView.text = current.title
        holder.endTimeItemView.text = convertLongToTime(current.endLong)
        holder.startTimeItemView.text = convertLongToTime(current.startLong)
    }

    internal fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }


fun convertLongToTime(time: Long): String{
    val date = Date(time)
    val format= SimpleDateFormat("HH:MM:SS")

    return format.format(date)
}



    override fun getItemCount() = items.size
}