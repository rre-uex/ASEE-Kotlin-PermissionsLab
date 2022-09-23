package es.unex.giiis.asee.permissionslabkotlin

//import android.Manifest.permission.READ_CONTACTS
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private val Tag = MainActivity::class.java.simpleName
        private const val MyPermissionsRequestReadContacts = 101
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i(Tag, "Permission to read contacts granted")
                buttonReadContacts.isEnabled = true
            } else {
                Log.i(Tag, "Permission to read contacts not granted")
                buttonReadContacts.isEnabled = false
            }
        }

    private lateinit var buttonReadContacts: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonReadContacts = findViewById(R.id.buttonReadContacts)
        buttonReadContacts.setOnClickListener {
            readContacts()
        }

        buttonReadContacts.isEnabled = false
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i(Tag, "Permission is already granted")
                buttonReadContacts.isEnabled = true
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                Log.i(Tag, "Permission is not granted: shouldShowRequestPermissionRationale()")
            } else -> {
                Log.i(Tag, "Permission is not granted: request")
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Log.i(Tag, "Permission result obtained")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MyPermissionsRequestReadContacts -> {
                buttonReadContacts.isEnabled = (
                        grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
            }
        }
    }

    private fun readContacts() {
        // TODO Read contacts
        Log.i(Tag, "Reading contacts...")
    }
}
