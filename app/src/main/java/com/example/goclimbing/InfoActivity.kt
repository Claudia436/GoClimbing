package com.example.goclimbing

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import java.io.IOException

class InfoActivity : AppCompatActivity() {
    private lateinit var nombre: TextView
    private lateinit var descripcion: TextView
    private lateinit var tarifa: TextView
    private lateinit var contacto: TextView
    private lateinit var horario: TextView
    private lateinit var ubicacion: TextView
    private lateinit var instalaciones: TextView
    private lateinit var imagen: ImageView

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
        imagen = findViewById(R.id.imagen)

        // cargamos el id del rocodromo que nos viene en el ListActivity
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
                        if (rocodromo.Id.equals(rocodromoId)) {
                            cargarRocodromo(rocodromo)
                            break //si se encuetra un rocodomo se sale/ cierra el for
                        }
                    }
                } else {
                    Toast.makeText(applicationContext, "no hay elementos bd!", LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "la DB ha fallado!", LENGTH_SHORT).show()
            }
    }

    private fun cargarRocodromo(rocodromo: Rocodromo) {
        nombre.text = rocodromo.Nombre
        descripcion.text = rocodromo.Descripcion
        tarifa.text = rocodromo.Tarifas
        contacto.text = rocodromo.Web
        ubicacion.text = rocodromo.Ubicacion
        horario.text = rocodromo.Horario
        instalaciones.text = rocodromo.Instalaciones

        cargarImagen(rocodromo.Imagen)
    }

    private fun cargarImagen(nombreImagen: String?) {
        if (!nombreImagen.isNullOrEmpty()) {
            try {
                val resID = resources.getIdentifier(nombreImagen, "drawable", packageName)
                imagen.setImageResource(resID)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}