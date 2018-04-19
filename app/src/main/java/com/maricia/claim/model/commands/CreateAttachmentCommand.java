package com.maricia.claim.model.commands;

import android.content.ContentResolver;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.maricia.claim.model.Attachment;
import com.maricia.claim.util.ActionCommand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class CreateAttachmentCommand extends ActionCommand<Uri, Attachment> {


    private final File dir;
    private final ContentResolver resolver;


    public CreateAttachmentCommand(
            final File dir,
            final ContentResolver resolver) {

        this.dir = dir;
        this.resolver = resolver;
    }



    File makeFile(final Uri value) throws IOException {
        final File outputFile = new File(dir, UUID.randomUUID().toString());
        final InputStream input = resolver.openInputStream(value);
        final FileOutputStream output = new FileOutputStream(outputFile);
        try {
            final byte[] buffer = new byte[10 * 1024];
            int bytesRead = 0;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.flush();
        } finally {
            output.close();
            input.close();
        }
        return outputFile;
    }

    @Override
    public Attachment onBackground(final Uri value) throws Exception {
        final File file = makeFile(value);
        final String type = resolver.getType(value);
        if (type != null && type.startsWith("image/") && BitmapFactory.decodeFile(file.getAbsolutePath()) != null)
        {
            return new Attachment(file, Attachment.Type.IMAGE);
        } else {
            return new Attachment(file, Attachment.Type.UNKNOWN);
        }
    }

}
