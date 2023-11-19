package com.example.goclimbing

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var correo: EditText
    private lateinit var contra: EditText
    private lateinit var error: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialise()
    }

    private fun initialise() {
        auth = FirebaseAuth.getInstance()
        correo = findViewById(R.id.correo)
        contra = findViewById(R.id.contra)
    }

    private fun createNewAccount() {
        email = correo.text.toString()
        password = contra.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"createUserWithEmail:success")
                    val user:FirebaseUser = auth.currentUser!!
                    verifyEmail(user)
                    Toast.makeText(baseContext,"Usuario registrado correctamente",Toast.LENGTH_LONG).show()
                    updateUserInfoAndGoHome()
                }else {
                    Log.w(TAG,"createUserWithEmail:failure",task.exception)
                    error = task.exception.toString().substringAfter(":")
                    Toast.makeText(baseContext, error, Toast.LENGTH_LONG).show()
                }
            }
        }else {
            Toast.makeText(this,"Complete todos los campos",Toast.LENGTH_SHORT).show()
        }
    }

    fun register(view: View) {
        createNewAccount()
    }

    private  fun updateUserInfoAndGoHome() {
        val intent = Intent(this,HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Email "+user.getEmail(),Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this,"Error al verificar el correo",Toast.LENGTH_SHORT).show()
            }
        }
    }
}