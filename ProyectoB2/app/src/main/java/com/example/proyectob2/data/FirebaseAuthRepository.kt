package com.example.proyectob2.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FirebaseAuthRepository {
    // Instancia de FirebaseAuth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Inicia sesión con email y contraseña.
     * @param email El correo del usuario.
     * @param password La contraseña.
     * @param callback Devuelve true si la autenticación es exitosa; de lo contrario, false junto con un mensaje de error.
     */
    fun signIn(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    /**
     * Registra un nuevo usuario con email y contraseña.
     * @param email El correo del usuario.
     * @param password La contraseña.
     * @param callback Devuelve true si el registro es exitoso; de lo contrario, false junto con un mensaje de error.
     */
    fun signUp(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    /**
     * Devuelve el usuario actualmente autenticado (o null si no hay sesión).
     */
    fun currentUser(): FirebaseUser? = auth.currentUser

    /**
     * Cierra la sesión del usuario actual.
     */
    fun signOut() {
        auth.signOut()
    }
}
