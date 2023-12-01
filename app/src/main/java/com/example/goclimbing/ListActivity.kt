package com.example.goclimbing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject

class ListActivity : AppCompatActivity() {
    lateinit var lista: RecyclerView
    lateinit var busqueda: SearchView

    var rocodromos = ArrayList<Rocodromo>()
    var rocodromosFiltrados = ArrayList<Rocodromo>()

    // adapter para cargar la lista de rocodromos
    val adapter = RocodromoAdapter(object : RocodromoAdapter.OnItemClickListener {
        override fun onItemClick(rocodromo: Rocodromo) {
            val intent = Intent(applicationContext, InfoActivity::class.java)
            // pasamos el id del rocodromo, para luego cargarlo en la vista detalle (InfoActivity)
            intent.putExtra("rocodromoID", rocodromo.Id)
            startActivity(intent)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        configuracionListaDeRocodromos() //conexion adapter con la lista
        cargarBaseDeDatos()
        manejarBusqueda()
    }

    private fun configuracionListaDeRocodromos() {
        lista = findViewById(R.id.list)
        lista.adapter = adapter
    }

    private fun cargarBaseDeDatos() {
        val database = Firebase.firestore

        val docRef = database.collection("Rocodromos") // nombre de la coleccion que tenemos en FireStore
        docRef.get().addOnSuccessListener { documents ->
                if (documents != null) {
                    rocodromos.clear()
                    rocodromosFiltrados.clear()

                    for (document in documents) {
                        val rocodromo = document.toObject<Rocodromo>()
                        rocodromos.add(rocodromo)
                    }

                    rocodromosFiltrados = rocodromos

                    adapter.submitList(rocodromosFiltrados)
                } else {
                    Toast.makeText(applicationContext, "No hay elementos DB", LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "La DB ha fallado", LENGTH_SHORT).show()
            }
    }

    private fun manejarBusqueda() {
        busqueda = findViewById(R.id.busqueda)
        busqueda.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(busqueda: String?): Boolean {
                filtrar(busqueda)
                return true
            }

            override fun onQueryTextChange(busqueda: String?): Boolean {
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
            // filtrar en repositorio y plasmar resultados en lista

            val resultadoQuery = rocodromos.filter { rocodromo ->
                // lowercase para que ignore las mayusculas o minusculas
                rocodromo.Nombre?.lowercase()?.contains(query.lowercase()) ?: false
            }

            rocodromosFiltrados = ArrayList(resultadoQuery)
            adapter.submitList(rocodromosFiltrados)
        }
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
