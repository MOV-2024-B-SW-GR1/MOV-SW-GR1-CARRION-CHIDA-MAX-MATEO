package com.example.proyectob2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectob2.R
import com.example.proyectob2.data.FirebaseAuthRepository
import com.example.proyectob2.ui.ChatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuthRepository.signIn(email, password) { success, message ->
                    if (success) {
                        // Redirige a ChatActivity en lugar de MainActivity
                        startActivity(Intent(this, ChatActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Ingrese email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        btnSignUp.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuthRepository.signUp(email, password) { success, message ->
                    if (success) {
                        // Redirige a ChatActivity en lugar de MainActivity
                        startActivity(Intent(this, ChatActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Ingrese email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
