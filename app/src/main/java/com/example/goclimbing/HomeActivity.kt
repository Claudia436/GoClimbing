package com.example.goclimbing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewPager: ViewPager2
    var rocodromos = ArrayList<Rocodromo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        createFragment()
        cargarBaseDeDatos()
        cargarFotos()

        findViewById<Button>(R.id.menu).setOnClickListener() {
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.all).setOnClickListener() {
            val intent = Intent(this,ListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createFragment() {
        val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    private fun cargarFotos() {
        viewPager = findViewById(R.id.viewPager)
        val photoList = listOf(
            R.drawable.roco17_1,
            R.drawable.roco26_1,
            R.drawable.roco7_1,
            R.drawable.roco16_1,
            R.drawable.roco19_1
        )

        val adapter = FotoAdapter(photoList)
        viewPager.adapter = adapter
    }

    private fun cargarBaseDeDatos() {
        val database = Firebase.firestore

        val docRef = database.collection("Rocodromos") // nombre de la coleccion que tenemos en FireStore
        docRef.get().addOnSuccessListener { documents ->
            if (documents != null) {
                rocodromos.clear()
                for (document in documents) {
                    val rocodromo = document.toObject<Rocodromo>()
                    rocodromos.add(rocodromo)
                }
                rocodromos.forEach {
                    val latitud = it.Latitud?.toDoubleOrNull()
                    val longitud = it.Longitud?.toDoubleOrNull()
                    val coords = LatLng(latitud!!, longitud!!)
                    val marker = MarkerOptions().position(coords).title(it.Nombre)
                    map.addMarker(marker)

                    if (it.Nombre == "Urban Monkey Madrid") {
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(coords, 10f),
                            4000,
                            null
                        )
                    }
                }
            } else {
                Toast.makeText(applicationContext, "No hay elementos DB", Toast.LENGTH_SHORT).show()
            }
        }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "La DB ha fallado", Toast.LENGTH_SHORT).show()
            }
    }
}