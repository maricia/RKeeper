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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maricia.claim.ui.CategoryPickerFragment;
import com.maricia.claim.ui.DatePickerWrapper;
import com.maricia.claim.ui.IconPickerWrapper;
import com.maricia.claim.widget.DatePickerLayout;

public class CaptureClaimActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerLayout selectDate;
    private CategoryPickerFragment categories;
   // private RadioGroup categories;

    private static final String TAG = "CaptureClaimActivity";

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

       final FragmentManager fragmentManager = getSupportFragmentManager();
       categories = (CategoryPickerFragment) fragmentManager.findFragmentById(R.id.categories);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attach:
                onAttachClick();
                break;
        }
    }

    public void onAttachClick() {
        final int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_ATTACH_PERMISSION);
            return;
        }

        Log.d(TAG, "onAttachClick: 3");
        Intent attach = new Intent(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE).setType("*/*");
        startActivityForResult(attach, REQUEST_ATTACH_FILE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ATTACH_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onAttachClick();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ATTACH_FILE:
                onAttachFileResults(requestCode, data);
                break;
        }
    }

    private void onAttachFileResults(int requestCode, Intent data) {
        if(requestCode == RESULT_OK || data == null || data.getData() == null){
            return;
        }
        Toast.makeText(this, data.getDataString(), Toast.LENGTH_LONG).show();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
