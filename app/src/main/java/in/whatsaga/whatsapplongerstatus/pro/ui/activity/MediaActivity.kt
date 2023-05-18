package `in`.whatsaga.whatsapplongerstatus.pro.ui.activity

import android.app.Dialog
import android.app.PendingIntent
import android.app.RecoverableSecurityException
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import `in`.whatsaga.whatsapplongerstatus.pro.R
import `in`.whatsaga.whatsapplongerstatus.pro.adapters.MediaAdapter
import `in`.whatsaga.whatsapplongerstatus.pro.models.MediaModel
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Common
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Constants
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

class MediaActivity : AppCompatActivity(), MediaAdapter.MediaListener, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    var list: MutableList<MediaModel>? = null
    var path: File? = null
    private var adapter: MediaAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var not_found_layout: LinearLayout? = null
    var selectionView: RelativeLayout? = null
    var topBar: RelativeLayout? = null
    var countText: TextView? = null
    var selectAll: ToggleButton? = null
    private var deleteDialog: Dialog? = null
    var type: String? = null
   
    private var retryAttempt = 0
    var someActivityResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_media)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        deleteDialog()
        list = ArrayList()
        not_found_layout = findViewById<LinearLayout>(R.id.not_found)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        findViewById<View>(R.id.back).setOnClickListener(this)
        findViewById<View>(R.id.back_toolbar).setOnClickListener(this)
        selectAll = findViewById<ToggleButton>(R.id.select_all)
        selectAll!!.setOnCheckedChangeListener(this)
        findViewById<View>(R.id.delete).setOnClickListener(this)
        selectionView = findViewById<RelativeLayout>(R.id.selection_view)
        topBar = findViewById<RelativeLayout>(R.id.top)
        countText = findViewById<TextView>(R.id.count)
        someActivityResultLauncher = registerForActivityResult<IntentSenderRequest, ActivityResult>(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result: ActivityResult? -> }
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    private fun getList() {
        type = intent.getStringExtra(Constants.TYPE)
        path = if (type == Constants.DELETED_IMAGES) {
            Common.ImageRecovery
        } else {
            Common.ImageRecovery
        }
        list!!.clear()
        if (path != null) if (path!!.exists()) {
            if (path!!.listFiles() != null) {
                val files = path!!.listFiles()!!
                Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE)
                for (mfile in files) {
                    if (!mfile.name.startsWith(".")) {
                        if (!list!!.contains<Any?>(mfile) && !mfile.isDirectory) {
                            if (type == Constants.DELETED_IMAGES) {
                                if (Common.isImageFile(mfile.path)) {
                                    val mediaModel = MediaModel()
                                    mediaModel.path = mfile.path
                                    mediaModel.isSelected = false
                                    list!!.add(mediaModel)
                                }
                            } else if (type == Constants.DELETED_VIDEOS) {
                                if (Common.isVideoFile(mfile.path)) {
                                    val mediaModel = MediaModel()
                                    mediaModel.path = mfile.path
                                    mediaModel.isSelected = false
                                    list!!.add(mediaModel)
                                }
                            } else {
                                if (mfile.path.contains(".opus")) {
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
        }
        Log.i("TAG", "Deleted List size : " + list!!.size)
        adapter = MediaAdapter(this, list, this, type)
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

    override fun onClick(pos: Int, count: Int) {
        if (count == 0) {
            selectionView!!.visibility = View.GONE
        }
        countText!!.text = count.toString() + ""
    }

    override fun onLongClick(pos: Int, count: Int) {
        selectionView!!.visibility = View.VISIBLE
        countText!!.text = count.toString() + ""
    }

    private fun deleteDialog() {
        deleteDialog = Dialog(this, R.style.Theme_Dialog)
        deleteDialog!!.setContentView(R.layout.delete_layout)
        deleteDialog!!.findViewById<View>(R.id.yes).setOnClickListener(this)
        deleteDialog!!.findViewById<View>(R.id.no).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.delete -> deleteDialog!!.show()
            R.id.back -> {
                selectionView!!.visibility = View.GONE
                topBar!!.visibility = View.VISIBLE
                adapter!!.unSelectAll()
                adapter!!.notifyDataSetChanged()
                countText!!.text = "0"
                adapter!!.setSelectionValue()
            }
            R.id.yes -> {
                deleteDialog!!.dismiss()
                deleteFiles()
            }
            R.id.no -> deleteDialog!!.dismiss()
            R.id.back_toolbar -> onBackPressed()
        }
    }

    private fun deleteFiles() {
        selectAll!!.isChecked = false
        selectionView!!.visibility = View.GONE
        topBar!!.visibility = View.VISIBLE
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
        val contentResolver = this@MediaActivity.contentResolver
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



    override fun onBackPressed() {

        super.onBackPressed()
    }
}