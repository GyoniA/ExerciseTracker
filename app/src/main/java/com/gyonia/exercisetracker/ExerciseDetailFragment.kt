package com.gyonia.exercisetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gyonia.exercisetracker.database.RoomExercise
import com.gyonia.exercisetracker.databinding.FragmentExerciseDetailBinding
import com.gyonia.exercisetracker.model.Exercise
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import kotlin.concurrent.thread


/**
 * A fragment representing a single Exercise detail screen.
 * This fragment is either contained in a [ExerciseListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ExerciseDetailFragment () : Fragment() {

    private fun RoomExercise.toDomainModel(): Exercise {
        return Exercise(
            id = id,
            name = name,
            description = description,
            type = type,
            amountDoneOnDate = amountDoneOnDate,
            ownerUserId = ownerUserId
        )
    }

    private var selectedExercise: Exercise? = null

    private lateinit var _binding: FragmentExerciseDetailBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = arguments?.getInt(ExerciseDetailHostActivity.KEY_ID) ?: 0

        thread {
            selectedExercise = ExerciseApplication.exerciseDatabase.exerciseDao().getExerciseById(id)?.toDomainModel()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updateChart() {
        val chart = binding.cubiclinechart as ValueLineChart


        val series = ValueLineSeries()
        series.color = 0xFFFE7A70.toInt()

        var valuesForDates = selectedExercise?.amountDoneOnDate?.toList()

        valuesForDates = valuesForDates?.sortedBy { it.first }

        valuesForDates?.forEach {
            val dateStrings = it.first.split(".")
            val dateInFormat = dateStrings[1] + "/" + dateStrings[2]
            series.addPoint(ValueLinePoint(dateInFormat, it.second.toFloat()))
        }

        chart.clearChart()

        chart.addSeries(series)
        chart.startAnimation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseDetail.text = selectedExercise?.description
        if (selectedExercise?.type == Exercise.ExerciseType.Reps) {
            binding.amountDoneTitle?.text = getText(R.string.reps_done)
        } else {
            binding.amountDoneTitle?.text = getText(R.string.time_done)
        }
        binding.submitButton?.setOnClickListener {
            val date = binding.inputDate?.year.toString() + "." + (binding.inputDate?.month?.toInt()?.plus(1)).toString() + "." + binding.inputDate?.dayOfMonth.toString()
            selectedExercise?.let { exercise ->
                exercise.amountDoneOnDate[date] =
                    binding.inputAmountDone?.text.toString().toInt()
                thread {
                    ExerciseApplication.exerciseViewModel?.update(exercise)
                }
            }
            updateChart()
        }
        updateChart()
    }
}