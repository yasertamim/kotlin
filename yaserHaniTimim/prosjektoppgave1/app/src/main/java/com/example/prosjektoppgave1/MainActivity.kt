package com.example.prosjektoppgave1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prosjektoppgave1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_addtodolist.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_list.*


class MainActivity() : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    private fun signInAnonymously() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "login success ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "login fail", it)
        }
    }

    private fun sendListToDataBase(todoList:TodoList){
        var db = FirebaseFirestore.getInstance()
        val item = hashMapOf("list_title" to todoList.list_title)
        val listRef:  CollectionReference = db.collection("Lists")
        listRef.document(todoList.list_title.toString()).set(item)

    }

    private fun getListsFromDataBase(holder:TodoDepositoryHolder) {
        val docRef = db.collection("Lists")
        docRef.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        var title = document.get("list_title")
                        var list = TodoList(title.toString())

                       holder.updateCollection(list)
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

    }





        private lateinit var AddListAdapter: MainRecyclerAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            auth = Firebase.auth

            signInAnonymously()


            AddListAdapter = MainRecyclerAdapter(mutableListOf())
            rvLists_todo.adapter = AddListAdapter
            rvLists_todo.layoutManager = LinearLayoutManager(this)


            val Lists = TodoDepositoryHolder.instance
            getListsFromDataBase(Lists)


            Lists.onList = {

                for (l in it){
                    AddListAdapter.addTodoList(l)
                    Lists.delet(l)
                }
            }

            Lists.loadList()


            btn_addList.setOnClickListener {
                val todoListTitle = addListName.text.toString()
                if (todoListTitle.isNotEmpty()) {
                    val todoList = TodoList(todoListTitle)
                    addListName.text.clear()
                    if (todoListTitle!=null) {
                        sendListToDataBase(todoList)
                    }
                }

               val intent = Intent(this, AddTodoListActivity::class.java)
                intent.putExtra("listTitle",todoListTitle)
                startActivity(intent)

            }
        }
    }










