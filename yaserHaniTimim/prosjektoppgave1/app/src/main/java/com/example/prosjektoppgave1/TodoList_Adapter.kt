package com.example.prosjektoppgave1

import android.content.ContentValues.TAG
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.prosjektoppgave1.databinding.ActivityMainBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.activity_addtodolist.*
import kotlinx.android.synthetic.main.activity_addtodolist.view.*
import kotlinx.android.synthetic.main.item_todo.view.*
import kotlinx.android.synthetic.main.new_list.view.*



private fun updateCheckedToDataBase(todo: Todo, list: String, state: Boolean){
    val docTitle = todo.item_title
    val itemRef:  CollectionReference = db.collection(list)
    val docRef:DocumentReference = itemRef.document(docTitle)
    docRef
            .update("isChecked", state)
            .addOnSuccessListener { Log.d(TAG, "State successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
}

private fun deleteItemFromDB(todo:Todo,collection:String){
    val listRef:CollectionReference = db.collection(collection)
    listRef.document(todo.item_title)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

}


class TodoList_Adapter(

        private val todos: MutableList<Todo>,


        ) : RecyclerView.Adapter<TodoList_Adapter.TodoViewHolder>(){

        class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

            return TodoViewHolder(

                    LayoutInflater.from(parent.context).inflate(

                            R.layout.item_todo,
                            parent,
                            false
                    )


            )

        }



        fun update(ItemTodo: Todo){

            todos.add(ItemTodo)
            notifyItemInserted(todos.size - 1)

        }


    fun deleteDoneTodo() {
            todos.removeAll { todo ->
                todo.isChecked
            }
            notifyDataSetChanged()

        }


        private fun toggleStrikThrough(tvTodoTitle: TextView, isChecked: Boolean) {
            if(isChecked){
                tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
        }



        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            val activity:AddTodoListActivity? = holder.itemView.context as AddTodoListActivity
            val title = activity?.getListTitle()
            val curentTodo= todos[position]
            holder.itemView.apply {
                tvitemTodo.text = curentTodo.item_title
                cbdone.isChecked = curentTodo.isChecked
                toggleStrikThrough(tvitemTodo, curentTodo.isChecked)
                cbdone.setOnCheckedChangeListener { _, isChecked ->
                    toggleStrikThrough(tvitemTodo, isChecked)
                    curentTodo.isChecked = !curentTodo.isChecked
                    updateCheckedToDataBase(curentTodo,title.toString(),curentTodo.isChecked)
                }

                delete_done_item.setOnClickListener {
                    deleteDoneTodo()
                
                    deleteItemFromDB(curentTodo,title.toString())

                }
            }
        }

        override fun getItemCount(): Int {

            return todos.size

        }
}