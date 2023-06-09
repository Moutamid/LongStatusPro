package `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.fragment

import `in`.whatsaga.whatsapplongerstatus.status.uploader.R
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
import `in`.whatsaga.whatsapplongerstatus.status.uploader.adapters.LastMessageAdapter
import `in`.whatsaga.whatsapplongerstatus.status.uploader.models.UnseenViewModel
import `in`.whatsaga.whatsapplongerstatus.status.uploader.persistence.LastMessage
import `in`.whatsaga.whatsapplongerstatus.status.uploader.ui.activity.ChatActivity
import timber.log.Timber

class ChatFragment : Fragment() {

    private lateinit var unseenViewModel: UnseenViewModel
    private lateinit var adapter: LastMessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewDeleted: CardView
    private lateinit var notFound: LinearLayout
    private lateinit var clickHandler: ClickHandler
    private lateinit var delete: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_chat_fragment, container, false)

        clickHandler = ClickHandler();
        delete = view.findViewById(R.id.delete);
        notFound = view.findViewById(R.id.not_found);
        viewDeleted = view.findViewById(R.id.viewDelete);
        recyclerView = view.findViewById(R.id.recyclerView);
        unseenViewModel = ViewModelProvider(this).get(UnseenViewModel::class.java)
        unseenViewModel.getAllLastMessages().observe(viewLifecycleOwner, Observer { setAdapter(it) })
        adapter =
            LastMessageAdapter(
                object :
                    LastMessageAdapter.OnMessageClickListener {
                    override fun onMessageClick(hiddenMessage: LastMessage, position: Int) {
                        ChatActivity.start(
                            context,
                            hiddenMessage.msgTitle,
                            hiddenMessage.pack
                        )
                    }

                    override fun onSelectionStarted() {
                        Timber.i("onSelectionStarted")
                        viewDeleted.visibility = View.VISIBLE
                    }

                    override fun updateCount(selectedCount: Int) {
                        Timber.i("updateCount : %s", selectedCount)
                    }

                    override fun onSelectionFinished() {
                        Timber.i("onSelectionFinished")
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
