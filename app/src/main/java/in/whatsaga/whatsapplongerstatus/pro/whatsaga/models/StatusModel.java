package in.whatsaga.whatsapplongerstatus.pro.whatsaga.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StatusModel implements Parcelable {
    private String path;
    private String name;
    private String mimeType;
    private boolean isSelected;
    private long lastModified;
    private int mediaType;

    public StatusModel() {
    }

    public StatusModel(String path, String name, String mimeType, long lastModified, int mediaType) {
        this.path = path;
        this.name = name;
        this.mimeType = mimeType;
        this.lastModified = lastModified;
        this.mediaType = mediaType;
    }

    protected StatusModel(Parcel in) {
        path = in.readString();
        name = in.readString();
        mimeType = in.readString();
        isSelected = in.readByte() != 0;
        lastModified = in.readLong();
        mediaType = in.readInt();
    }

    public static final Creator<StatusModel> CREATOR = new Creator<StatusModel>() {
        @Override
        public StatusModel createFromParcel(Parcel in) {
            return new StatusModel(in);
        }

        @Override
        public StatusModel[] newArray(int size) {
            return new StatusModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        dest.writeString(mimeType);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeLong(lastModified);
        dest.writeInt(mediaType);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }
}