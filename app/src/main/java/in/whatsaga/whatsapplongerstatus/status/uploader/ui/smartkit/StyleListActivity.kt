package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.smartkit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
import `in`.whatsaga.whatsapplongerstatus.status.uploader.utils.Common

class StyleListActivity : AppCompatActivity() {
    private var tempIndexList: MutableList<Int>? = null
    private var mFinalData: MutableList<String>? = null
    private var spaceList: MutableList<Int>? = null
    var circleList: List<Char>? = null
    var squaredList: List<Int>? = null
    var filledCircleList: List<Int>? = null
    var notFilledSquareList: List<Int>? = null
    var turnedList: List<Char>? = null
    var doubleStruckList: List<Int>? = null
    var scriptList: List<Int>? = null
    var boldScriptList: List<Int>? = null
    private var letterList: CharArray
        get() {
            TODO()
        }
        set(value) {}
    private var recyclerView: RecyclerView? = null
    var text: String? = null
    var adapter: StyleAdapter? = null
   
    private var retryAttempt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.setLocale(this)
        setContentView(R.layout.activity_style_list)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        text = intent.getStringExtra("text")
        tempIndexList = ArrayList()
        mFinalData = ArrayList()
        spaceList = ArrayList()
        letterList = Common.getLetterList()
        circleList = Common.getCircleList()
        squaredList = Common.getNegativeSquaredList()
        filledCircleList = Common.getFilledCircleList()
        notFilledSquareList = Common.getNotFilledSquaredList()
        turnedList = Common.getTurnedList()
        doubleStruckList = Common.getDoubleStuckList()
        scriptList = Common.getScriptList()
        boldScriptList = Common.getBoldScriptList()
        convert()
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.setHasFixedSize(false)
        recyclerView!!.setLayoutManager(LinearLayoutManager(this, RecyclerView.VERTICAL, false))
        adapter = StyleAdapter(mFinalData as ArrayList<String>, this)
        recyclerView!!.setAdapter(adapter)
    }

    private fun convert() {
        mFinalData!!.clear()
        tempIndexList!!.clear()
        spaceList!!.clear()
        val textArray = text!!.toCharArray()
        for (i in textArray.indices) {
            if (textArray[i] == ' ') {
                spaceList!!.add(i)
            }
        }
        for (i in textArray.indices) {
            for (j in letterList.indices) {
                if (textArray[i] == letterList[j]) {
                    tempIndexList!!.add(j)
                }
            }
        }
        for (i in spaceList!!.indices) {
            tempIndexList!!.add(spaceList!![i], -2)
        }
        val builder = StringBuilder()
        val boldBuilder = StringBuilder()
        val scriptBuilder = StringBuilder()
        val turnedBuilder = StringBuilder()
        val squaredBuilder = StringBuilder()
        val filledCircleBuilder = StringBuilder()
        val notFilledSquareBuilder = StringBuilder()
        val doubleBuilder = StringBuilder()
        for (i in tempIndexList!!.indices) {
            if (tempIndexList!![i] == -2) {
                builder.append(" ")
                squaredBuilder.append(" ")
                filledCircleBuilder.append(" ")
                notFilledSquareBuilder.append(" ")
                turnedBuilder.append(" ")
                doubleBuilder.append(" ")
                scriptBuilder.append(" ")
                boldBuilder.append(" ")
            } else {
                builder.append(circleList!![tempIndexList!![i]])
                turnedBuilder.append(turnedList!![tempIndexList!![i]])
                squaredBuilder.append(getEmojiByUnicode(squaredList!![tempIndexList!![i]]))
                filledCircleBuilder.append(getEmojiByUnicode(filledCircleList!![tempIndexList!![i]]))
                notFilledSquareBuilder.append(
                    getEmojiByUnicode(
                        notFilledSquareList!![tempIndexList!![i]]
                    )
                )
                doubleBuilder.append(getEmojiByUnicode(doubleStruckList!![tempIndexList!![i]]))
                scriptBuilder.append(getEmojiByUnicode(scriptList!![tempIndexList!![i]]))
                boldBuilder.append(getEmojiByUnicode(boldScriptList!![tempIndexList!![i]]))
            }
        }
        mFinalData!!.add(builder.toString())
        mFinalData!!.add(squaredBuilder.toString())
        mFinalData!!.add(filledCircleBuilder.toString())
        mFinalData!!.add(notFilledSquareBuilder.toString())
        mFinalData!!.add(turnedBuilder.toString())
        mFinalData!!.add(doubleBuilder.toString())
        mFinalData!!.add(scriptBuilder.toString())
        mFinalData!!.add(boldBuilder.toString())
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    override fun onBackPressed() {

        super.onBackPressed()
    }
}