package com.maricia.claim.ui.attachments;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.maricia.claim.model.Attachment;
import com.maricia.claim.widget.AttachmentPreview;

import java.util.Collections;
import java.util.List;

public class AttachmentPreviewAdapte extends PagerAdapter {

    private List<Attachment> attachments = Collections.emptyList();


    @Override
    public int getCount() {

        return attachments.size();
    }

    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments != null ? attachments : Collections.<Attachment>emptyList();
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view instanceof AttachmentPreview) && (((AttachmentPreview) view).getAttachment() == object);
    }

    public Object instantiateItem(final ViewGroup container, final int position) {
        final AttachmentPreview preview = new AttachmentPreview(container.getContext());
        preview.setAttachment(attachments.get(position));
        container.addView(preview);
        return attachments.get(position);
    }

    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        for (int i = 0; i < container.getChildCount(); i++) {
            final AttachmentPreview preview = ((AttachmentPreview) container.getChildAt(i));
            if (preview.getAttachment() == object) {
                container.removeViewAt(i);
                break;
            }
        }
    }
}
