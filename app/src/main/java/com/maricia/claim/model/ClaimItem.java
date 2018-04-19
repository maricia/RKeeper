package com.maricia.claim.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ClaimItem implements Parcelable {

    String description;
    double amount;
    Date timestamp;
    Category category;
    List<Attachment> attachments = new ArrayList<>();

    public ClaimItem() {
    }
    //getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    //add, remove, and list attachments
    public void addAttachment(final Attachment attachment) {
        if ((attachment != null) && !attachments.contains(attachment)) {
            attachments.add(attachment);
        }
    }
    public void removeAttachment(final Attachment attachment) {
        attachments.remove(attachment);
    }
    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }


    //parcelable methods
    protected ClaimItem(Parcel in) {
        description = in.readString();
        amount = in.readDouble();
        final long time = in.readLong();
        timestamp = time != -1 ? new Date(time) : null;
        final int categoryOrd = in.readInt();
        category = categoryOrd != -1 ? Category.values()[categoryOrd] : null;
        attachments = in.createTypedArrayList(Attachment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeTypedList(attachments);
        dest.writeLong(timestamp != null ? timestamp.getTime() : -1);
        dest.writeInt(category != null ? category.ordinal() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClaimItem> CREATOR = new Creator<ClaimItem>() {
        @Override
        public ClaimItem createFromParcel(Parcel in) {

            return new ClaimItem(in);
        }

        @Override
        public ClaimItem[] newArray(int size)
        {
            return new ClaimItem[size];
        }
    };

}
