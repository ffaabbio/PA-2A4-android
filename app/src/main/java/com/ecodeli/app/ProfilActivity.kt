package com.ecodeli.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.ecodeli.app.storage.AuthRepository
import kotlinx.coroutines.launch

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val name = sharedPref.getString("user_name", "Nom inconnu")
        val email = sharedPref.getString("user_email", "Email inconnu")

        findViewById<TextView>(R.id.textViewName).text = "Nom : $name"
        findViewById<TextView>(R.id.textViewEmail).text = "Email : $email"

        val buttonLogout = findViewById<Button>(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            try {
                val response = AuthRepository().logout()

                if (response.isSuccessful) {
                    getSharedPreferences("user_prefs", MODE_PRIVATE)
                        .edit().clear().apply()

                    val intent = Intent(this@ProfilActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@ProfilActivity, "Erreur lors de la déconnexion", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfilActivity, "Erreur réseau : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
