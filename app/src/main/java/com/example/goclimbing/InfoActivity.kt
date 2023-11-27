package com.example.goclimbing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class InfoActivity : AppCompatActivity() {
    private lateinit var nombre: TextView
    private lateinit var descripcion: TextView
    private lateinit var tarifa: TextView
    private lateinit var contacto: TextView
    private lateinit var horario: TextView
    private lateinit var ubicacion: TextView
    private lateinit var instalaciones: TextView
    //private lateinit var imagen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        nombre = findViewById(R.id.nombre)
        descripcion = findViewById(R.id.descripcion)
        tarifa = findViewById(R.id.tarifa)
        contacto = findViewById(R.id.contacto)
        horario = findViewById(R.id.horario)
        ubicacion = findViewById(R.id.ubicacion)
        instalaciones = findViewById(R.id.instalaciones)
        //imagen = findViewById(R.id.imagen)

        val rocodromoId = intent.getStringExtra("rocodromoID") ?: ""
        cargarBaseDeDatos(rocodromoId)
    }

    private fun cargarBaseDeDatos(rocodromoId: String) {
        //database = Firebase.database
        val database = Firebase.firestore

        val docRef = database.collection("Rocodromos")
        docRef.get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents) {
                        val rocodromo = document.toObject<Rocodromo>()
                        Log.d("rocodromo", "${rocodromo}")

                        if (rocodromo.Id.equals(rocodromoId)) {
                            cargarRocodromo(rocodromo)
                            break
                        }
                    }
                } else {
                    Log.d("document", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("", "get failed with ", exception)
            }
    }

    fun cargarRocodromo(rocodromo: Rocodromo) {
        nombre.text = rocodromo.Nombre
        descripcion.text = rocodromo.Descripcion
        tarifa.text = rocodromo.Tarifas
        contacto.text = rocodromo.Web
        ubicacion.text = rocodromo.Ubicacion
        horario.text = rocodromo.Horario
        //imagen.text = rocodromo.Imagen
        instalaciones.text = rocodromo.Instalaciones

    }
}