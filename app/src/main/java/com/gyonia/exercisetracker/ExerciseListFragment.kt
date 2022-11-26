package com.gyonia.exercisetracker

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gyonia.exercisetracker.adapter.SimpleItemRecyclerViewAdapter
import com.gyonia.exercisetracker.databinding.FragmentExerciseListBinding
import com.gyonia.exercisetracker.model.Exercise
import com.gyonia.exercisetracker.viewmodel.ExerciseViewModel


/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ExerciseDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class ExerciseListFragment : Fragment(), ExerciseCreateFragment.ExerciseCreatedListener, SimpleItemRecyclerViewAdapter.ExerciseItemClickListener {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!

    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter
    private  var itemDetailFragmentContainer: View? = null
    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        ExerciseApplication.exerciseViewModel = exerciseViewModel
        exerciseViewModel.allExercises.observe(this) { exercises ->
            simpleItemRecyclerViewAdapter.submitList(exercises)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        exerciseViewModel.allExercises.observe(viewLifecycleOwner) { exercises ->
            simpleItemRecyclerViewAdapter.submitList(exercises)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-land)
        itemDetailFragmentContainer = view.findViewById(R.id.exercise_detail_nav_container)

        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        binding.root.findViewById<RecyclerView>(R.id.exercise_list).adapter =
            simpleItemRecyclerViewAdapter
    }

    override fun onItemClick(exercise: Exercise) {
        val bundle = Bundle()
        bundle.putInt(
            ExerciseDetailHostActivity.KEY_ID,
            exercise.id
        )
        if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer!!.findNavController()
                .navigate(R.id.fragment_exercise_detail, bundle)
        } else {
            findNavController(this).navigate(R.id.show_exercise_detail, bundle)
        }
    }

    override fun onItemLongClick(position: Int, view: View, exercise: Exercise): Boolean {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu_exercise)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    exerciseViewModel.delete(exercise)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemCreateExercise) {
            val exerciseCreateFragment = ExerciseCreateFragment()
            exerciseCreateFragment.setTargetFragment(this,1)
            fragmentManager?.let { exerciseCreateFragment.show(it, "TAG") }
        } else  if (item.itemId == R.id.settings) {
            val intentSettings = Intent(context, SettingsActivity::class.java)
            startActivity(intentSettings)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onExerciseCreated(exercise: Exercise) {
        exerciseViewModel.insert(exercise)
    }


}