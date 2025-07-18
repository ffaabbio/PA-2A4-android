package com.ecodeli.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class LivraisonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livraison_detail)

        // Toolbar config
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Récupération des extras
        val titre = intent.getStringExtra("TITRE") ?: "Sans titre"
        val from = intent.getStringExtra("FROM_CITY") ?: "-"
        val to = intent.getStringExtra("TO_CITY") ?: "-"
        val date = intent.getStringExtra("PREFERRED_DATE") ?: "-"
        val prix = intent.getStringExtra("PRICE") ?: "-"
        val description = intent.getStringExtra("DESCRIPTION") ?: "Aucune description"

        // Affectation aux vues
        findViewById<TextView>(R.id.textViewTitle).text = titre
        findViewById<TextView>(R.id.textViewDescription).text = description
        findViewById<TextView>(R.id.textViewDetails).text = """
            De : $from
            Vers : $to
            Date : $date
            Prix : $prix €
        """.trimIndent()

        // Bouton validation (non fonctionnel pour l’instant)
        findViewById<Button>(R.id.buttonValidate).setOnClickListener {
            Toast.makeText(this, "Validation non implémentée", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

