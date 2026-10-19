package com.aimtool.overlay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);
            Toast.makeText(this, "Please grant Overlay Permission", Toast.LENGTH_LONG).show();
        } else {
            startOverlayService();
        }
    }

    private void startOverlayService() {
        Intent intent = new Intent(this, OverlayService.class);
        startService(intent);
        Toast.makeText(this, "Aim Tool Overlay Started!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startOverlayService();
            } else {
                Toast.makeText(this, "Overlay Permission Required!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
