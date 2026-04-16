package com.example.lab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCall.setOnClickListener {
            val phone = getString(R.string.contact_phone)
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            safeStartActivity(intent)
        }

        binding.btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // Только почтовые приложения [cite: 562]
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.contact_email)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            }
            safeStartActivity(intent)
        }

        binding.btnMap.setOnClickListener {
            val label = getString(R.string.map_label)
            val geoUri = Uri.parse("geo:0,0?q=60.0237,30.2289($label)")
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            safeStartActivity(intent)
        }

        binding.btnShare.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
            }
            val chooser = Intent.createChooser(sendIntent, getString(R.string.share_title))
            startActivity(chooser)
        }
    }


    private fun safeStartActivity(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.error_no_app), Toast.LENGTH_SHORT).show()
        }
    }
}