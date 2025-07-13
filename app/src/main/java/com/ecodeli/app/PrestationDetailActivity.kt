package com.ecodeli.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class PrestationDetailActivity : AppCompatActivity() {

    private var statutPrestation = "En cours"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestation_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val titre = intent.getStringExtra("TITLE") ?: "Titre inconnu"
        val type = intent.getStringExtra("TYPE") ?: "Type inconnu"
        val date = intent.getStringExtra("DATE") ?: "Date inconnue"
        val status = intent.getStringExtra("STATUS") ?: "Statut inconnu"
        val description = intent.getStringExtra("DESCRIPTION") ?: "Aucune description"

        statutPrestation = status

        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewDetails = findViewById<TextView>(R.id.textViewDetails)
        val buttonValidate = findViewById<Button>(R.id.buttonValidate)

        textViewTitle.text = titre
        textViewDescription.text = description

        val fakeDetails = """
            Type : $type
            Date souhaitée : $date
            Statut : $statutPrestation
        """.trimIndent()

        textViewDetails.text = fakeDetails

        buttonValidate.setOnClickListener {
            if (statutPrestation == "Terminée") {
                Toast.makeText(this, "Cette prestation est déjà validée.", Toast.LENGTH_SHORT).show()
            } else {
                statutPrestation = "Terminée"
                val updatedDetails = fakeDetails.replace(status, statutPrestation)
                textViewDetails.text = updatedDetails
                Toast.makeText(this, "Prestation validée !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
