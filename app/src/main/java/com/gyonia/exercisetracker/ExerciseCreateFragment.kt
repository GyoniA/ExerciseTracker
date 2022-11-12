package com.gyonia.exercisetracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.gyonia.exercisetracker.databinding.FragmentCreateBinding
import com.gyonia.exercisetracker.model.Exercise
import kotlin.random.Random

class ExerciseCreateFragment : DialogFragment() {
    private lateinit var listener: ExerciseCreatedListener
    private lateinit var binding: FragmentCreateBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as ExerciseCreatedListener
            } else {
                activity as ExerciseCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        dialog?.setTitle(R.string.itemCreateExercise)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spnrExerciseType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Reps", "Time")
        )

        binding.btnCreateExercise.setOnClickListener {
            val selectedType = when (binding.spnrExerciseType.selectedItemPosition) {
                0 -> Exercise.ExerciseType.Reps
                1 -> Exercise.ExerciseType.Time
                else -> Exercise.ExerciseType.Reps
            }

            listener.onExerciseCreated(Exercise(
                id = Random.nextInt(),
                name = binding.etExerciseName.text.toString(),
                description = binding.etExerciseDescription.text.toString(),
                type = selectedType,
                amountDoneOnDate = HashMap()
            ))
            dismiss()
        }

        binding.btnCancelCreateExercise.setOnClickListener {
            dismiss()
        }

    }

    private fun showDatePickerDialog() {
        //TODO
    }

    interface ExerciseCreatedListener {
        fun onExerciseCreated(exercise: Exercise)
    }
}