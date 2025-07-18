package com.ecodeli.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ecodeli.app.model.LoginRequest
import com.ecodeli.app.storage.AuthRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = authRepository.login(request)

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null && body.success) {
                        val user = body.user
                        Toast.makeText(applicationContext, "Bienvenue ${user?.name}", Toast.LENGTH_SHORT).show()

                        // Enregistrement de l'ID utilisateur
                        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                        sharedPref.edit()
                            .putInt("user_id", user?.id ?: -1)
                            .putString("user_name", user?.name)
                            .putString("user_email", user?.email)
                            .apply()

                        // Redirection
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Identifiants incorrects", Toast.LENGTH_SHORT).show()
                    }

                } else if (response.code() == 422) {
                    Toast.makeText(applicationContext, "Format invalide : vérifie l'email et le mot de passe", Toast.LENGTH_LONG).show()

                } else if (response.code() == 401) {
                    Toast.makeText(applicationContext, "Identifiants incorrects", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(applicationContext, "Erreur serveur : ${response.code()}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Log.e("Login", "Exception : ${e.message}", e)
                Toast.makeText(applicationContext, "Erreur réseau : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }




}
