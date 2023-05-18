package `in`.whatsaga.whatsapplongerstatus.pro.ui.smartkit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import `in`.whatsaga.whatsapplongerstatus.pro.R

class StyleAdapter(private val mDataset: List<String>, private val context: Context) :
    RecyclerView.Adapter<StyleAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater

    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var textView: TextView
        var share: MaterialCardView
        var whatsapp: MaterialCardView
        var copy: MaterialCardView

        init {
            textView = v.findViewById(R.id.textView)
            share = v.findViewById(R.id.share)
            whatsapp = v.findViewById(R.id.whatsapp)
            copy = v.findViewById(R.id.copy)
            share.setOnClickListener(this)
            whatsapp.setOnClickListener(this)
            copy.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.copy -> {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("emoji", textView.text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
                }
                R.id.share -> {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(Intent.EXTRA_TEXT, textView.text)
                    sendIntent.type = "text/plain"
                    context.startActivity(Intent.createChooser(sendIntent, "share via"))
                }
                R.id.whatsapp -> {
                    val sharingWhatsApp = Intent(Intent.ACTION_SEND)
                    sharingWhatsApp.type = "text/plain"
                    sharingWhatsApp.putExtra(Intent.EXTRA_TEXT, textView.text)
                    sharingWhatsApp.setPackage("com.whatsapp")
                    try {
                        context.startActivity(sharingWhatsApp)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Whatsapp not found", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val v: View = inflater.inflate(R.layout.stylish_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = mDataset[position]
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }
}