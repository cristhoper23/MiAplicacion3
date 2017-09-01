package com.cristhoper.miaplicacion3;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PedidoActivity extends AppCompatActivity {

    private Spinner tipoPizza;
    private RadioGroup rdCompl;
    private CheckBox checkQueso, checkJamon;
    private EditText direccionPedido;

    private String textSpinner, textRadioBtn;
    private Double montoPizza, montoAdicional = 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        rdCompl = (RadioGroup) findViewById(R.id.rdCompl);
        checkQueso = (CheckBox) findViewById(R.id.checkQueso);
        checkJamon = (CheckBox) findViewById(R.id.checkJamon);
        direccionPedido = (EditText) findViewById(R.id.direccionPedido);

        tipoPizza = (Spinner) findViewById(R.id.tipoPizza);
        tipoPizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                textSpinner = parent.getItemAtPosition(position).toString();
                if(position == 1){
                    montoPizza = 40.00;
                }else if (position == 2){
                    montoPizza = 60.00;
                }else if (position == 3){
                    montoPizza = 45.00;
                }else if (position == 4){
                    montoPizza = 65.00;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void radioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.rdMasaGrande:
                if(checked)
                    textRadioBtn = "masa grande";
                break;
            case R.id.rdMasaDelg:
                if(checked)
                    textRadioBtn = "masa delgada";
                break;
            case R.id.rdMasaArt:
                if(checked)
                    textRadioBtn = "masa artesanal";
                break;
        }
    }

    public void checkBoxClicked(View view){
        switch (view.getId()){
            case R.id.checkQueso:
                CheckBox checkQueso = (CheckBox) view;
                if(checkQueso.isChecked())
                    montoAdicional = 8.00;
                else
                    montoAdicional = 0.00;
            case R.id.checkJamon:
                CheckBox checkJamon = (CheckBox) view;
                if(checkJamon.isChecked())
                    montoAdicional = 12.00;
                else
                    montoAdicional = 0.00;
        }
    }

    public void showDialog(final View view){

        if (tipoPizza.getSelectedItemPosition() == 0 ){
            Toast.makeText(this, "Tipo de pizza no seleccionado", Toast.LENGTH_LONG).show();
        }else if (rdCompl.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Tipo de masa no seleccionada", Toast.LENGTH_LONG).show();
        }else{
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog);

            dialog.setTitle("Confimación de pedido");
            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
            Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
            TextView textDialog = (TextView) dialog.findViewById(R.id.txtCustomDialog);

            //Hallando el monto total de la pizza
            Double montoTotal = montoPizza + montoAdicional;
            textDialog.setText("Su pedido de " + textSpinner + " con " + textRadioBtn + " a S/." + montoTotal.toString() + " + IGV está en proceso de envío");

            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    tipoPizza.setSelection(0);
                    rdCompl.clearCheck();
                    checkQueso.setChecked(false);
                    checkJamon.setChecked(false);
                    direccionPedido.setText("");

                    Toast.makeText(getApplicationContext(), "Pedido cancelado", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            // Your android custom dialog ok action
            // Action for custom dialog ok button click
            dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notification notification = new NotificationCompat.Builder(PedidoActivity.this)
                            .setContentTitle("Notification de pedido")
                            .setContentText("Su pedido de pizza se ha realizado correctamente")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setColor(ContextCompat.getColor(PedidoActivity.this, R.color.colorPrimary))
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setAutoCancel(true)
                            .build();

                    // Notification manager
                    NotificationManager notificationManager = (NotificationManager) PedidoActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);

                    Toast.makeText(getApplicationContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = new Intent(PedidoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            dialog.show();
        }
    }
}
