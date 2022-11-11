package com.gyonia.exercisetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gyonia.exercisetracker.R
import com.gyonia.exercisetracker.databinding.RowExerciseBinding
import com.gyonia.exercisetracker.model.Exercise

class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val exerciseList = mutableListOf<Exercise>()

    var itemClickListener: ExerciseItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowExerciseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList[position]

        holder.exercise = exercise

        holder.binding.tvName.text = exercise.name

        val resource = when (exercise.type) {
            Exercise.ExerciseType.Reps -> R.drawable.ic_reps
            Exercise.ExerciseType.Time -> R.drawable.ic_time
        }
        holder.binding.ivType.setImageResource(resource)
    }

    fun addItem(exercise: Exercise) {
        val size = exerciseList.size
        exerciseList.add(exercise)
        notifyItemInserted(size)
    }

    fun addAll(exercises: List<Exercise>) {
        val size = exerciseList.size
        exerciseList += exercises
        notifyItemRangeInserted(size, exercises.size)
    }

    fun deleteRow(position: Int) {
        exerciseList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = exerciseList.size

    inner class ViewHolder(val binding: RowExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        var exercise: Exercise? = null

        init {
            itemView.setOnClickListener {
                exercise?.let { exercise -> itemClickListener?.onItemClick(exercise) }
            }

            itemView.setOnLongClickListener { view ->
                itemClickListener?.onItemLongClick(adapterPosition, view)
                true
            }
        }
    }

    interface ExerciseItemClickListener {
        fun onItemClick(exercise: Exercise)
        fun onItemLongClick(position: Int, view: View): Boolean
    }
}