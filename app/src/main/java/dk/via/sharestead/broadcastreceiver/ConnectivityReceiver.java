package dk.via.sharestead.broadcastreceiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class ConnectivityReceiver extends BroadcastReceiver {
    public boolean isConnected = true;
    String status;
    Context Cnt;
    Activity activity;
    Activity parent;
    AlertDialog alert;

    public ConnectivityReceiver()
    {

    }


    public ConnectivityReceiver(Activity a) {
        parent = a;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        activity = (Activity) context;
        status = NetworkUtil.getConnectivityStatusString(context);
        returnStatus(status, context);
    }

    public void returnStatus(String s, final Context cnt) {
        if (s.equals("Mobile data enabled")) {
            isConnected = true;
        } else if (s.equals("Wifi enabled")) {
            isConnected = true;
        } else {
            isConnected = false;
            final AlertDialog.Builder builder = new AlertDialog.Builder(cnt);
            // Set the Alert Dialog Message
            builder.setMessage("Internet connection required")
                    .setCancelable(false)
                    .setPositiveButton("continue",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    activity.finish();
                                    Intent intent = new Intent(activity, activity.getClass());
                                    activity.startActivity(intent);
                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {
                            if(alert.isShowing()) {
                                isConnected=false;
                                alert.dismiss();
                            }
                        }
                    });

            alert = builder.create();
            alert.show();
        }
    }
}
