package com.donnfelker.kotlinmix

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.donnfelker.kotlinmix.models.Todo

import butterknife.ButterKnife
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView
import io.realm.Realm
import org.jetbrains.anko.support.v4.find


class TodosFragment : Fragment(), TodoAdapter.TodoItemClickListener {

    companion object {

        fun newInstance(): TodosFragment {
            return TodosFragment()
        }
    }

    private var realm: Realm? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_todos, container, false)
        ButterKnife.bind(this, v)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        realm = Realm.getDefaultInstance()

    }

    override fun onResume() {
        super.onResume()
        val todos = realm!!.where(Todo::class.java).findAll()
        val adapter = TodoAdapter(activity, todos, true, true, this)
        val rv: RealmRecyclerView? = find(R.id.todos_recycler_view)
        rv!!.setAdapter(adapter)

    }

    override fun onDestroy() {
        super.onDestroy()
        realm!!.close()
    }

    override fun onTodoClick(caller: View, task: Todo) {
        val editFragment = EditFragment.newInstance(task.id!!)
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, editFragment, editFragment.javaClass.simpleName)
                .addToBackStack(editFragment.javaClass.simpleName)
                .commit()

    }
}
