package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.smartkit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.fxn.stash.Stash
import com.google.android.gms.ads.AdView

import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
import `in`.whatsaga.whatsapplongerstatus.status.uploader.adsense.Ads
import `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.MainActivity
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants

class TextRepeaterActivity : AppCompatActivity(), View.OnClickListener {
    var ed_text: EditText? = null
    var ed_number: EditText? = null
    var isNextLine: SwitchCompat? = null
    var text: String? = null
    var number = 0
    var finalList: MutableList<String>? = null
    var result: TextView? = null
    private var finalText: String? = null
   
    private var retryAttempt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_text_repeater)

        var banner: AdView = findViewById(R.id.adView)
        Ads.calledIniti(this)
        if (!Stash.getBoolean(Constants.IS_PRO, false)) {
            Ads.showBannerAd(banner)
        }

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        finalList = ArrayList()
        ed_text = findViewById<EditText>(R.id.ed_text)
        ed_number = findViewById<EditText>(R.id.ed_emoji)
        isNextLine = findViewById<SwitchCompat>(R.id.isNextLine)
        result = findViewById<TextView>(R.id.result)
        findViewById<View>(R.id.convert).setOnClickListener(this)
        findViewById<View>(R.id.copy).setOnClickListener(this)
        findViewById<View>(R.id.whatsapp).setOnClickListener(this)
        findViewById<View>(R.id.clear).setOnClickListener(this)
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
            R.id.clear -> result!!.text = " "
            R.id.convert -> {
                finalList!!.clear()
                text = ed_text!!.text.toString()
                if (text!!.length == 0) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.enter_text),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                number = if (ed_number!!.text.toString().length == 0) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.enter_number),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else {
                    ed_number!!.text.toString().toInt()
                }
                var i = 0
                while (i < number) {
                    finalList!!.add(text!!)
                    i++
                }
                val builder = StringBuilder()
                if (isNextLine!!.isChecked) {
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
        }
    }



    override fun onBackPressed() {
        if (Stash.getBoolean(Constants.IS_PRO, false)) {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else {
            Ads.loadIntersAD(this, this, MainActivity::class.java)
        }
    }
}