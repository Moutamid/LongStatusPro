package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.status

import android.app.Dialog
import android.app.PendingIntent
import android.app.RecoverableSecurityException
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
import `in`.whatsaga.whatsapplongerstatus.status.uploader.adapters.StatusAdapter
import `in`.whatsaga.whatsapplongerstatus.status.uploader.models.MediaModel
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Constants
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.io.File
import java.util.*

class BusinessActivity : AppCompatActivity(), StatusAdapter.OnItemClick, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    var list: MutableList<MediaModel>? = null
    var path: File? = null
    private var adapter: StatusAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var not_found_layout: LinearLayout? = null
    var selectionView: RelativeLayout? = null
    var countText: TextView? = null
    var selectAll: ToggleButton? = null
    private var deleteDialog: Dialog? = null
   
    private var retryAttempt = 0
    var someActivityResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
    private fun deleteDialog() {
        deleteDialog = Dialog(this, R.style.Theme_Dialog)
        deleteDialog!!.setContentView(R.layout.delete_layout)
        deleteDialog!!.findViewById<View>(R.id.yes).setOnClickListener(this)
        deleteDialog!!.findViewById<View>(R.id.no).setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)

        list = ArrayList()
        if (Build.VERSION.SDK_INT < 30) {
            path = Common.whatsAppFolderStatus_B
            if (!path!!.exists()) path = Common.whatsAppFolderStatus_B11
        } else {
            //android 11
            path = Common.whatsAppFolderStatus_B11
        }
        findViewById<View>(R.id.back).setOnClickListener(this)
        selectAll = findViewById<ToggleButton>(R.id.select_all)
        selectAll!!.setOnCheckedChangeListener(this)
        findViewById<View>(R.id.delete).setOnClickListener(this)
        findViewById<View>(R.id.business).setOnClickListener(this)
        selectionView = findViewById<RelativeLayout>(R.id.selection_view)
        countText = findViewById<TextView>(R.id.count)
        not_found_layout = findViewById<LinearLayout>(R.id.not_found)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        deleteDialog()
        someActivityResultLauncher = registerForActivityResult<IntentSenderRequest, ActivityResult>(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result: ActivityResult? -> }
    }

    public override fun onResume() {
        super.onResume()
        getList()
    }

    private fun getList() {
        list!!.clear()
        if (Common.isRorAbove()) {
            if (path != null) if (path!!.exists()) {
                try {
                    list = Common.fetchImagesBusiness()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            if (path != null) if (path!!.exists()) {
                if (path!!.listFiles() != null) {
                    val files = path!!.listFiles()!!
                    Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE)
                    for (mfile in files) {
                        if (!mfile.name.startsWith(".")) {
                            if (!list!!.contains<Any?>(mfile) && !mfile.isDirectory) {
                                val mediaModel = MediaModel()
                                mediaModel.path = mfile.path
                                mediaModel.isSelected = false
                                list!!.add(mediaModel)
                            }
                        }
                    }
                }
            }
        }
        adapter =
            StatusAdapter(
                this,
                list,
                this,
                Constants.BUSINESS
            )
        recyclerView!!.layoutManager = GridLayoutManager(this, 3)
        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
        checkAdapterSize()
    }

    private fun checkAdapterSize() {
        if (adapter!!.getItemCount() > 0) {
            not_found_layout!!.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE
        } else {
            recyclerView!!.visibility = View.GONE
            not_found_layout!!.visibility = View.VISIBLE
        }
    }

    override fun onItemClicked(pos: Int, count: Int) {
        if (count == 0) {
            selectionView!!.visibility = View.GONE
        }
        countText!!.text = count.toString() + ""
    }

    override fun onLongClick(pos: Int, count: Int) {
        selectionView!!.visibility = View.VISIBLE
        countText!!.text = count.toString() + ""
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.delete -> deleteDialog!!.show()
            R.id.back -> defaultValues()
            R.id.business -> {
                val launchIntent = packageManager.getLaunchIntentForPackage("com.whatsapp.w4b")
                startActivity(launchIntent)
            }
            R.id.yes -> {
                deleteDialog!!.dismiss()
                deleteFiles()
            }
            R.id.no -> deleteDialog!!.dismiss()
        }
    }

    private fun defaultValues() {
        selectionView!!.visibility = View.GONE
        adapter!!.unSelectAll()
        adapter!!.notifyDataSetChanged()
        countText!!.text = "0"
        adapter!!.setSelectionValue()
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            adapter!!.selectAll()
            adapter!!.notifyDataSetChanged()
            countText!!.text = list!!.size.toString() + ""
        } else {
            adapter!!.unSelectAll()
            adapter!!.notifyDataSetChanged()
            countText!!.text = "0"
        }
    }

    private fun deleteFiles() {
        selectAll!!.isChecked = false
        selectionView!!.visibility = View.GONE
        var i = 0
        while (i < list!!.size) {
            val mediaModel = list!![i]
            if (mediaModel.isSelected) {
                val file = File(mediaModel.path)
                if (Common.isRorAbove()) {
                    if (file.name.endsWith(".png") || file.name.endsWith(".jpg")) {
                        delete(someActivityResultLauncher, Common.getImageContentUri(this, file))
                    } else {
                        delete(someActivityResultLauncher, Common.getVideoContentUri(this, file))
                    }
                } else {
                    file.delete()
                }
                list!!.removeAt(i)
                adapter!!.notifyItemRemoved(i)
                adapter!!.notifyItemRangeChanged(i, list!!.size)
                i--
            }
            i++
        }
        adapter!!.setSelectionValue()
        checkAdapterSize()
    }

    fun delete(launcher: ActivityResultLauncher<IntentSenderRequest>?, uri: Uri) {
        val contentResolver = this@BusinessActivity.contentResolver
        try {

            //delete object using resolver
            contentResolver.delete(uri, null, null)
        } catch (e: SecurityException) {
            var pendingIntent: PendingIntent? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val collection = ArrayList<Uri>()
                collection.add(uri)
                pendingIntent = MediaStore.createDeleteRequest(contentResolver, collection)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                //if exception is recoverable then again send delete request using intent
                if (e is RecoverableSecurityException) {
                    pendingIntent = e.userAction.actionIntent
                }
            }
            if (pendingIntent != null) {
                val sender = pendingIntent.intentSender
                val request = IntentSenderRequest.Builder(sender).build()
                launcher!!.launch(request)
            }
        }
    }



    override fun onBackPressed() {

        super.onBackPressed()
    }
}