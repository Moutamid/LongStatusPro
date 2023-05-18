package in.whatsaga.whatsapplongerstatus.pro.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Status implements Parcelable {

    public static final int NONE = -1;

    public static final int TYPE_PHOTO = 0;
    public static final int TYPE_VIDEO = 1;
    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @NotNull
        @Contract("_ -> new")
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
    private File file;
    private int type;
    private boolean selected;

    @Contract(pure = true)
    public Status(File file, int type) {
        this.file = file;
        this.type = type;
    }

    protected Status(@NotNull Parcel in) {
        type = in.readInt();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isVideo() {
        return type == TYPE_VIDEO;
    }

    public boolean delete() {
        return file.delete();
    }

    public boolean exists() {
        return file.exists();
    }
}
