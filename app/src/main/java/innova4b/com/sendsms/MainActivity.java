package innova4b.com.sendsms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int SEND_PERMISSION_REQUEST_CODE = 1;
    Button mButtonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSend = (Button) findViewById(R.id.button);
        mButtonSend.setEnabled(false);

        if (checkCallPermission(Manifest.permission.SEND_SMS)){
            mButtonSend.setEnabled(true);
        }
    }

    private boolean checkCallPermission(String permission) {
        int permisionCheck = ContextCompat.checkSelfPermission(this, permission);

        return permisionCheck == PackageManager.PERMISSION_GRANTED;
    }

    // CHECKEAR SI SE USA REALMENTE
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case SEND_PERMISSION_REQUEST_CODE:
               if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   mButtonSend.setEnabled(true);
               }
               return;
       }
    }

    public void send(View view) {
        String phoneNumber = ((EditText)findViewById(R.id.editTextNumber)).getText().toString();
        String msg = ((EditText)findViewById(R.id.editTextMsg)).getText().toString();

        if (phoneNumber == null || phoneNumber.length()==0 || msg == null || msg.length() == 0 ){
            return;
        }

        if (checkCallPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null, msg, null, null);
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }





    }
}
