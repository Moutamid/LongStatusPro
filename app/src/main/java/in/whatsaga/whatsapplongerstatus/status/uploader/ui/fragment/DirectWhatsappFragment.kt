package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common
import java.net.URLEncoder

class DirectWhatsappFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var edNumber: EditText? = null
    var edMessage: EditText? = null
    var ccp: com.hbb20.CountryCodePicker? = null
    var countryCode: String? = null
    var number: String? = null
    var numberWithCountryCode: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_direct_whatsapp, container, false)
        ccp = view.findViewById<com.hbb20.CountryCodePicker>(R.id.ccp22)
        edNumber = view.findViewById<EditText>(R.id.ed_number)
        edMessage = view.findViewById<EditText>(R.id.ed_msg)
        view.findViewById<View>(R.id.send).setOnClickListener(this)
        view.findViewById<View>(R.id.share).setOnClickListener(this)
        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.send -> {
                if (!Common.isAppInstalled(
                        context, "com.whatsapp"
                    )
                ) {
                    Toast.makeText(context, "Whatsapp not installed", Toast.LENGTH_SHORT).show()
                    return
                }
                countryCode = ccp!!.selectedCountryCode
                number = edNumber!!.text.toString()
                if (number!!.length != 0) {
                    send("com.whatsapp")
                } else {
                    Toast.makeText(context, "Enter Number", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.share -> shareMessage(edMessage!!.text.toString())
        }
    }

    private fun shareMessage(message: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "share via"))
    }

    private fun send(pack: String) {
        numberWithCountryCode = try {
            if (number!![0].code == 0) {
                number!!.replace("(^|[^0-9])0+".toRegex(), countryCode!!)
            } else {
                countryCode + number
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Enter Number", Toast.LENGTH_SHORT).show()
            return
        }
        val packageManager = requireContext().packageManager
        val i = Intent(Intent.ACTION_VIEW)
        try {
            val url =
                "https://api.whatsapp.com/send?phone=$numberWithCountryCode&text=" + URLEncoder.encode(
                    edMessage!!.text.toString(), "UTF-8"
                )
            i.setPackage(pack)
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): DirectWhatsappFragment {
            val fragment = DirectWhatsappFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}