package com.donnfelker.kotlinmix

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.donnfelker.kotlinmix.models.Todo

import io.realm.RealmBasedRecyclerViewAdapter
import io.realm.RealmResults
import io.realm.RealmViewHolder

import butterknife.ButterKnife
import org.jetbrains.anko.find


class TodoAdapter(context: Context, realmResults: RealmResults<Todo>, automaticUpdate: Boolean, animateResults: Boolean, private val clickListener: TodoItemClickListener) : RealmBasedRecyclerViewAdapter<Todo, TodoAdapter.ViewHolder>(context, realmResults, automaticUpdate, animateResults) {
    override fun onCreateRealmViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = inflater.inflate(R.layout.todo_item, viewGroup, false)
        return ViewHolder(v, clickListener)
    }

    override fun onBindRealmViewHolder(viewHolder: ViewHolder, position: Int) {
        val todo = realmResults[position]
        viewHolder.todoTitle!!.text = todo.title
    }

    inner class ViewHolder(view: View, private val clickListener: TodoItemClickListener?) : RealmViewHolder(view), View.OnClickListener {

        val todoTitle: TextView? = view.find<TextView>(R.id.todo_item_todo_title)

        init {
            ButterKnife.bind(this, view)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            clickListener?.onTodoClick(v, realmResults[adapterPosition])
        }
    }

    interface TodoItemClickListener {
        fun onTodoClick(caller: View, task: Todo)
    }
}