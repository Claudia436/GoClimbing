package com.example.goclimbing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        database = Firebase.database.reference

        recibirLista()
    }

    fun recibirLista() {
        database.child("Rocodromos").get().addOnSuccessListener {
            Toast.makeText(baseContext, "Got value ${it.value}", Toast.LENGTH_LONG).show()
            Log.i("firebase rocodromos", "Got value ${it.value}")


        }.addOnFailureListener{
            Toast.makeText(baseContext, "error getting value", Toast.LENGTH_LONG).show()
            Log.e("firebase rocodromos", "Error getting data", it)
        }

    }


}