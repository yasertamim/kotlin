package com.example.prosjektoppgave1

class TodoDepositoryHolder {

    private lateinit var todoCollection: MutableList<TodoList>


    var onList: ((List<TodoList>) -> Unit)? = null
    var onItemUpdate: ((todolist: TodoList) -> Unit)? = null



    fun loadList(){
        todoCollection = mutableListOf(

        )

        onList?.invoke(todoCollection)
    }

    fun updateCollection(list:TodoList){
        todoCollection.add(list)
        onList?.invoke(todoCollection)
    }
    fun delet(i:TodoList){
        todoCollection.remove(i)
        onList?.invoke(todoCollection)
    }

    companion object{
        val instance = TodoDepositoryHolder()
    }


}