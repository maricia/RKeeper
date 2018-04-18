package com.maricia.claim.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.maricia.claim.model.Attachment;
import com.maricia.claim.model.ClaimItem;
import com.maricia.claim.model.commands.CreateAttachmentCommand;
import com.maricia.claim.util.ActionCommand;

import java.io.File;

public class AttachmentPagerFragment extends Fragment {


    private static final int REQUEST_ATTACH_FILE = 1;
    private static final int REQUEST_ATTACH_PERMISSION = 1001;

    private final AttachmentPreviewAdapter adapter = new AttachmentPreviewAdapter();

    private ActionCommand<Uri, Attachment> attachFileCommand;
    private ViewPager pager;

    private ClaimItem claimItem;

    public void setClaimItem(final ClaimItem claimItem) {
        this.claimItem = claimItem;
        onAttachmentsChanged();
    }

    public void onAttachmentsChanged() {
        adapter.setAttachments(
                claimItem != null ? claimItem.getAttachments() : null);
        pager.setCurrentItem(adapter.getCount() - 1);
    }

    public void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final File attachmentsDir =
                getContext().getDir("attachments", Context.MODE_PRIVATE);
        attachFileCommand = new CreateAttachmentCommand(
                attachmentsDir,
                getContext().getContentResolver()) {
            @Override
            public void onForeground(final Attachment value) {
                if (claimItem != null) {
                    claimItem.addAttachment(value);
                    onAttachmentsChanged();
                }
            }
        };
    }

    public void onDestroy() {
        super.onDestroy();
        claimItem = null;
    }


}
