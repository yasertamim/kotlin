package com.example.prosjektoppgave1

class ItemDepositoryHolder {

    private lateinit var itemCollection: MutableList<Todo>
    private lateinit var itemTodo:Todo


    var onItem: ((List<Todo>) -> Unit)? = null
    var onItemUpdate: ((todo: Todo) -> Unit)? = null
    var onHeader: ((string:String)-> Unit)? = null


    fun loadList(){

        itemCollection = mutableListOf(

        )

        onItem?.invoke(itemCollection)
    }

    fun updateCollection(list:Todo){
        itemCollection.add(list)
        onItem?.invoke(itemCollection)
    }

    fun delete(i:Todo){
        itemCollection.remove(i)
        onItem?.invoke(itemCollection)
    }
    fun size():Int{

        return itemCollection.size
    }


    fun updateItem(i:Todo){
       for (item in itemCollection){
           if (i.item_title == item.item_title){
               itemCollection.remove(item)
               i.isChecked = !i.isChecked
               itemCollection.add(i)
           }
           onItem?.invoke(itemCollection)
       }
    }


    companion object{
        val instance = ItemDepositoryHolder()
    }

}