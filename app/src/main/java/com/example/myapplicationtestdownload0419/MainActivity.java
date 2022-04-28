package com.example.myapplicationtestdownload0419;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    public static final String FILE_NAME_EMPTY_FORM="original.pdf";
    private ProgressDialog progressDialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        Button mybtn = findViewById(R.id.mybtn);
        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(context,"downloading...");
               Thread t= new Thread(new Runnable() {
                    public void run() {//test
                        DownloadFiles("http://sthsvr80.sth.com/lis_pdf/test/testDrawOnPdfDiectly/dc_original2.pdf");progressDialog.dismiss();
                    }
                });
               t.start();

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        });

    }
//
//    private void down(String psPdfFileNameOnServer) {
//
//        showProgressDialog(context,"Preparing Form..... (If it takes too long, double-check your wireless connection.)");
//        String newFileName=FILE_NAME_EMPTY_FORM;
//
//     String BASE_PATH_WORKING_WITH_FILES=  context.getExternalFilesDir(null).getAbsolutePath()+"/";
//        while(isFileExists(BASE_PATH_WORKING_WITH_FILES+FILE_NAME_EMPTY_FORM)){
//            deleteMyFile(BASE_PATH_WORKING_WITH_FILES+FILE_NAME_EMPTY_FORM);
//        }
//
//
//
//
//        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(psPdfFileNameOnServer ));
//        request.setTitle("Download: "+newFileName);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
//            request.allowScanningByMediaScanner();
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        }
////       request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,newFileName);
//        request.setDestinationInExternalFilesDir(context,null,newFileName);
//
//        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
//        request.setMimeType("application/pdf");
//        request.allowScanningByMediaScanner();
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//        long downloadReference= downloadManager.enqueue(request);
//
//        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        BroadcastReceiver receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                if (downloadReference == reference) {
//                    progressDialog.dismiss();
//                    unregisterReceiver(this);
//
//
//                   // stickLabelToOriginalInDevice(bmPatientLabel);
//
//
//
//                   // PlugPreConsultDataAndStartFillPdfIntent();
//
//                }
//
//
//            }
//        };
//
//        registerReceiver(receiver, filter);
//
//
//
//    }
//    public static boolean isFileExists(String sLocalPath){
//        return  new File(sLocalPath).exists();
//    }
//    public void deleteMyFile(String sLocalPath){
//       File a= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//         String qa=      a.getAbsolutePath();
//        Boolean fe= a.canRead();
//       Boolean fw=  a.canWrite();
//
//        String str3 = Environment.DIRECTORY_DCIM;
//        String str2 = Environment.DIRECTORY_DOWNLOADS;
//        String str = context.getFilesDir().toString();
//        Boolean  x= new File(sLocalPath).canRead();
//        Boolean  y= new File(sLocalPath).canWrite();
//        int xr=1;
//        new File(sLocalPath).delete();
//    }
    public  ProgressDialog showProgressDialog(Context context, String sMsg){


        progressDialog=new ProgressDialog(context);

        progressDialog.setMessage(sMsg);
        progressDialog.setCancelable(false);
        progressDialog.show();

        return  progressDialog;

    }
    public void DownloadFiles(String sURL){
        //this function will also overwrite file with same name
        try {

            URL u = new URL(sURL);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(context.getExternalFilesDir(null).getAbsolutePath()+ "/" + "ori.pdf"));
            while ((length = dis.read(buffer))>0) {
                fos.write(buffer, 0, length);
            }

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }

    }
}
