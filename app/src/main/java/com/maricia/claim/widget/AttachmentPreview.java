package com.maricia.claim.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.maricia.claim.R;
import com.maricia.claim.model.Attachment;
import com.maricia.claim.util.ActionCommand;

public class AttachmentPreview extends CardView {

    private Attachment attachment;
    private ImageView preview;


    public AttachmentPreview(Context context) {
        super(context);
        initialize(context);
    }

    public AttachmentPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public AttachmentPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_attachment_preview,this,true);
        preview = (ImageView) getChildAt(0);

    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
        preview.setImageDrawable(null);

        if (attachment != null) {
            new UpdatePreviewCommand().exec(attachment);
        }
    }

    private class UpdatePreviewCommand extends ActionCommand<Attachment, Drawable> {

        @Override
        public Drawable onBackground(Attachment value) throws Exception {
            switch (attachment.getType()) {
                case IMAGE:
                    return new BitmapDrawable(getResources(), attachment.getFile().getAbsolutePath());
            }

            return getResources().getDrawable(R.drawable.ic_unknown_file_type);

        }

        @Override
        public void onForeground(Drawable value) {
            preview.setImageDrawable(value);
        }

    }


}
