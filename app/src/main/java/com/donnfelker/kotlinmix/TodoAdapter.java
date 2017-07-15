package com.donnfelker.kotlinmix;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donnfelker.kotlinmix.models.Todo;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;


import butterknife.Bind;
import butterknife.ButterKnife;

public class TodoAdapter extends RealmBasedRecyclerViewAdapter<Todo, TodoAdapter.ViewHolder> {

    private TodoItemClickListener clickListener;

    public TodoAdapter(Context context, RealmResults<Todo> realmResults, boolean automaticUpdate, boolean animateResults, TodoItemClickListener clickListener) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.todo_item, viewGroup, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final Todo todo = realmResults.get(position);
        viewHolder.todoTitle.setText(todo.getTitle());
    }

    class ViewHolder extends RealmViewHolder implements View.OnClickListener {

        @Bind(R.id.todo_item_todo_title) public TextView todoTitle;
        private TodoItemClickListener clickListener;

        public ViewHolder(View view, TodoItemClickListener clickListener) {
            super(view);
            this.clickListener = clickListener;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null) {
                clickListener.onTodoClick(v, realmResults.get(getAdapterPosition()));
            }
        }
    }

    public interface TodoItemClickListener {
        void onTodoClick(View caller, Todo task);
    }
}
