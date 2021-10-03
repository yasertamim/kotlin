package com.example.prosjektoppgave1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_addtodolist.*
import kotlinx.android.synthetic.main.item_todo.*

private fun sendCheckedItemToDataBase(listTitle: String, item: Todo){
    val doc = db.collection(listTitle).document(item.item_title)
    doc
            .update("isChecked", true)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
}


val EXTRA_LIST: TodoList = TodoList("list1")

private fun deleteItemFromDatabase(header: String) {
    val listRef: CollectionReference = db.collection(header)
    listRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var title = document.get("item_title")
                    var checked = document.get("isChecked")
                    if (checked == true) {

                        listRef.document(title.toString()).delete()
                    }

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

//    listRef.get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    var title = document.get("item_title")
//                    var checked = document.get("isChecked")
//                    if (checked == true) {
//
//                        listRef.document(title.toString()).delete()
//                    }
//
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "Error getting documents: ", exception)
//            }



}





    class AddTodoListActivity : AppCompatActivity() {

        private lateinit var todoListAdapter: TodoList_Adapter


        private fun senditemToDataBase(todoItem: Todo, list_title: String) {
            var db = FirebaseFirestore.getInstance()
            val item = hashMapOf("item_title" to todoItem.item_title, "isChecked" to todoItem.isChecked)
            val listRef: CollectionReference = db.collection(list_title)
            val docRef: DocumentReference = listRef.document(todoItem.item_title)
            docRef.set(item)

        }

        public fun getListTitle():String{
            val title= single_list_header.text
            return title.toString()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_addtodolist)



            val bundle = intent.extras
            val todoListTitle = bundle!!.getString("listTitle")
            var TodoItemTitle = bundle!!.getString("Title")
            single_list_header.text = todoListTitle



            todoListAdapter = TodoList_Adapter(mutableListOf())
            rvItem_todo.adapter = todoListAdapter
            rvItem_todo.layoutManager = LinearLayoutManager(this)



            val Items = ItemDepositoryHolder.instance
            if (TodoItemTitle != null) {
                single_list_header.text = TodoItemTitle
            }




            Items.onItem = {
                for (l in it) {
                    todoListAdapter.update(l)
                    Items.delete(l)
                    progress_single_list.progress = todoListAdapter.itemCount
                }

            }

            Items.loadList()



            btn_addItem.setOnClickListener {
                val todoTitle = tvAddItem.text.toString()

                if (todoTitle.isNotEmpty()) {
                    val todo = Todo(todoTitle)
                    progress_single_list.progress = Items.size()
                    ItemDepositoryHolder.instance.updateCollection(todo)
                    tvAddItem.text.clear()
                    senditemToDataBase(todo,  single_list_header.text.toString())

                }
            }


            btn_delete.setOnClickListener {
                val collection = single_list_header.text.toString()
                deleteItemFromDatabase(collection)
                todoListAdapter.deleteDoneTodo()
                progress_single_list.progress = todoListAdapter.itemCount

            }


        }
    }

