package in.whatsaga.whatsapplongerstatus.pro.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HiddenMessage.class, LastMessage.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "WHAT_SAVE_DATABASE";

    private static AppDatabase INSTANCE;

    static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract HiddenMessageDao hiddenMessageDao();

    public abstract LastMessageDao lastMessageDao();
}
