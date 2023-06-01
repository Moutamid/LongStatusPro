package in.whatsaga.whatsapplongerstatus.pro.notification;
import static in.whatsaga.whatsapplongerstatus.pro.utils.NotificationHelper.ACTION_READ;
import static in.whatsaga.whatsapplongerstatus.pro.utils.NotificationHelper.ACTION_REPLY;
import static in.whatsaga.whatsapplongerstatus.pro.utils.NotificationHelper.EXTRA_PACK;
import static in.whatsaga.whatsapplongerstatus.pro.utils.NotificationHelper.EXTRA_TITLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.RemoteInput;

import in.whatsaga.whatsapplongerstatus.pro.persistence.Repository;
import in.whatsaga.whatsapplongerstatus.pro.services.NLService;
import in.whatsaga.whatsapplongerstatus.pro.utils.NotificationHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

public class NotificationActionReceiver extends BroadcastReceiver {

    @Nullable
    private String getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            CharSequence message = remoteInput.getCharSequence(ACTION_REPLY);
            if (message != null) {
                return message.toString();
            }
        }
        return null;
    }

    @Override
    public void onReceive(Context context, @NotNull Intent intent) {
        String action = intent.getAction();
        Timber.i("Action : %s", action);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String pack = intent.getStringExtra(EXTRA_PACK);
        Timber.i("Title : %s", title);
        if (action == null || title == null)
            return;
        if (action.equals(ACTION_REPLY)) {
            Timber.i("Replying to : %s", title);
            String messageText = getMessageText(intent);
            Timber.i("Message : " + messageText);
            NLService.reply(context, title, messageText,pack);
        } else if (action.equals(ACTION_READ)) {
            Timber.i("Marking as read : %s", title);
        }
        markAsRead(title);
        NotificationHelper.cancelNotification(context, title.hashCode());
    }

    public void markAsRead(String title) {
        Repository.INSTANCE.markAsRead(title);
    }
}
