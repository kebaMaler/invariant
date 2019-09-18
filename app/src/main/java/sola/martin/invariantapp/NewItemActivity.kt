package sola.martin.invariantapp

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class NewItemActivity : AppCompatActivity(){
    private lateinit var editStartTime: EditText
    private lateinit var editEndTime: EditText
    private lateinit var titleItme: EditText
    private lateinit var saveBtn: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        editStartTime = findViewById(R.id.start_time_editText)
        editEndTime = findViewById(R.id.endt_time_editText)
        titleItme = findViewById(R.id.itemTitle_editText)
        saveBtn= findViewById(R.id.save_btn)

       saveBtn.setOnClickListener {
           val replyIntent = Intent()

           if (TextUtils.isEmpty(editStartTime.text) or TextUtils.isEmpty(editEndTime.text)) {
               setResult(Activity.RESULT_CANCELED, replyIntent)
           } else {
               val  start = editStartTime.text.toString()
               val  end = editEndTime.text.toString()
               val title = titleItme.text.toString()

               replyIntent.putExtra(EXTRA_START, start)
               replyIntent.putExtra(EXTRA_END, end)
               replyIntent.putExtra(EXTRA_TITLE, title)
               setResult(Activity.RESULT_OK, replyIntent)
           }
           finish()

       }
    }

    companion object {
        const val EXTRA_START = "start"
        const val EXTRA_END = "end"
        const val EXTRA_TITLE = "title"
    }
}
