Name
ToDoList (Prosjektoppgave I)

Description
This application is the first project in IKT205. Kotlin is the language which is used to develop the app, it uses Firestore as a backend database.
The application interface consist of to screens. The first screen is to display all lists, whereas the second one is to display the items of a specific list.

Screens

First screen:
-	A Header with text “My lists”
-	RecyclerView to list all lists that a user create in the app
-	EditText in the very left bottom to write a title to new list
-	Add button to add new list with the title from EditText
<img width="195" alt="firestScreen" src="https://user-images.githubusercontent.com/60326230/114682259-35f12b00-9d0f-11eb-8a38-27d88ebb5386.PNG">



Second screen:
-	Header with list title
-	RecyclerView to list all items that a user added to the list
-	EditText in the very left bottom to write a new item 
-	Add button to add new item to the list
-	Delete button to delete all checked items in the list
<img width="195" alt="secondScreen" src="https://user-images.githubusercontent.com/60326230/114682634-8e282d00-9d0f-11eb-82da-849fee1878dd.PNG">




How it works

When user open the application so the first screen is pop up, then the user can write a title to the list that he/she would create. When the user press ADD button, the application passes the text in the EditText to the second screen and set the text as a title to the list. 
In the second activity (screen) user can add items to the list that is already created
By writing the title of the item and pressing ADD button, then the new item is added to recyclerview in this activity (AddTodoListActivity). Also the value of the progress Bar increases.  When user would delete an item from list so user have to check the checkbox and then press DELETE button in the bottom part of screen.
When user go back to the first screen (MainActivity), so the new list is created and added to the recyclerview in this activity.
All functions included add lists, add items, delete lists and delete items are synchronized with Firestore database.


Firestore

<img width="949" alt="firebase" src="https://user-images.githubusercontent.com/60326230/114683479-5968a580-9d10-11eb-8b25-574a9e0dc71e.PNG">


 link til nedlasting av APK via Firebase App distribution [here](https://appdistribution.firebase.dev/i/492859cd31be7baf)

