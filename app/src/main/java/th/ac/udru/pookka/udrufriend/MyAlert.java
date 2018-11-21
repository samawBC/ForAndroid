package th.ac.udru.pookka.udrufriend;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class MyAlert {

    private Context context;

    // alt+insert > constructor >ok
    public MyAlert(Context context) {
        this.context = context;
    }

    //method มี void ทำงานแล้วไม่ส่งค่ากลับโปรแกรมหลัก / return ทำงานแล่วส่งค่ากลับ
    public void normalDialog(String titleString,String messageString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_alert);
        builder.setTitle(titleString);
        builder.setMessage(messageString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();





    }


}// main Class



