package com.gyonia.exercisetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gyonia.exercisetracker.databinding.FragmentExerciseDetailBinding
import com.gyonia.exercisetracker.model.Exercise

/**
 * A fragment representing a single Exercise detail screen.
 * This fragment is either contained in a [ExerciseListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ExerciseDetailFragment : Fragment() {

    private var selectedExercise: Exercise? = null

    private lateinit var _binding: FragmentExerciseDetailBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            selectedExercise = Exercise(
                id = 0,
                name = "name",
                description = args.getString(ExerciseDetailHostActivity.KEY_DESC) ?: "description",
                type = Exercise.ExerciseType.Reps,
                amountDoneOnDate = HashMap<String, Int>(),
                ownerUserId = ExerciseApplication.userId
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseDetail.text = selectedExercise?.description
        if (selectedExercise?.type == Exercise.ExerciseType.Reps) {
            binding.inputAmountDone?.hint = getString(R.string.reps_done)
        } else {
            binding.inputAmountDone?.hint = getString(R.string.time_done)
        }
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