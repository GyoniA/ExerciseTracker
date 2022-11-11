package com.gyonia.exercisetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gyonia.exercisetracker.adapter.SimpleItemRecyclerViewAdapter
import com.gyonia.exercisetracker.databinding.FragmentExerciseListBinding
import com.gyonia.exercisetracker.model.Exercise

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ExerciseDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class ExerciseListFragment : Fragment(), SimpleItemRecyclerViewAdapter.ExerciseItemClickListener {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!

    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter
    private  var itemDetailFragmentContainer: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
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
        val demoData = mutableListOf(
            Exercise(1, "title1", "description1", Exercise.ExerciseType.Reps, HashMap()),
            Exercise(2, "title2", "description2", Exercise.ExerciseType.Time, HashMap()),
            Exercise(3, "title3", "description3", Exercise.ExerciseType.Reps, HashMap())
        )
        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        simpleItemRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.exercise_list).adapter =
            simpleItemRecyclerViewAdapter
    }

    override fun onItemClick(exercise: Exercise) {
        val bundle = Bundle()
        bundle.putString(
            ExerciseDetailHostActivity.KEY_DESC,
            exercise.description
        )
        if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer!!.findNavController()
                .navigate(R.id.fragment_exercise_detail, bundle)
        } else {
            findNavController(this).navigate(R.id.show_exercise_detail, bundle)
        }
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        val popup = PopupMenu(requireActivity(), view)
        popup.inflate(R.menu.menu_exercise)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> simpleItemRecyclerViewAdapter.deleteRow(position)
            }
            false
        }
        popup.show()
        return false
    }
/*
    class SimpleItemRecyclerViewAdapter(
        private val values: List<PlaceholderContent.PlaceholderItem>,
        private val itemDetailFragmentContainer: View?
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding = ExerciseListContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.id
            holder.contentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener { itemView ->
                    val item = itemView.tag as PlaceholderContent.PlaceholderItem
                    val bundle = Bundle()
                    bundle.putString(
                        ExerciseDetailFragment.ARG_ITEM_ID,
                        item.id
                    )
                    if (itemDetailFragmentContainer != null) {
                        itemDetailFragmentContainer.findNavController()
                            .navigate(R.id.fragment_exercise_detail, bundle)
                    } else {
                        itemView.findNavController().navigate(R.id.show_exercise_detail, bundle)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    /**
                     * Context click listener to handle Right click events
                     * from mice and trackpad input to provide a more native
                     * experience on larger screen devices
                     */
                    setOnContextClickListener { v ->
                        val item = v.tag as PlaceholderContent.PlaceholderItem
                        Toast.makeText(
                            v.context,
                            "Context click of item " + item.id,
                            Toast.LENGTH_LONG
                        ).show()
                        true
                    }
                }

                setOnLongClickListener { v ->
                    // Setting the item id as the clip data so that the drop target is able to
                    // identify the id of the content
                    val clipItem = ClipData.Item(item.id)
                    val dragData = ClipData(
                        v.tag as? CharSequence,
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        clipItem
                    )

                    if (Build.VERSION.SDK_INT >= 24) {
                        v.startDragAndDrop(
                            dragData,
                            View.DragShadowBuilder(v),
                            null,
                            0
                        )
                    } else {
                        v.startDrag(
                            dragData,
                            View.DragShadowBuilder(v),
                            null,
                            0
                        )
                    }
                }
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(binding: ExerciseListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val idView: TextView = binding.idText
            val contentView: TextView = binding.content
        }

    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}