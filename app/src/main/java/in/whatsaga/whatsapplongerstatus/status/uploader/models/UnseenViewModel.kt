package `in`.whatsaga.whatsapplongerstatus.status.uploader.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import `in`.whatsaga.whatsapplongerstatus.status.uploader.persistence.Repository

class UnseenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Unseen Fragment"
    }
    val text: LiveData<String> = _text
    fun getAllLastMessages() = Repository.INSTANCE.allLastMessages
    fun getAllBusinessLastMessages() = Repository.INSTANCE.allBusinessLastMessages

}