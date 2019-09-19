package sola.martin.invariantapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel
    private val newItemActivityRequestCode = 1
    private lateinit var adedItem: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ItemAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        itemViewModel.allItems.observe(this, Observer { items ->
            items?.let { adapter.setItems(items) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewItemActivity::class.java)
            startActivityForResult(intent, newItemActivityRequestCode)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                p0: RecyclerView,
                p1: RecyclerView.ViewHolder,
                p2: RecyclerView.ViewHolder
            ): Boolean {
                (adapter as ItemAdapter).onDragAndDrop(p1.adapterPosition, p2.adapterPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                var position = viewHolder.getAdapterPosition()
                val item = adapter.getItemAtPosition(position)

                   itemViewModel.deleteItem(item)


            }


        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let { data ->

                val item = Item(
                    data.getStringExtra(NewItemActivity.EXTRA_TITLE),
                    data.getStringExtra(NewItemActivity.EXTRA_START).toLong(),
                    data.getStringExtra(NewItemActivity.EXTRA_END).toLong()
                )

                itemViewModel.insert(item)
            }


        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }


    }

}
