package com.ecodeli.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.ecodeli.app.model.AnnonceTransport
import kotlinx.coroutines.launch
import com.ecodeli.app.storage.AnnonceRepository

class LivraisonsActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout
    private val repository = AnnonceRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livraisons)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        container = findViewById(R.id.containerLivraisons)

        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE)
            .getInt("user_id", -1)

        if (userId != -1) {
            loadLivraisons(userId)
        } else {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadLivraisons(userId: Int) {
        lifecycleScope.launch {
            try {
                val response = repository.getTransportAnnonces(userId)
                if (response.isSuccessful && response.body()?.success == true) {
                    val annonces = response.body()?.annonces ?: emptyList()
                    displayLivraisons(annonces)
                } else {
                    Toast.makeText(this@LivraisonsActivity, "Erreur serveur : ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("Livraisons", "Erreur : ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(this@LivraisonsActivity, "Erreur réseau : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                Log.e("Livraisons", "Exception : ${e.message}", e)
            }
        }
    }

    private fun displayLivraisons(annonces: List<AnnonceTransport>) {
        val inflater = LayoutInflater.from(this)
        container.removeAllViews()

        for (annonce in annonces) {
            val view = inflater.inflate(R.layout.item_livraison, container, false)

            view.findViewById<TextView>(R.id.textViewTitle).text = annonce.title
            view.findViewById<TextView>(R.id.textViewVilles).text = "${annonce.from_city} → ${annonce.to_city}"
            view.findViewById<TextView>(R.id.textViewPrix).text = "${annonce.price} €"
            view.findViewById<TextView>(R.id.textViewDate).text = annonce.preferred_date

            view.findViewById<Button>(R.id.buttonVoirDetail).setOnClickListener {
                val intent = Intent(this, LivraisonDetailActivity::class.java)
                intent.putExtra("TITRE", annonce.title)
                intent.putExtra("FROM_CITY", annonce.from_city)
                intent.putExtra("TO_CITY", annonce.to_city)
                intent.putExtra("PREFERRED_DATE", annonce.preferred_date)
                intent.putExtra("PRICE", annonce.price)
                intent.putExtra("DESCRIPTION", annonce.description)
                startActivity(intent)
            }

            container.addView(view)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
