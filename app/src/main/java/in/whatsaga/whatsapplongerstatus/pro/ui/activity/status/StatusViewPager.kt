package `in`.whatsaga.whatsapplongerstatus.pro.ui.activity.status

import android.app.Dialog
import android.app.PendingIntent
import android.app.RecoverableSecurityException
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.universalvideoview.UniversalMediaController
import com.universalvideoview.UniversalVideoView
import `in`.whatsaga.whatsapplongerstatus.pro.R
import `in`.whatsaga.whatsapplongerstatus.pro.models.MediaModel
import `in`.whatsaga.whatsapplongerstatus.pro.ui.activity.ShareActivity
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Common
import `in`.whatsaga.whatsapplongerstatus.pro.utils.Constants
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class StatusViewPager : AppCompatActivity(), StatusSlidingImageAdapter.VideoListener,
    View.OnClickListener {
    var viewPager: ViewPager? = null
    var adapter: StatusSlidingImageAdapter? = null
    var list: MutableList<File>? = null
    var pos = 0
    var onlyPaths: MutableList<String> = ArrayList()
    var videoView: UniversalVideoView? = null
    private var videoLayout: FrameLayout? = null
    private var deleteDialog: Dialog? = null
    var file: File? = null
    var someActivityResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_view_pager)
        list = ArrayList()
        pos = intent.getIntExtra("position", -1)
        type = intent.getStringExtra(Constants.TYPE)
        assert(type != null)
        if (type == Constants.BUSINESS) {
            if (Build.VERSION.SDK_INT < 30) {
                file = Common.whatsAppFolderStatus_B
                if (!file!!.exists()) file = Common.whatsAppFolderStatus_B11
            } else {
                //android 11
                file = Common.whatsAppFolderStatus_B11
            }
        } else if (type == Constants.WHATSAPP) {
            if (Build.VERSION.SDK_INT < 30) {
                file = Common.whatsAppFolderStatus
                if (!file!!.exists()) file = Common.whatsAppFolderStatus11
            } else {
                //android 11
                file = Common.whatsAppFolderStatus11
            }
        } else {
            file = Common.Statusbackupfolder
        }
        if (Common.isRorAbove()) {
            onlyPaths = ArrayList()
            var mediaModelList: List<MediaModel>? = null
            if (type == Constants.BUSINESS) {
                mediaModelList = Common.fetchImagesBusiness()
                for (i in mediaModelList.indices) {
                    onlyPaths.add(mediaModelList[i].path)
                }
            } else if (type == Constants.WHATSAPP) {
                mediaModelList = Common.fetchImages()
                for (i in mediaModelList.indices) {
                    onlyPaths.add(mediaModelList[i].path)
                }
            } else {
                val files = file!!.listFiles()!!
                Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE)
                for (mfile in files) {
                    onlyPaths.add(mfile.path)
                }
            }
        } else {
            val files = file!!.listFiles()!!
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE)
            for (mfile in files) {
                if (!mfile.name.startsWith(".")) {
                    if (!list!!.contains(mfile) && !mfile.isDirectory) {
                        list!!.add(mfile)
                    }
                }
            }
        }
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        adapter = if (Common.isRorAbove()) {
            StatusSlidingImageAdapter(onlyPaths, this, this)
        } else {
            StatusSlidingImageAdapter(this, list, this)
        }
        deleteDialog()
        viewPager!!.setAdapter(adapter)
        viewPager!!.setCurrentItem(pos)
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
//                current.setText(i + 1 + "");
                try {
                    if (videoView!!.isPlaying) {
                        videoView!!.stopPlayback()
                        videoLayout!!.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
        val speedDialView = findViewById<SpeedDialView>(R.id.speedDial)
        someActivityResultLauncher = registerForActivityResult<IntentSenderRequest, ActivityResult>(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result: ActivityResult? -> }
        speedDialView.setOnActionSelectedListener { speedDialActionItem: SpeedDialActionItem ->
            when (speedDialActionItem.id) {
                R.id.re_post -> {
                    //                    InterstitialHelper.showAd(this, () -> {
                    val intent = Intent(this@StatusViewPager, ShareActivity::class.java)
                    if (Common.isRorAbove()) {
                        val forIntent = onlyPaths[viewPager!!.getCurrentItem()]
                        intent.putExtra("path", forIntent)
                        intent.putExtra(Constants.TYPE, type)
                    } else {
                        intent.putExtra("path", list!!.get(viewPager!!.getCurrentItem()).path)
                        intent.putExtra(Constants.TYPE, type)
                    }
                    startActivity(intent)
                    //                    });
                    return@setOnActionSelectedListener false
                }
                R.id.deleteStatus -> {
                    deleteDialog!!.show()
                    return@setOnActionSelectedListener false
                }
                R.id.save -> {

//                    InterstitialHelper.showAd(this, () -> {
                    if (Common.isRorAbove()) {
                        downloadFileForEleven(onlyPaths[viewPager!!.getCurrentItem()])
                    } else {
                        downloadFile()
                    }
                    //                    });
                    return@setOnActionSelectedListener true
                }
                else -> return@setOnActionSelectedListener false
            }
        }
        if (type == Constants.SAVED) {
            speedDialView.inflate(R.menu.menu_context_status_saved)
        } else {
            speedDialView.inflate(R.menu.menu_context_status)
        }
    }

    private fun deleteDialog() {
        deleteDialog = Dialog(this, R.style.Theme_Dialog)
        deleteDialog!!.setContentView(R.layout.delete_layout)
        deleteDialog!!.findViewById<View>(R.id.yes).setOnClickListener(this)
        deleteDialog!!.findViewById<View>(R.id.no).setOnClickListener(this)
    }

    val batchDirectoryName: String
        get() {
            var app_folder_path = ""
            app_folder_path = Common.Statusbackupfolder.path
            val dir = File(app_folder_path)
            if (!dir.exists() && !dir.mkdirs()) {
            }
            return app_folder_path
        }

    private fun downloadFileForEleven(uri: String) {
        val file1 = File(uri)
        try {
            val input = contentResolver.openInputStream(Uri.parse(uri))
            try {
                val file = File(batchDirectoryName, file1.name)
                if (file.exists()) {
                    Toast.makeText(this@StatusViewPager, "Already Saved", Toast.LENGTH_SHORT).show()
                    return
                }
                FileOutputStream(file).use { output ->
                    val buffer = ByteArray(4 * 1024) // or other buffer size
                    var read: Int
                    while (input!!.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                }
            } finally {
                input!!.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        refreshGallery(file1)
        finish()
        Toast.makeText(this@StatusViewPager, "Saved", Toast.LENGTH_SHORT).show()
    }

    private fun downloadFile() {
        val path = list!![viewPager!!.currentItem].path
        val file = File(path)
        val what = Common.copyImage(File(path))
        when (what) {
            Common.FILE_COPIED -> {
                finish()
                refreshGallery(file)
            }
            Common.FILE_EXISTS -> Toast.makeText(this, "Already Downloaded", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun refreshGallery(file: File) {
        val savedImageFile = File(Common.Statusbackupfolder, file.name)
        if (file.path.contains(".mp4")) {
//            Toast.makeText(this, "Video Saved", Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(
                this,
                arrayOf(savedImageFile.path),
                arrayOf("video/mp4"),
                null
            )
        } else {
//            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(
                this,
                arrayOf(savedImageFile.path),
                arrayOf("image/jpeg"),
                null
            )
        }
    }

    //    @Override
    //    public void onClick(View v) {
    //        if (v.getId() == R.id.back) {
    //            finish();
    //        }
    //    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.yes -> {
                deleteDialog!!.dismiss()
                delete()
                Toast.makeText(this, resources.getString(R.string.file_deleted), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
            R.id.no -> deleteDialog!!.dismiss()
        }
    }

    private fun delete() {
        try {
            val itemPos = viewPager!!.currentItem
            Log.i("TAG", "itempos : $itemPos")
            val file = File(onlyPaths[itemPos])
            if (Common.isRorAbove()) {
                if (file.name.endsWith(".png") || file.name.endsWith(".jpg") || file.name.endsWith(".jpeg")) {
                    delete(someActivityResultLauncher, Common.getImageContentUri(this, file))
                } else {
                    delete(someActivityResultLauncher, Common.getVideoContentUri(this, file))
                }
            } else {
                file.delete()
            }
            if (Common.isRorAbove()) {
                onlyPaths.removeAt(itemPos)
            } else {
                list!!.removeAt(itemPos)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(launcher: ActivityResultLauncher<IntentSenderRequest>?, uri: Uri) {
        val contentResolver = this@StatusViewPager.contentResolver
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

    //    @Override
    //    public void onBackPressed() {
    //        InterstitialHelper.showAd(this, new InterstitialHelper.OnCloseListener() {
    //            @Override
    //            public void onExit() {
    //                finish();
    //            }
    //        });
    //    }
    override fun onPlayButtonClick(
        pos: Int,
        view: UniversalVideoView,
        video: FrameLayout,
        controller: UniversalMediaController
    ) {
        videoLayout = video
        videoView = view
        videoView!!.setMediaController(controller)
        if (Common.isRorAbove()) {
            videoView!!.setVideoURI(Uri.parse(onlyPaths[pos]))
        } else {
            videoView!!.setVideoURI(Uri.parse(list!![pos].path))
        }
        videoView!!.start()
    }

    companion object {
        var type: String? = null
    }
}