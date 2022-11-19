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
class ExerciseDetailFragment : Fragment() {

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
/*
        selectedExercise?.amountDoneOnDate?.let {
            it["2022.1.22"] = 2
            it["2022.2.12"] = 3
            it["2022.3.31"] = 5
            it["2022.4.8"] = 3
            it["2022.5.22"] = 5
            it["2022.6.32"] = 12
            it["2022.6.32"] = 12
            it["2022.6.32"] = 12
            it["2022.7.15"] = 1
            it["2022.8.22"] = 2
            it["2022.9.12"] = 3
            it["2022.10.31"] = 5
            it["2022.11.8"] = 3
        }*/

        var valuesForDates = selectedExercise?.amountDoneOnDate?.toList()

        valuesForDates = valuesForDates?.sortedBy { it.first }

        valuesForDates?.forEach {
            val dateStrings = it.first.split(".")
            val dateinFormat = dateStrings[1] + "/" + dateStrings[2]
            series.addPoint(ValueLinePoint(dateinFormat, it.second.toFloat()))
        }
        /*
        series.addPoint(ValueLinePoint("01.22", 2.4f))
        series.addPoint(ValueLinePoint("02.12", 3.4f))
        series.addPoint(ValueLinePoint("03.31", .4f))
        series.addPoint(ValueLinePoint("04.08", 1.2f))
        series.addPoint(ValueLinePoint("05.22", 2.6f))
        series.addPoint(ValueLinePoint("06.32", 1.0f))
        series.addPoint(ValueLinePoint("07.15", 3.5f))*/

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
            val date = binding.inputDate?.year.toString() + "." + binding.inputDate?.month.toString() + "." + binding.inputDate?.dayOfMonth.toString()
            selectedExercise?.let { exercise ->
                exercise.amountDoneOnDate[binding.inputDate.toString()] =
                    binding.inputAmountDone?.text.toString().toInt()
            }
            updateChart()
        }
        selectedExercise?.amountDoneOnDate?.clear()
        updateChart()
    }

    /**
     * The placeholder content this fragment is presenting.
     */
    /*
    private var item: PlaceholderContent.PlaceholderItem? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
            val dragData = clipDataItem.text
            selectedExercise = PlaceholderContent.ITEM_MAP[dragData]
            updateContent()
        }
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.exerciseDetail

        updateContent()
        rootView.setOnDragListener(dragListener)

        return rootView
    }

    private fun updateContent() {
        toolbarLayout?.title = item?.content

        // Show the placeholder content as text in a TextView.
        item?.let {
            itemDetailTextView.text = it.details
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
}