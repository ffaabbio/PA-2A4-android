package com.ecodeli.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class PrestationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestations)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val container = findViewById<LinearLayout>(R.id.containerPrestations)

        // FAKE DATA
        val prestations = listOf(
            PrestationFake("Courses alimentaires", "Courses", "2025-07-20", "En cours"),
            PrestationFake("Garde d'animaux", "Service", "2025-07-22", "Terminée"),
            PrestationFake("Jardinage", "Service", "2025-07-25", "En attente")
        )

        prestations.forEach { prestation ->
            val prestationView = layoutInflater.inflate(R.layout.item_prestation, null)

            prestationView.findViewById<TextView>(R.id.textViewPrestationTitle).text = prestation.title
            prestationView.findViewById<TextView>(R.id.textViewPrestationType).text = "Type : ${prestation.type}"
            prestationView.findViewById<TextView>(R.id.textViewPrestationDate).text = "Date : ${prestation.date}"
            prestationView.findViewById<TextView>(R.id.textViewPrestationStatus).text = "Statut : ${prestation.status}"

            val buttonVoirDetail = prestationView.findViewById<Button>(R.id.buttonVoirDetailPrestation)
            buttonVoirDetail.setOnClickListener {
                val intent = Intent(this, PrestationDetailActivity::class.java)
                intent.putExtra("TITLE", prestation.title)
                intent.putExtra("TYPE", prestation.type)
                intent.putExtra("DATE", prestation.date)
                intent.putExtra("STATUS", prestation.status)
                intent.putExtra("DESCRIPTION", "Ceci est une superbe prestation effectuée via EcoDeli.")
                startActivity(intent)
            }

            container.addView(prestationView)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

data class PrestationFake(
    val title: String,
    val type: String,
    val date: String,
    val status: String
)
