package `in`.whatsaga.whatsapplongerstatus.pro.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import `in`.whatsaga.whatsapplongerstatus.pro.R
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Common
import java.util.concurrent.TimeUnit

class FeedbackActivity : AppCompatActivity() {
    var editText: EditText? = null
    var textToSend: String? = null
   
    private var retryAttempt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_feedback)

        editText = findViewById<EditText>(R.id.ed_text)
        findViewById<View>(R.id.submit).setOnClickListener(View.OnClickListener {
            textToSend = editText!!.getText().toString()
            if (textToSend == "") {
                Toast.makeText(this@FeedbackActivity, "Write feedback", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("appssupertech@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        })
    }



    override fun onBackPressed() {

        super.onBackPressed()
    }
}