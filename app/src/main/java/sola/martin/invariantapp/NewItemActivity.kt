package sola.martin.invariantapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.recyclerview_item.*


class NewItemActivity : AppCompatActivity(){
    private  val TAG = "New Item Activity:"
    private lateinit var editStartTime: EditText
    private lateinit var editEndTime: EditText
    private lateinit var editTitleItem: EditText
    private lateinit var saveBtn: Button

    var newItem: Item? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        editStartTime = findViewById(R.id.start_time_editText)
        editEndTime = findViewById(R.id.endt_time_editText)
        editTitleItem = findViewById(R.id.itemTitle_editText)
        saveBtn= findViewById(R.id.save_btn)

        newItem = intent.getParcelableExtra(NewItemActivity.EXTRA_EDIT_ITEM)
        if (newItem == null){

            Log.d(TAG,"item is $newItem")
        }else{
            editTitleItem.setText(newItem!!.title)
            editStartTime.setText(newItem!!.startLong.toString())
            editEndTime.setText(newItem!!.endLong.toString())

        }

       saveBtn.setOnClickListener {
           val replyIntent = Intent()

           if (TextUtils.isEmpty(editStartTime.text) or TextUtils.isEmpty(editEndTime.text)) {

                   setResult(Activity.RESULT_CANCELED, replyIntent)

           } else {

               val  start = editStartTime.text.toString().toLong()
               val  end = editEndTime.text.toString().toLong()
               val title = editTitleItem.text.toString()
               newItem = Item(title,start, end)

               replyIntent.let {
                 it.putExtra(EXTRA_ITEM,newItem)
               }

               setResult(Activity.RESULT_OK, replyIntent)
           }

           Log.d(TAG, newItem.toString())
           finish()
       }
    }

    companion object {
        const val EXTRA_ITEM = "extra_item"
        const val EXTRA_EDIT_ITEM = "extra_edit_item"

    }
}
