package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.smartkit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common

class EmojiListActivity : AppCompatActivity(), View.OnClickListener {
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
        setContentView(R.layout.activity_emoji_list)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        findViewById<View>(R.id.copy).setOnClickListener(this)
        findViewById<View>(R.id.whatsapp).setOnClickListener(this)
        patternList = ArrayList()
        finalResultIndex = ArrayList()
        resultList = ArrayList()
        result = findViewById<TextView>(R.id.result)
        emoji = intent.getStringExtra("emoji")
        text = intent.getStringExtra("text")
        initPatternList()
        val textArray = text!!.toCharArray()
        for (aTextArray in textArray) {
            for (j in letterList.indices) {
                if (aTextArray == letterList[j]) {
                    (finalResultIndex as ArrayList<Int>).add(j)
                }
            }
        }
        for (i in (finalResultIndex as ArrayList<Int>).indices) {
            val res = (patternList as ArrayList<String>).get((finalResultIndex as ArrayList<Int>).get(i)).replace("e".toRegex(), emoji!!)
            val res2 = res.replace("s".toRegex(), "\t")
            val res3 = res2.replace("n".toRegex(), "\n")
            val res4 = res3.replace(" ".toRegex(), "")
            val resFinal = res3.replace("\"".toRegex(), "")
            (resultList as ArrayList<String>).add(resFinal)
        }
        val builder = StringBuilder()
        for (details in resultList as ArrayList<String>) {
            builder.append(details + "\n")
        }
        finalText = builder.toString()
        result!!.setText(builder.toString())
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