package com.ecodeli.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.ecodeli.app.storage.AnnonceRepository
import kotlinx.coroutines.launch

class PrestationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestation_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Données transmises
        val titre = intent.getStringExtra("TITRE") ?: "-"
        val description = intent.getStringExtra("DESCRIPTION") ?: "-"
        val date = intent.getStringExtra("PREFERRED_DATE") ?: "-"
        val prix = intent.getStringExtra("PRICE") ?: "-"
        val status = intent.getStringExtra("STATUS") ?: "-"

        // Affectation aux vues
        findViewById<TextView>(R.id.textViewTitle).text = titre
        findViewById<TextView>(R.id.textViewDescription).text = description
        findViewById<TextView>(R.id.textViewDetails).text = """
            Date souhaitée : $date
            Prix : $prix €
            Statut : $status
        """.trimIndent()

        val id = intent.getIntExtra("ID", -1)

        findViewById<Button>(R.id.buttonValidate).setOnClickListener {
            if (id != -1) {
                validatePrestation(id)
            } else {
                Toast.makeText(this, "ID de prestation invalide", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validatePrestation(id: Int) {
        lifecycleScope.launch {
            try {
                val userId = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .getInt("user_id", -1)

                if (userId == -1) {
                    Toast.makeText(this@PrestationDetailActivity, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val response = AnnonceRepository().validerPrestation(id, userId)

                if (response.isSuccessful) {
                    Toast.makeText(this@PrestationDetailActivity, "Prestation validée avec succès", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@PrestationDetailActivity, "Erreur : ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PrestationDetailActivity, "Erreur réseau : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
