package de.endios.openmapstest.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.animation.Animation.INFINITE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import de.endios.openmapstest.R
import de.endios.openmapstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainActivityArrow.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.arrow_rotation).apply {
                repeatCount = INFINITE
            }
        )

        binding.mainActivityOpenMobileButton.setOnClickListener {
            Intent(this, OpenMobileMapsActivity::class.java).startMapsActivity()
        }
        binding.mainActivityOpenStreetButton.setOnClickListener {
            Intent(this, OpenStreetMapsActivity::class.java).startMapsActivity()
        }
    }

    private fun Intent.startMapsActivity() {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(this)
    }
}