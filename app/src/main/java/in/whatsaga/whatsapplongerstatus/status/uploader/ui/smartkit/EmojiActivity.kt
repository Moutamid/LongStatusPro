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
import androidx.appcompat.widget.Toolbar
import com.fxn.stash.Stash
import com.google.android.gms.ads.AdView

import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
import `in`.whatsaga.whatsapplongerstatus.status.uploader.adsense.Ads
import `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.MainActivity
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants
import java.util.*

class EmojiActivity : AppCompatActivity(), View.OnClickListener {
    var ed_emoji: EditText? = null
    var ed_text: EditText? = null
    var emoji: String? = null
    var text: String? = null
    var patternList: MutableList<String>? = null
    var letterList = Common.getLetterList()
    var finalResultIndex: MutableList<Int>? = null
    var resultList: MutableList<String>? = null
    private var finalText: String? = null
    var result: TextView? = null
   
    private var retryAttempt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_emoji)

        var banner: AdView = findViewById(R.id.adView)
        Ads.calledIniti(this)
        if (!Stash.getBoolean(Constants.IS_PRO, false)) {
            Ads.showBannerAd(banner)
        }

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        patternList = ArrayList()
        finalResultIndex = ArrayList()
        resultList = ArrayList()
        result = findViewById<TextView>(R.id.result)
        ed_emoji = findViewById<EditText>(R.id.ed_emoji)
        ed_text = findViewById<EditText>(R.id.ed_text)
        emoji = getEmojiByUnicode(0x1F60A)
        findViewById<View>(R.id.convert).setOnClickListener(this)
        findViewById<View>(R.id.copy).setOnClickListener(this)
        findViewById<View>(R.id.whatsapp).setOnClickListener(this)
        findViewById<View>(R.id.clear).setOnClickListener(this)
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
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
                result!!.text = ""
                finalResultIndex!!.clear()
                resultList!!.clear()
                text = ed_text!!.text.toString().uppercase(Locale.getDefault())
                emoji = ed_emoji!!.text.toString()
                if (text!!.length == 0) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.enter_text),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (emoji!!.length == 0) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.enter_emoji),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val flag = emoji!!.matches(".*[a-zA-Z].*".toRegex())
                if (!flag) {
                    ed_emoji!!.setText(emoji)
                    initPatternList()
                    val textArray = text!!.toCharArray()
                    for (aTextArray in textArray) {
                        var j = 0
                        while (j < letterList.size) {
                            if (aTextArray == letterList[j]) {
                                finalResultIndex!!.add(j)
                            }
                            j++
                        }
                    }
                    var i = 0
                    while (i < finalResultIndex!!.size) {
                        val res =
                            patternList!![finalResultIndex!![i]].replace("e".toRegex(), emoji!!)
                        val res2 = res.replace("s".toRegex(), "\t")
                        val res3 = res2.replace("n".toRegex(), "\n")
                        val res4 = res3.replace(" ".toRegex(), "")
                        val resFinal = res3.replace("\"".toRegex(), "")
                        resultList!!.add(resFinal)
                        i++
                    }
                    val builder = StringBuilder()
                    for (details in resultList!!) {
                        builder.append(
                            """
    $details
    
    """.trimIndent()
                        )
                    }
                    finalText = builder.toString()
                    result!!.text = builder.toString()
                } else {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.select_emoji),
                        Toast.LENGTH_SHORT
                    ).show()
                    ed_emoji!!.isFocusable = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initPatternList() {
        val resources = resources
        patternList!!.add(resources.getString(R.string.a))
        patternList!!.add(resources.getString(R.string.b))
        patternList!!.add(resources.getString(R.string.c))
        patternList!!.add(resources.getString(R.string.d))
        patternList!!.add(resources.getString(R.string.e))
        patternList!!.add(resources.getString(R.string.f))
        patternList!!.add(resources.getString(R.string.g))
        patternList!!.add(resources.getString(R.string.h))
        patternList!!.add(resources.getString(R.string.i))
        patternList!!.add(resources.getString(R.string.j))
        patternList!!.add(resources.getString(R.string.k))
        patternList!!.add(resources.getString(R.string.l))
        patternList!!.add(resources.getString(R.string.m))
        patternList!!.add(resources.getString(R.string.n))
        patternList!!.add(resources.getString(R.string.o))
        patternList!!.add(resources.getString(R.string.p))
        patternList!!.add(resources.getString(R.string.q))
        patternList!!.add(resources.getString(R.string.r))
        patternList!!.add(resources.getString(R.string.s))
        patternList!!.add(resources.getString(R.string.t))
        patternList!!.add(resources.getString(R.string.u))
        patternList!!.add(resources.getString(R.string.v))
        patternList!!.add(resources.getString(R.string.w))
        patternList!!.add(resources.getString(R.string.x))
        patternList!!.add(resources.getString(R.string.y))
        patternList!!.add(resources.getString(R.string.z))
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