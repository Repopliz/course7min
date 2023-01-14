package com.repopliz.a7minuteworkout

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.repopliz.a7minuteworkout.databinding.ActivityExerciseBinding
import com.repopliz.a7minuteworkout.databinding.DialogCustomBackConfrimationBinding
import java.lang.Exception
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var restTimerDuration: Long = 10
    private var exerciseTimerDuration: Long = 30

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var restVisible = true
    private var exerciseVisible = false

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = 0

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)


        exerciseList = Constants.defaultExerciseList()
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter

        tts = TextToSpeech(this, this)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        setVisibility()
        setupRestView()

    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfrimationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    //Init for tts obj
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = binding?.progressBar?.progress.toString()
            }

            override fun onFinish() {
                restVisible = false
                exerciseVisible = true
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView() {

        try {
            val soundURI = Uri.parse("android.resource://com.repopliz.a7minuteworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.exerciseNameInRestView?.text = exerciseList?.get(currentExercisePosition)?.getName().toString()
        setRestProgressBar()
        setVisibility()

        try {
            val soundURI = Uri.parse("android.resource://com.repopliz.a7minuteworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setupExerciseView() {
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        updateExerciseViews(currentExercisePosition)
        setExerciseProgressBar()
        setVisibility()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++

                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = binding?.progressBarExercise?.progress.toString()
            }

            override fun onFinish() {
                restVisible = true
                exerciseVisible = false
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                if (workoutDone()) {
                    Log.i(TAG, "I GOT IN KILLED ME")
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                if (!workoutDone()) {
                    currentExercisePosition++
                    exerciseList!![currentExercisePosition].setIsSelected(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupRestView()
                }
            }
        }.start()
    }

    private fun setVisibility() {
        if (restVisible) {
            showRestView()
            hideExerciseView()
        }
        if (exerciseVisible) {
            showExerciseView()
            hideRestView()
            updateExerciseViews(currentExercisePosition)
        }
    }

    private fun updateExerciseViews(position: Int) {
        binding?.exerciseNameInExerciseView?.text =
            exerciseList?.get(position)?.getName().toString()
        binding?.exerciseImage?.setImageResource(exerciseList!![position].getImage())
        speak(exerciseList?.get(position)?.getName()!!)
    }


    private fun showExerciseView() {
        binding?.flProgressBarExercise?.visibility = View.VISIBLE
        binding?.progressBarExercise?.visibility = View.VISIBLE
        binding?.tvTimerExercise?.visibility = View.VISIBLE
        binding?.exerciseImage?.visibility = View.VISIBLE
        binding?.exerciseNameInExerciseView?.visibility = View.VISIBLE

    }

    private fun hideExerciseView() {
        binding?.flProgressBarExercise?.visibility = View.INVISIBLE
        binding?.progressBarExercise?.visibility = View.INVISIBLE
        binding?.tvTimerExercise?.visibility = View.INVISIBLE
        binding?.exerciseImage?.visibility = View.INVISIBLE
        binding?.exerciseNameInExerciseView?.visibility = View.INVISIBLE

    }

    private fun showRestView() {
        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.tvTimer?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.exerciseNameInRestView?.visibility = View.VISIBLE
        binding?.tvUpcoming?.visibility = View.VISIBLE
    }

    private fun hideRestView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.progressBar?.visibility = View.INVISIBLE
        binding?.tvTimer?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.exerciseNameInRestView?.visibility = View.INVISIBLE
        binding?.tvUpcoming?.visibility = View.INVISIBLE
    }

    private fun workoutDone(): Boolean {
        if (currentExercisePosition == exerciseList!!.size - 1) {
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        if (player != null) {
            player!!.stop()
        }
        binding = null
    }


}