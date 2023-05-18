package in.whatsaga.whatsapplongerstatus.pro.services;

import android.content.Context;

import in.whatsaga.whatsapplongerstatus.pro.persistence.Repository;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BadgeHelper {

    public static int updateBadgeCount(Context context) {
        int badgeCount = Repository.INSTANCE.getUnreadCount();
        ShortcutBadger.applyCount(context, badgeCount);
        return badgeCount;
    }

    public static void updateBadgeCount(Context context, String messageTitle) {
        Repository.INSTANCE.markAsRead(messageTitle);
        ShortcutBadger.applyCount(context, Repository.INSTANCE.getUnreadCount());
    }
}
