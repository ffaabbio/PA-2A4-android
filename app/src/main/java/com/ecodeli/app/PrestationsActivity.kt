package com.ecodeli.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.ecodeli.app.model.AnnoncePrestation
import com.ecodeli.app.storage.AnnonceRepository
import kotlinx.coroutines.launch

class PrestationsActivity : AppCompatActivity() {

    private val DETAIL_REQUEST_CODE = 1001
    private lateinit var container: LinearLayout
    private val repository = AnnonceRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestations)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        container = findViewById(R.id.containerPrestations)

        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", -1)
        Log.d("Prestations", "User ID envoyé : $userId")

        if (userId != -1) {
            loadPrestations(userId)
        } else {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadPrestations(userId: Int) {
        lifecycleScope.launch {
            try {
                val response = repository.getPrestationAnnonces(userId)
                Log.d("Prestations", "Réponse brute = ${response.body()?.prestations}")

                if (response.isSuccessful && response.body()?.success == true) {
                    val prestations = response.body()?.prestations ?: emptyList()
                    Log.d("Prestations", "Nombre de prestations : ${prestations.size}")
                    displayPrestations(prestations)
                } else {
                    Toast.makeText(this@PrestationsActivity, "Erreur serveur : ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("Prestations", "Erreur : ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(this@PrestationsActivity, "Exception : ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                Log.e("Prestations", "Exception : ${e.message}", e)
            }
        }
    }

    private fun displayPrestations(prestations: List<AnnoncePrestation>) {
        val inflater = layoutInflater
        container.removeAllViews()

        for (prestation in prestations) {
            val view = inflater.inflate(R.layout.item_prestation, container, false)

            view.findViewById<TextView>(R.id.textViewPrestationTitle).text = prestation.title
            view.findViewById<TextView>(R.id.textViewPrestationType).text = prestation.description
            view.findViewById<TextView>(R.id.textViewPrestationDate).text = prestation.preferred_date
            view.findViewById<TextView>(R.id.textViewPrestationStatus).text = prestation.status

            view.findViewById<Button>(R.id.buttonVoirDetailPrestation).setOnClickListener {
                val intent = Intent(this, PrestationDetailActivity::class.java)
                intent.putExtra("ID", prestation.id)
                intent.putExtra("USER_ID", prestation.user_id)
                intent.putExtra("TITRE", prestation.title)
                intent.putExtra("DESCRIPTION", prestation.description)
                intent.putExtra("PREFERRED_DATE", prestation.preferred_date)
                intent.putExtra("PRICE", prestation.price)
                intent.putExtra("STATUS", prestation.status)
                startActivityForResult(intent, DETAIL_REQUEST_CODE)
            }

            container.addView(view)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", -1)
            if (userId != -1) {
                loadPrestations(userId)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
