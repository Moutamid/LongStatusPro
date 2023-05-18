package `in`.whatsaga.whatsapplongerstatus.pro.ui.fragment

import `in`.whatsaga.whatsapplongerstatus.pro.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import `in`.whatsaga.whatsapplongerstatus.pro.adapters.LastMessageAdapter
import `in`.whatsaga.whatsapplongerstatus.pro.models.UnseenViewModel
import `in`.whatsaga.whatsapplongerstatus.pro.persistence.LastMessage
import `in`.whatsaga.whatsapplongerstatus.pro.ui.activity.ChatActivity

class BusinessChatFragment : Fragment() {

    private lateinit var unseenViewModel: UnseenViewModel
    private lateinit var adapter: LastMessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewDeleted: CardView
    private lateinit var clickHandler: ClickHandler
    private lateinit var delete: Button
    private lateinit var notFound: LinearLayout


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_business_fragment, container, false)

        clickHandler = ClickHandler();
        delete = view.findViewById(R.id.delete);
        viewDeleted = view.findViewById(R.id.viewDelete);
        notFound = view.findViewById(R.id.not_found);
        recyclerView = view.findViewById(R.id.recyclerView);
        unseenViewModel = ViewModelProvider(this).get(UnseenViewModel::class.java)
        unseenViewModel.getAllBusinessLastMessages()
            .observe(viewLifecycleOwner, Observer { setAdapter(it) })
        adapter = LastMessageAdapter(object : LastMessageAdapter.OnMessageClickListener {
            override fun onMessageClick(hiddenMessage: LastMessage, position: Int) {
                ChatActivity.start(context, hiddenMessage.msgTitle, hiddenMessage.pack)
            }

            override fun onSelectionStarted() {
                viewDeleted.visibility = View.VISIBLE
            }

            override fun updateCount(selectedCount: Int) {
            }

            override fun onSelectionFinished() {
                viewDeleted.visibility = View.GONE

            }
        })

        recyclerView.layoutManager = LinearLayoutManager(context);
        recyclerView.adapter = adapter;

        delete.setOnClickListener {
            clickHandler.onDeleteClick()
        }

        return view
    }

    private fun setAdapter(messages: List<LastMessage>) {
        adapter.clearList()
        adapter.addAll(messages)
        recyclerView.adapter;
        if (adapter.size() == 0) {
            notFound.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            notFound.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    inner class ClickHandler {
        fun onDeleteClick() {
            adapter.deleteSelected()
        }
    }
}
