package com.ecodeli.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class LivraisonDetailActivity : AppCompatActivity() {

    private var statutLivraison = "En cours"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livraison_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val titre = intent.getStringExtra("TITRE") ?: "Titre inconnu"

        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewDetails = findViewById<TextView>(R.id.textViewDetails)
        val buttonValidate = findViewById<Button>(R.id.buttonValidate)

        textViewTitle.text = titre
        textViewDescription.text = "Superbe annonce de transport à valider."

        val fakeDetails = """
            Type : Transport
            Prix : 25.00 €
            Poids : 10 kg
            Volume : 0.5 m³
            Contraintes : Aucun
            Statut : $statutLivraison
            De : Paris
            À : Marseille
            Date souhaitée : 15/07/2025
        """.trimIndent()

        textViewDetails.text = fakeDetails

        buttonValidate.setOnClickListener {
            if (statutLivraison == "Livrée") {
                Toast.makeText(this, "Cette livraison est déjà validée.", Toast.LENGTH_SHORT).show()
            } else {
                statutLivraison = "Livrée"
                val updatedDetails = fakeDetails.replace("En cours", statutLivraison)
                textViewDetails.text = updatedDetails
                Toast.makeText(this, "Livraison validée !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
