package `in`.whatsaga.whatsapplongerstatus.pro.ui.smartkit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import `in`.whatsaga.whatsapplongerstatus.pro.R
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Common
import java.util.concurrent.TimeUnit

class RepeaterListActivity : AppCompatActivity(), View.OnClickListener {
    var finalList: MutableList<String?>? = null
    var result: TextView? = null
    private var finalText: String? = null
    var nextLine = false
    var text: String? = null
    var number = 0
   
    private var retryAttempt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_repeater_list)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        findViewById<View>(R.id.copy).setOnClickListener(this)
        findViewById<View>(R.id.whatsapp).setOnClickListener(this)
        finalList = ArrayList()
        result = findViewById<TextView>(R.id.result)
        text = intent.getStringExtra("text")
        number = intent.getIntExtra("number", 1)
        nextLine = intent.getBooleanExtra("next", false)
        repeat()
    }

    private fun repeat() {
        for (i in 0 until number) {
            finalList!!.add(text)
        }
        val builder = StringBuilder()
        if (nextLine) {
            for (details in finalList!!) {
                builder.append(
                    """
    $details
    
    """.trimIndent()
                )
            }
        } else {
            for (details in finalList!!) {
                builder.append(details)
            }
        }
        finalText = builder.toString()
        result!!.text = finalText
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.copy -> {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("emoji", finalText)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, resources.getString(R.string.copied), Toast.LENGTH_SHORT)
                    .show()
            }
            R.id.whatsapp -> {
                val sharingWhatsApp = Intent(Intent.ACTION_SEND)
                sharingWhatsApp.type = "text/plain"
                sharingWhatsApp.putExtra(Intent.EXTRA_TEXT, finalText)
                sharingWhatsApp.setPackage("com.whatsapp")
                try {
                    startActivity(sharingWhatsApp)
                } catch (e: Exception) {
                    Toast.makeText(this, "Whatsapp not found", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }



    override fun onBackPressed() {

        super.onBackPressed()
    }
}