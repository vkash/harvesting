package ua.com.vkash.harvesting.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ua.com.vkash.harvesting.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val actionPackageInstalled = "ua.com.vkash.harvesting.SESSION_API_PACKAGE_INSTALLED"

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            HarvestingApp()
        }

        addOnNewIntentListener {
            onPackageInstalledIntent(it)
        }
    }

    @SuppressLint("UnsafeIntentLaunch")
    @Suppress("DEPRECATION")
    private fun onPackageInstalledIntent(intent: Intent) {
        if (intent.action == actionPackageInstalled) {
            intent.extras?.run {
                val status = getInt(PackageInstaller.EXTRA_STATUS)
                val message = getString(PackageInstaller.EXTRA_STATUS_MESSAGE)

                when (status) {
                    PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                        val confirmIntent =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                getParcelable(Intent.EXTRA_INTENT, Intent::class.java)
                            } else {
                                getParcelable(Intent.EXTRA_INTENT) as? Intent
                            }
                        if (confirmIntent != null) {
                            startActivity(confirmIntent)
                        }
                    }

                    PackageInstaller.STATUS_SUCCESS -> {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.install_successful,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle(R.string.app_name)
                            setMessage(message)
                            setPositiveButton(android.R.string.ok, null)
                        }.show()
                    }
                }
            }
        }
    }
}
