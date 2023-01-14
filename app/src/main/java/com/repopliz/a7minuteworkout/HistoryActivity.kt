package com.repopliz.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.repopliz.a7minuteworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO(Step 6 : Adding the History Screen Activity.)
class HistoryActivity : AppCompatActivity() {
    //Todo 7 create a binding for the layout
    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//Todo  8 inflate the layout
        binding = ActivityHistoryBinding.inflate(layoutInflater)
//Todo 9 bind the layout to this activity
        setContentView(binding?.root)

//TODO(Step 11 : Setting up the action bar in the History Screen Activity and
// adding a back arrow button and click event for it.)
// START
        setSupportActionBar(binding?.toolbarHistoryActivity)

        val actionbar = supportActionBar//actionbar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true) //set back button
            actionbar.title = "HISTORY" // Setting a title in the action bar.
        }

        val dao = (application as WorkOutApp).db.historyDao()
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }


        getAllCompletedDates(dao)


// END
    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.getAllData().collect { allCompletedDatesList ->
                if (allCompletedDatesList.isNotEmpty()) {

                    val dates = ArrayList<HistoryEntity>()
                    for (date in allCompletedDatesList) {
                        dates.add(date)
                    }
                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = historyAdapter
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
//Todo 12 reset the binding to null to avoid memory leak
        binding = null
    }
}