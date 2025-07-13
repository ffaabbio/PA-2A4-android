package com.ecodeli.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        val textViewAbonnement = findViewById<TextView>(R.id.textViewAbonnement)
        val buttonLogout = findViewById<Button>(R.id.buttonLogout)

        // FAKE DATA
        textViewName.text = "Nom : Fabio Dugay"
        textViewEmail.text = "Email : fabio@ecodeli.com"
        textViewAbonnement.text = "Abonnement : Premium"

        buttonLogout.setOnClickListener {
            Toast.makeText(this, "Déconnexion (à implémenter plus tard)", Toast.LENGTH_SHORT).show()
            // Plus tard → retour LoginActivity
            // val intent = Intent(this, LoginActivity::class.java)
            // startActivity(intent)
            // finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
