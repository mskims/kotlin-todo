package com.donnfelker.kotlinmix

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.donnfelker.kotlinmix.models.Todo
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import java.util.*


class EditFragment : Fragment() {

    //Constants
    val TODO_ID_KEY : String = "todo_id_key"
    val realm : Realm = Realm.getDefaultInstance()
    // Fields
    var todo : Todo? = null

    companion object {

        fun newInstance(id : String) : EditFragment {
            val args : Bundle = Bundle()
            args.putString("todo_id_key", id)
            var editFragment : EditFragment = newInstance()
            editFragment.arguments = args
            return editFragment
        }
        //Called by Companion Object
        fun newInstance() : EditFragment {
            return EditFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            verticalLayout {
                padding = dip(30)
                //Title
                val title = editText {
                    id = R.id.todo_title
                    hintResource = R.string.title_hint
                }
                //Desc
                val desc = editText {
                    id = R.id.todo_desc
                    hintResource = R.string.description_hint
                }
                //Add
                button {
                    id = R.id.todo_add
                    textResource = R.string.add_todo
                    onClick { view -> createTodoFrom(title, desc) }
                }
            }
        }.view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        try {
            //From Fragment.getArguments() : Return the arguments supplied to Fragment
            if (arguments != null && arguments.containsKey(TODO_ID_KEY)) {
                //Get  todoId from Arguments
                val todoId = arguments.getString(TODO_ID_KEY)
                //Get realm Object from todoId
                todo = realm.where(Todo::class.java).equalTo("id", todoId).findFirst()
                //Title
                //Get EditText Reference
                val todoTitle = find<EditText>(R.id.todo_title)
                //SafeCall? Set Text if not null else assign null
                todoTitle.setText(todo?.title)
                //Desc
                val todoDesc = find<EditText>(R.id.todo_desc)
                todoDesc.setText(todo?.description)
                //Add
                val add = find<Button>(R.id.todo_add)
                add.setText(R.string.save)

                //Add Click Listenter
                //add.setOnClickListener { view -> createTodoFrom(todoTitle, todoDesc) }
            }
        } catch(e: Exception) {
            Log.d(MainActivity.TAG, e.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Close realm instance
        realm.close()
    }

    /**
     *  A private function to create a TODO item in the database (Realm).
     *
     *  @param title the title edit text.
     *  @param desc the description edit text.
     */
    private fun createTodoFrom(title: EditText, desc: EditText) {

        try {
            //Call beginTranslation to start realm Transaction
            realm.beginTransaction()

            val newPkey : String = UUID.randomUUID().toString()

            //Either update the edited object or create a new one, with a new required key.
            //Using Elvis operator like if object != null else createObject
            val t = todo?: realm.createObject(Todo::class.java, newPkey)

            //Savecall to get Id, else with Elvis Operator get a new UUID
            //t.id = todo?.id?: newPkey
            t.title = title.text.toString()
            t.description = desc.text.toString()

            //Commit Transaction
            realm.commitTransaction()

            // Go back to previous activity
            //activity.supportFragmentManager.popBackStack()
//            activity.fragmentManager.popBackStack()
            activity.supportFragmentManager.popBackStack()
        } catch(e: Exception) {
            realm.cancelTransaction()
            Log.d(MainActivity.TAG, e.message)
        }
    }
}
