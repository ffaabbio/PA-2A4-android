package com.ecodeli.app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class LivraisonsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livraisons)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val container = findViewById<LinearLayout>(R.id.containerLivraisons)

        val livraisons = listOf(
            LivraisonFake("Colis #1", "Paris", "Marseille", "25.00 €", "2025-07-15"),
            LivraisonFake("Colis #2", "Lyon", "Lille", "40.00 €", "2025-07-18"),
            LivraisonFake("Colis #3", "Montpellier", "Rennes", "30.50 €", "2025-07-20")
        )

        livraisons.forEach { livraison ->
            val livraisonView = layoutInflater.inflate(R.layout.item_livraison, null)

            livraisonView.findViewById<TextView>(R.id.textViewTitle).text = livraison.titre
            livraisonView.findViewById<TextView>(R.id.textViewVilles).text =
                "${livraison.fromCity} → ${livraison.toCity}"
            livraisonView.findViewById<TextView>(R.id.textViewPrix).text = livraison.prix
            livraisonView.findViewById<TextView>(R.id.textViewDate).text = livraison.date

            livraisonView.findViewById<android.widget.Button>(R.id.buttonVoirDetail).setOnClickListener {
                val intent = Intent(this, LivraisonDetailActivity::class.java)
                intent.putExtra("TITRE", livraison.titre)
                startActivity(intent)
            }

            container.addView(livraisonView)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

data class LivraisonFake(
    val titre: String,
    val fromCity: String,
    val toCity: String,
    val prix: String,
    val date: String
)
