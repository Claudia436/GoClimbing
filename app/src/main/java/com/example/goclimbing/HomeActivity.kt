package com.example.goclimbing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        createFragment()

        findViewById<Button>(R.id.menu).setOnClickListener() {
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.all).setOnClickListener() {
            val intent = Intent(this,ListActivity::class.java)
            startActivity(intent)
        }

        cargarFotos()
    }

    private fun createFragment() {
        val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
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

    private fun createMarker() {
        val coords1 = LatLng(40.32013843149239, -3.7481422711146606)
        val marker1 = MarkerOptions().position(coords1).title("Soul Climb Leganés")
        map.addMarker(marker1)

        val coords2 = LatLng(40.858726216658674, -3.6138302711082906)
        val marker2 = MarkerOptions().position(coords2).title("Honey Rock Climbing")
        map.addMarker(marker2)

        val coords3 = LatLng(40.45279980627598, -3.7075078711079548)
        val marker3 = MarkerOptions().position(coords3).title("Arkose Madrid")
        map.addMarker(marker3)

        val coords4 = LatLng(40.34234153717938, -3.534463242295697)
        val marker4 = MarkerOptions().position(coords4).title("La Reunión Escalada")
        map.addMarker(marker4)

        val coords5 = LatLng(40.31193214491777, -3.703092228807229)
        val marker5 = MarkerOptions().position(coords5).title("Indoorwall Getafe")
        map.addMarker(marker5)

        val coords6 = LatLng(40.49371038327535, -3.3415138597026695)
        val marker6 = MarkerOptions().position(coords6).title("Planet Vertical")
        map.addMarker(marker6)

        val coords7 = LatLng(40.498524222247966, -3.8907942288008606)
        val marker7 = MarkerOptions().position(coords7).title("Sputnik Climbing Las Rozas")
        map.addMarker(marker7)

        val coords8 = LatLng(40.74925883710439, -3.774217454156258)
        val marker8 = MarkerOptions().position(coords8).title("La Roca Del Fénix")
        map.addMarker(marker8)

        val coords9 = LatLng(40.437902667807435, -3.620241186441132)
        val marker9 = MarkerOptions().position(coords9).title("Sharma Climbing Madrid")
        map.addMarker(marker9)

        val coords10 = LatLng(40.230796486565204, -3.778836928798178)
        val marker10 = MarkerOptions().position(coords10).title("Gorila Búlder")
        map.addMarker(marker10)

        val coords11 = LatLng(40.39154064367174, -3.6696252999560843)
        val marker11 = MarkerOptions().position(coords11).title("Boulder Madrid")
        map.addMarker(marker11)

        val coords12 = LatLng(40.63187841533779, -4.018273957843146)
        val marker12 = MarkerOptions().position(coords12).title("Indoor Climb Planet Villalba")
        map.addMarker(marker12)

        val coords13 = LatLng(40.465480180851294, -3.695938757625522)
        val marker13 = MarkerOptions().position(coords13).title("Rockomadrid")
        map.addMarker(marker13)

        val coords14 = LatLng(40.434544974083224, -3.707001199950049)
        val marker14 = MarkerOptions().position(coords14).title("Salamandra Boulder Café")
        map.addMarker(marker14)

        val coords15 = LatLng(40.34205201818519, -3.848263742275584)
        val marker15 = MarkerOptions().position(coords15).title("Climbat X-Madrid")
        map.addMarker(marker15)

        val coords16 = LatLng(40.53608182359771, -3.6567766729707643)
        val marker16 = MarkerOptions().position(coords16).title("Sputnik Climbing Alcobendas")
        map.addMarker(marker16)

        val coords17 = LatLng(40.34692106454365, -3.8008947827460196)
        val marker17 = MarkerOptions().position(coords17).title("The Climb")
        map.addMarker(marker17)

        val coords18 = LatLng(40.40178658936863, -3.6975197711146617)
        val marker18 = MarkerOptions().position(coords18).title("Urban Monkey Madrid")
        map.addMarker(marker18)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coords18,10f),
            4000,
            null
        )

        val coords19 = LatLng(40.62846932555582, -4.01728884228933)
        val marker19 = MarkerOptions().position(coords19).title("Urban Monkey Villalba")
        map.addMarker(marker19)

        val coords20 = LatLng(40.45079603882325, -3.4826508615574334)
        val marker20 = MarkerOptions().position(coords20).title("Indoorwall Torrejón")
        map.addMarker(marker20)

        val coords21 = LatLng(40.20752597797398, -3.68406035760306)
        val marker21 = MarkerOptions().position(coords21).title("Wild Cats")
        map.addMarker(marker21)

        val coords22 = LatLng(40.431944848742496, -3.654161913452263)
        val marker22 = MarkerOptions().position(coords22).title("Espacio Acción")
        map.addMarker(marker22)

        val coords23 = LatLng(40.28512818366679, -3.7834521892363404)
        val marker23 = MarkerOptions().position(coords23).title("Awesome Boulder Center")
        map.addMarker(marker23)

        val coords24 = LatLng(40.55907714629092, -3.61784629995843)
        val marker24 = MarkerOptions().position(coords24).title("Tsunami Climb")
        map.addMarker(marker24)

        val coords25 = LatLng(40.33386879126016, -3.8178965585212974)
        val marker25 = MarkerOptions().position(coords25).title("Yurok Climbing Center")
        map.addMarker(marker25)

        val coords26 = LatLng(40.70369686935297, -3.953109999952386)
        val marker26 = MarkerOptions().position(coords26).title("RockGame Moralzarzal")
        map.addMarker(marker26)
    }
}