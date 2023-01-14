package com.repopliz.a7minuteworkout

import java.util.*

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks =
            ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, true)
        val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.ic_wall_sit, false, false)
        val pushUp = ExerciseModel(3, "Push Up", R.drawable.ic_push_up, false, false)
        val abdominalCrunch =
            ExerciseModel(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        val stepUpOnChair =
            ExerciseModel(5, "Step Up On Chair", R.drawable.ic_step_up_onto_chair, false, false)
        val squat = ExerciseModel(6, "Squat", R.drawable.ic_squat, false, false)
        val tricepsDipOnChair = ExerciseModel(
            7,
            "Triceps Dip On Chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        val plank = ExerciseModel(8, "Plank", R.drawable.ic_plank, false, false)
        val highKneesRunningInPlace = ExerciseModel(
            9,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        val lunges = ExerciseModel(10, "Lunges", R.drawable.ic_lunge, false, false)
        val pushUpAndRotation = ExerciseModel(
            11,
            "Push Up And Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        val sidePlank = ExerciseModel(12, "Side Plank", R.drawable.ic_side_plank, false, false)

        exerciseList.add(jumpingJacks)
        exerciseList.add(wallSit)
        exerciseList.add(pushUp)
        exerciseList.add(abdominalCrunch)
        exerciseList.add(stepUpOnChair)
        exerciseList.add(squat)
        exerciseList.add(tricepsDipOnChair)
        exerciseList.add(plank)
        exerciseList.add(highKneesRunningInPlace)
        exerciseList.add(lunges)
        exerciseList.add(pushUpAndRotation)
        exerciseList.add(sidePlank)


        return exerciseList
    }
}