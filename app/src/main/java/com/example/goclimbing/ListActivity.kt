package com.example.goclimbing

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject

class ListActivity : AppCompatActivity() {
    lateinit var lista: RecyclerView
    lateinit var busqueda: SearchView

    var rocodromos = ArrayList<Rocodromo>()
    var rocodromosFiltrados = ArrayList<Rocodromo>()

    val adapter = RocodromoAdapter(object : RocodromoAdapter.OnItemClickListener {
        override fun onItemClick(rocodromo: Rocodromo) {
            val intent = Intent(applicationContext, InfoActivity::class.java) // https://medium.com/susheel-karam/different-ways-to-get-context-in-android-8018d9663292
            intent.putExtra("rocodromoID", rocodromo.Id)
            startActivity(intent)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setUpList()
        cargarBaseDeDatos()
        manejarBusqueda()
    }

    private fun manejarBusqueda() {
        busqueda = findViewById(R.id.busqueda)
        busqueda.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                filtrar(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        busqueda.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                rocodromosFiltrados = rocodromos
                adapter.submitList(rocodromosFiltrados)
                return true
            }
        })
    }

    private fun filtrar(query: String?) {
        if (query.isNullOrEmpty()) {
            rocodromosFiltrados = rocodromos
            adapter.submitList(rocodromosFiltrados)
        } else {
            // Filtrar en repositorio y plasmar resultados en lista

            val resultadoQuery = rocodromos.filter { rocodromo ->
                rocodromo.Nombre?.lowercase()?.contains(query.lowercase()) ?: false
            }

            rocodromosFiltrados = ArrayList(resultadoQuery)
            adapter.submitList(rocodromosFiltrados)
        }
    }

    private fun cargarBaseDeDatos() {
        //database = Firebase.database
        val database = Firebase.firestore

        val docRef = database.collection("Rocodromos")
        docRef.get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    rocodromos.clear()
                    rocodromosFiltrados.clear()

                    for (document in documents) {
                        val rocodromo = document.toObject<Rocodromo>()
                        Log.d("rocodromo", "${rocodromo}")
                        rocodromos.add(rocodromo)
                    }

                    rocodromosFiltrados = rocodromos

                    adapter.submitList(rocodromosFiltrados)

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
