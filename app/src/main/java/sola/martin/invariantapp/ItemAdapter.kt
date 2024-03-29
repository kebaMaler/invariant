package sola.martin.invariantapp


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.ContextCompat.startActivity
import sola.martin.invariantapp.NewItemActivity.Companion.EXTRA_EDIT_ITEM


class ItemAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val con = context
    private val inflater: LayoutInflater = LayoutInflater.from(con)
    lateinit var items: MutableList<Item>
    private var removedPosition: Int = 0
    lateinit var removedItem: Item
    private var sourcePosition: Int = 0
    private var targetPosition: Int = 0
    private val editItemActivityRequestCode = 1




    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.itemTitle_textView)
        val startTimeItemView: TextView = itemView.findViewById(R.id.startTime_textView)
        val endTimeItemView: TextView = itemView.findViewById(R.id.endTime_textView)
        val editBtn: Button = itemView.findViewById(R.id.editItem_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = items[position]
        current.tag = position

        holder.titleItemView.text = current.title
        holder.endTimeItemView.text = convertLongToTime(current.endLong)
        holder.startTimeItemView.text = convertLongToTime(current.startLong)
        holder.editBtn.setOnClickListener {

            val intent = Intent(con, NewItemActivity::class.java)
            intent.putExtra(EXTRA_EDIT_ITEM, current)
            (con as Activity).startActivityForResult(intent,editItemActivityRequestCode)

        }

    }

    internal fun setItems(items: List<Item>) {
        this.items = items as MutableList<Item>
        notifyDataSetChanged()
    }


fun convertLongToTime(time: Long): String{
    val date = Date(time)
    val format= SimpleDateFormat("HH:MM:SS")

    return format.format(date)
}
    fun onDragAndDrop(p1: Int, p2: Int){
        sourcePosition = p1
        targetPosition = p2
        Collections.swap(items, sourcePosition,targetPosition)
        notifyItemMoved(sourcePosition,targetPosition)
    }



fun getItemAtPosition(position: Int): Item{
 return    items.get(position)
}


    fun removeItemSwipe(position: Int, viewHolder: RecyclerView.ViewHolder) {
        removedItem = items[position]
        removedPosition = position

        items.removeAt(position)
        notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "$removedItem removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
            items.add(removedPosition, removedItem)
            notifyItemInserted(removedPosition)

        }.show()
    }

    fun removeItem(position: Int) {
        removedItem = items[position]
        removedPosition = position

        items.removeAt(position)
        notifyItemRemoved(position)

    }


    override fun getItemCount() = items.size
}