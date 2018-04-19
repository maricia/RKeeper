package com.maricia.claim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maricia.claim.model.Attachment;
import com.maricia.claim.model.ClaimItem;
import com.maricia.claim.ui.CategoryPickerFragment;
import com.maricia.claim.ui.DatePickerWrapper;
import com.maricia.claim.ui.IconPickerWrapper;
import com.maricia.claim.ui.attachments.AttachmentPagerFragment;
import com.maricia.claim.widget.DatePickerLayout;

public class CaptureClaimActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerLayout selectDate;
    private CategoryPickerFragment categories;
    private AttachmentPagerFragment attachments;
    private EditText description;
    private EditText amount;
    private ClaimItem claimItem;

    private static final String TAG = "CaptureClaimActivity";
    public static final String EXTRA_CLAIM_ITEM = "com.maricia.claim.extras.CLAIM_ITEM";
    private static final String KEY_CLAIM_ITEM ="com.maricia.claim.ClaimItem";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_claim);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton attach = (FloatingActionButton) findViewById(R.id.attach);
        attach.setOnClickListener(this);

        //selectDate = new DatePickerWrapper((TextView)findViewById(R.id.date) );
        selectDate = (DatePickerLayout)findViewById(R.id.date);
        description = (EditText)findViewById(R.id.description);
        amount = (EditText)findViewById(R.id.amount);


       final FragmentManager fragmentManager = getSupportFragmentManager();
       categories = (CategoryPickerFragment) fragmentManager.findFragmentById(R.id.categories);
       attachments = (AttachmentPagerFragment)fragmentManager.findFragmentById(R.id.attachments);

       //where is the claim item coming from???
       if(savedInstanceState !=null){
           claimItem = savedInstanceState.getParcelable(KEY_CLAIM_ITEM);
       }else if(getIntent().hasExtra(EXTRA_CLAIM_ITEM)){
           claimItem = getIntent().getParcelableExtra(EXTRA_CLAIM_ITEM);
       }
        //if no claim item is being passed in then do this
        if(claimItem == null){
           claimItem = new ClaimItem();
        }else {
           description.setText(claimItem.getDescription());
           amount.setText(String.format("%f", claimItem.getAmount()));
           selectDate.setDate(claimItem.getTimestamp());
        }
        attachments.setClaimItem(claimItem);
    }


    void captureClaimItem(){
        claimItem.setDescription(description.getText().toString());
        if(!TextUtils.isEmpty(amount.getText())){
            claimItem.setAmount(Double.parseDouble(amount.getText().toString()));
        }
        claimItem.setTimestamp(selectDate.getDate());
        claimItem.setCategory(categories.getSelectedCategory());
    }
    @Override
    protected void onSaveInstanceState(final Bundle outState){
        super.onSaveInstanceState(outState);
        captureClaimItem(); //make sure the claim item is up to date
        outState.putParcelable(KEY_CLAIM_ITEM, claimItem);
    }
    @Override
    public void finish(){
        captureClaimItem();
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_CLAIM_ITEM, claimItem));
        super.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attach:
                attachments.onAttachClick();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capture_claim, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
                default:
                    return false;
        }
        return true;
    }

}
