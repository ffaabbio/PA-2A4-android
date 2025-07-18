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

class LivraisonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livraison_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val titre = intent.getStringExtra("TITRE") ?: "Sans titre"
        val from = intent.getStringExtra("FROM_CITY") ?: "-"
        val to = intent.getStringExtra("TO_CITY") ?: "-"
        val date = intent.getStringExtra("PREFERRED_DATE") ?: "-"
        val status = intent.getStringExtra("STATUS") ?: "-"
        val prix = intent.getStringExtra("PRICE") ?: "-"
        val description = intent.getStringExtra("DESCRIPTION") ?: "Aucune description"

        findViewById<TextView>(R.id.textViewTitle).text = titre
        findViewById<TextView>(R.id.textViewDescription).text = description
        findViewById<TextView>(R.id.textViewDetails).text = """
        De : $from
        Vers : $to
        Date : $date
        Prix : $prix €
        Statut : $status
    """.trimIndent()

        val id = intent.getIntExtra("ID", -1)

        findViewById<Button>(R.id.buttonValidate).setOnClickListener {
            if (id != -1) {
                validateLivraison(id)
            } else {
                Toast.makeText(this, "ID utilisateur ou livraison invalide", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun validateLivraison(id: Int) {
        lifecycleScope.launch {
            try {
                val userId = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .getInt("user_id", -1)

                if (userId == -1) {
                    Toast.makeText(this@LivraisonDetailActivity, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val response = AnnonceRepository().validerLivraison(id, userId)

                if (response.isSuccessful) {
                    Toast.makeText(this@LivraisonDetailActivity, "Livraison validée avec succès", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@LivraisonDetailActivity, "Erreur : ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LivraisonDetailActivity, "Erreur réseau : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

