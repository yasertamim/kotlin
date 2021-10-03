package com.example.prosjektoppgave1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_addtodolist.*
import kotlinx.android.synthetic.main.new_list.view.*
import java.sql.Array

var db = FirebaseFirestore.getInstance()



private fun deleteListFromDatabase(todolist:TodoList){
    val title = todolist.list_title.toString()
    val listRef:CollectionReference = db.collection("Lists")
    val subRef: CollectionReference= db.collection(title)
    subRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                 document.reference.delete()

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    listRef.document(title).delete()
}



private fun getItemsFromDataBase(holder:ItemDepositoryHolder,listTitle:String) {

    val docRef = db.collection(listTitle)
        docRef
            .get().addOnSuccessListener{result->

                for(document in result){
                    val title = document.get("item_title").toString()
                    val checked= document.get("isChecked")
                    if (checked == true) {
                        val item = Todo(title, true)
                        holder.updateCollection(item)
                    }else{
                        val item= Todo(title)
                        holder.updateCollection(item)

                    }

                    Log.d(TAG,"${document.id}=>${document.data}")

                }
            }
            .addOnFailureListener{exception->
                Log.w(TAG,"Error getting documents.",exception)
            }

    }



class MainRecyclerAdapter(
        private val todosList : MutableList<TodoList>
    ) : RecyclerView.Adapter<MainRecyclerAdapter.TodoViewHolder2>() {


    class TodoViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder2 {
        return TodoViewHolder2(
                LayoutInflater.from(parent.context).inflate(

                        R.layout.new_list,
                        parent,
                        false
                )
        )

    }

    fun addTodoList(todoLista: TodoList){
        todosList.add(todoLista)

        notifyItemInserted(todosList.size-1)
    }

    fun deleteDoneList(todoLista:TodoList) {
        todosList.remove(todoLista)
        notifyDataSetChanged()
        deleteListFromDatabase(todoLista)

    }

    fun update(listsTodo:TodoList){
            todosList.add(listsTodo)
            notifyItemInserted(todosList.size-1)
    }




        override fun onBindViewHolder(holder: TodoViewHolder2, position: Int) {
            val current_list = todosList[position]

            holder.itemView.apply {
                tvListTodo.text = current_list.list_title

                delete_list.setOnClickListener {
                    deleteDoneList(current_list)
                }

                setOnClickListener {

                    val TodoItemTitle = current_list.list_title
                    val ListItems = ItemDepositoryHolder.instance
                   getItemsFromDataBase(ListItems,TodoItemTitle.toString())
                    val intent = Intent(context, AddTodoListActivity::class.java)
                    intent.putExtra("Title",TodoItemTitle)
                    context.startActivity(intent)
                }


                }




    }

    override fun getItemCount(): Int {

       return todosList.size
    }


}
