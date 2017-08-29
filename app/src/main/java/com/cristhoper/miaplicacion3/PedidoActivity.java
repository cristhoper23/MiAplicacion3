package com.cristhoper.miaplicacion3;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PedidoActivity extends AppCompatActivity {

    private Spinner tipoPizza;
    private RadioGroup rdCompl;
    private TextView textDialog;

    private String textSpinner;
    private String textRadioBtn;
    private Double monto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        textDialog = (TextView) findViewById(R.id.txtCustomDialog);

        rdCompl = (RadioGroup) findViewById(R.id.rdCompl);

        tipoPizza = (Spinner) findViewById(R.id.tipoPizza);
        tipoPizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                textSpinner = parent.getItemAtPosition(position).toString();
                if(position == 0){
                    monto = 40.00;
                }else if (position == 2){
                    monto = 60.00;
                }else if (position == 3){
                    monto = 45.00;
                }else{
                    monto = 65.00;
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
                    monto = monto + 8;
                break;
            case R.id.checkJamon:
                CheckBox checkJamon = (CheckBox) view;
                if(checkJamon.isChecked())
                    monto = monto + 12;
                break;
        }
    }

    public void showDialog(View view){
        //textDialog.setText("Su pedido de " + textSpinner + " con " + textRadioBtn + " a S/." + monto.toString() + " + IGV está en proceso de envío");
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        dialog.setTitle("Confimación de pedido");
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pedido cancelado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
