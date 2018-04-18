package com.maricia.claim.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;


public class Attachment implements Parcelable {

    public enum Type {
        IMAGE,
        UNKNOWN;

        public static Type safe(final Type type) {
            // Use a ternary to replace null with UNKNOWN
            return type != null ? type : UNKNOWN;
        }
    }

    File file;
    Type type;



    public Attachment(final File file, final Type type) {
        this.file = file;
        this.type = Type.safe(type);
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

    public File getFile() { return file; }
    public void setFile(final File file) {
        this.file = file;
    }

    public Type getType() { return type; }
    public void setType(final Type type) {
        this.type = Type.safe(type);
    }

    protected Attachment(final Parcel in) {
        file = new File(in.readString());
        type = Type.values()[in.readInt()];
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(file.getAbsolutePath());
        dest.writeInt(type.ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

}//end Attachment
