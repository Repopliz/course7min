package com.repopliz.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.repopliz.a7minuteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

var finishBinding: ActivityFinishBinding? = null

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finishBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(finishBinding?.root)

        finishBinding?.btnFinish?.setOnClickListener {
            val intent = Intent(this@FinishActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)

    }


    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("date: ", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("date: ", "Added")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        finishBinding = null
    }


}