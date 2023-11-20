package com.example.goclimbing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject

class ListActivity : AppCompatActivity() {
    lateinit var lista: RecyclerView
    val adapter = RocodromoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setUpList()

        //database = Firebase.database
        val database = Firebase.firestore

        val docRef = database.collection("Rocodromos")
        docRef.get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    var rocodromos = ArrayList<Rocodromo>()

                    for (document in documents) {
                        val rocodromo = document.toObject<Rocodromo>()
                        Log.d("rocodromo", "${rocodromo}")
                        rocodromos.add(rocodromo)
                    }

                    adapter.submitList(rocodromos)

                    Log.d("document", "DocumentSnapshot data: ${documents}")
                    // convertir document en nuestra class Rocodromos
                    // dicha clase es la que vamos a pasar a nuestra lista
                } else {
                    Log.d("document", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("", "get failed with ", exception)
            }

    }

    private fun setUpList() {
        lista = findViewById(R.id.list)
        lista.adapter = adapter
    }
}

data class Rocodromo(
    val Id: String? = null,
    val Nombre: String? = null,
    val Descripcion: String? = null,
    val Horario: String? = null,
    val Latitud: String? = null,
    val Longitud: String? = null,
    val Imagen: String? = null,
    val Instalaciones: String? = null,
    val Tarifas: String? = null,
    val Web: String? = null,
    val Ubicacion: String? = null
)
