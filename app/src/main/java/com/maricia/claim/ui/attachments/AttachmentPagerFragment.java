package com.maricia.claim.ui.attachments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maricia.claim.R;
import com.maricia.claim.model.Attachment;
import com.maricia.claim.model.ClaimItem;
import com.maricia.claim.model.commands.CreateAttachmentCommand;
import com.maricia.claim.util.ActionCommand;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class AttachmentPagerFragment extends Fragment {

    private static final String TAG = "AttachmentPagerFragment";
    private static final int REQUEST_ATTACH_FILE = 1;
    private static final int REQUEST_ATTACH_PERMISSION = 1001;

    private final AttachmentPreviewAdapte adapter = new AttachmentPreviewAdapte();

    private ActionCommand<Uri, Attachment> attachFileCommand;
    private ViewPager pager;

    private ClaimItem claimItem;

    public void setClaimItem(final ClaimItem claimItem) {
        this.claimItem = claimItem;
        onAttachmentsChanged();
    }

    public void onAttachmentsChanged() {
        adapter.setAttachments(claimItem != null ? claimItem.getAttachments() : null);
        pager.setCurrentItem(adapter.getCount() - 1);
    }

    @Override
    public void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final File attachmentsDir = getContext().getDir("attachments", Context.MODE_PRIVATE);
        attachFileCommand = new CreateAttachmentCommand(attachmentsDir,getContext().getContentResolver()) {
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


    public View onCreateView(
            final LayoutInflater inflater,
            final @Nullable ViewGroup container,
            final @Nullable Bundle savedInstanceState) {

        pager = (ViewPager) inflater.inflate(R.layout.fragment_attachment_pager, container, false);
        pager.setPageMargin(
                getResources().getDimensionPixelSize(R.dimen.grid_spacer1));
        pager.setAdapter(adapter);

        return pager;
    }


    public void onAttachClick() {
        final int permissionStatus = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_ATTACH_PERMISSION);
            return;
        }

        Log.d(TAG, "onAttachClick: 3");
        Intent attach = new Intent(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE).setType("*/*");
        startActivityForResult(attach, REQUEST_ATTACH_FILE);

    }//end onAttachClick


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ATTACH_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onAttachClick();
                }
                break;
        }
    }//end OnRequestPermissionsResults

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ATTACH_FILE:
                onAttachFileResults(requestCode, data);
                break;
        }
    }//end onActivityResults

    private void onAttachFileResults(int requestCode, Intent data) {
        if(requestCode == RESULT_OK || data == null || data.getData() == null){
            return;
        }
        Toast.makeText(getActivity(), data.getDataString(), Toast.LENGTH_LONG).show();
        attachFileCommand.exec(data.getData());
    }


}
