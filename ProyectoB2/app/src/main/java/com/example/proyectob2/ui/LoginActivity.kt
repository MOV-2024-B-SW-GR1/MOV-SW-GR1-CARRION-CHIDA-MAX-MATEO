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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoogleSignIn: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configurar Google Sign-In
        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id)) // Configurar en strings.xml
                .build()
        )

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)

        // Lógica para login con email y contraseña
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuthRepository.signIn(email, password) { success, message ->
                    if (success) {
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

        // Lógica para login con Google
        btnGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    // Manejar resultado del inicio de sesión con Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Error en login con Google: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Autenticación con Firebase usando Google
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        if (account == null) {
            Toast.makeText(this, "Fallo la autenticación con Google", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, ChatActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error en autenticación con Firebase", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
