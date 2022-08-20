package com.example.abctrabajo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.abctrabajo.db.DbMaterial;
import com.example.abctrabajo.entidades.Material;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.PrimitiveIterator;

public class RejistrarProducto extends AppCompatActivity {
    TextInputEditText txtNoMaterial ,txtLoTendita ,txtLoAlmacen,txtDesripcion;

    String IdMaterial = "";

    Material material;

    DbMaterial dbMaterial;

    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejistrar_producto);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);



        txtNoMaterial = findViewById(R.id.txtMaterial);
        txtLoTendita = findViewById(R.id.txtTiendita);
        txtLoAlmacen = findViewById(R.id.txtAlmacen);
        txtDesripcion= findViewById(R.id.txtDescripcion);


        //VERIFICAR PARAMETROS INTENT
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                //No recivio parametro , activity para rejistrar


                //ACTIVAR TXT NUMERO DE MATERIAL
                txtNoMaterial.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


            }else {
                //activity para editar material



                IdMaterial = extras.getString("idMaterial");

                findViewById(R.id.btnEditar).setVisibility(View.VISIBLE);
                findViewById(R.id.btnRejistrar).setVisibility(View.GONE);
                findViewById(R.id.btnEliminar).setVisibility(View.VISIBLE);

                txtNoMaterial.setInputType(InputType.TYPE_NULL);


                mostrarDatosDeMaterial();



                findViewById(R.id.btnEditar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(txtNoMaterial.getText().toString().equals("") || txtLoTendita.getText().toString().equals("") || txtLoAlmacen.getText().toString().equals("") || txtDesripcion.getText().toString().equals("")){
                             Snackbar.make(v, "TODOS LOS CAMPOS DE TEXTO SON OBLIGATORIOS.", Snackbar.LENGTH_LONG)
                                    .setTextColor(getResources().getColor(R.color.white))
                                    .setBackgroundTint(getResources().getColor(R.color.red))
                                    .show();
                        }else {
                            DbMaterial dbMaterial = new DbMaterial(RejistrarProducto.this);
                            Boolean correcto = dbMaterial.editarMaterial(IdMaterial,txtLoTendita.getText().toString(),txtLoAlmacen.getText().toString(),txtDesripcion.getText().toString());

                          if (correcto){
                              Snackbar.make(v, "MATERIAL MODIFICADO", Snackbar.LENGTH_LONG)
                                      .setTextColor(getResources().getColor(R.color.white))
                                      .setBackgroundTint(getResources().getColor(R.color.principal))
                                      .show();
                          }else {
                                Snackbar.make(v, "ERROR , VERIFIQUE LOS DATOS.", Snackbar.LENGTH_LONG)
                                      .setTextColor(getResources().getColor(R.color.white))
                                      .setBackgroundTint(getResources().getColor(R.color.red))
                                      .show();
                          }
                        }
                    }
                });

                //ELIMINAR
                findViewById(R.id.btnEliminar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCustomDialog();

                    }
                });

            }
        }else {
            Toast.makeText(RejistrarProducto.this ,"ALGO SALIO MAL ..+.."+IdMaterial,Toast.LENGTH_LONG).show();
            IdMaterial = (String) savedInstanceState.getSerializable("idMaterial");
        }








        //EVENTO CLICK DE BUTTON REJISTRAR MATERIAL
        findViewById(R.id.btnRejistrar).setOnClickListener(v -> {

            //validar txt

            if(txtNoMaterial.getText().toString().equals("") || txtLoTendita.getText().toString().equals("") || txtLoAlmacen.getText().toString().equals("") || txtDesripcion.getText().toString().equals("")){
               Snackbar.make(v, "TODOS LOS CAMPOS DE TEXTO SON OBLIGATORIOS.", Snackbar.LENGTH_LONG)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setBackgroundTint(getResources().getColor(R.color.red))
                        .show();
            }else {


                DbMaterial dbMaterial = new DbMaterial(RejistrarProducto.this);
                long id = dbMaterial.insertarMaterial(txtNoMaterial.getText().toString() ,txtLoTendita.getText().toString(),txtLoAlmacen.getText().toString(),txtDesripcion.getText().toString());
                if (id > 0){



                    Snackbar.make(v, "MATERIAL REJISTRADO", Snackbar.LENGTH_LONG)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.principal))
                            .show();


                    limpiar();

                    txtNoMaterial.requestFocus();
                    //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }else {
                    Snackbar.make(v, "ERROR", Snackbar.LENGTH_LONG)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }
            }



        });



    }

    //Function to display the custom dialog.
    public void showCustomDialog() {
        final Dialog dialog = new Dialog(RejistrarProducto.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(false);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.alert);

        //Initializing the views of the dialog.
        MaterialButton btnOk = dialog.findViewById(R.id.btnAlertOk);
        MaterialButton btnCancel = dialog.findViewById(R.id.btnAlerCancel);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbMaterial dbMaterial = new DbMaterial(RejistrarProducto.this);

                dbMaterial.eliminarMaterial(txtNoMaterial.getText().toString());

                Intent principal = new Intent(getBaseContext() ,MainActivity.class);
                startActivity(principal);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void limpiar(){
        txtNoMaterial.setText("");
        txtLoTendita.setText("");
        txtLoAlmacen.setText("");
        txtDesripcion.setText("");
    }

    private void mostrarDatosDeMaterial(){

        dbMaterial = new DbMaterial(RejistrarProducto.this);
        material = dbMaterial.VerMaterial(IdMaterial);




        if (material != null){

            txtNoMaterial.setText(material.getMaterial());
            txtLoTendita.setText(material.getTiendita());
            txtLoAlmacen.setText(material.getAlmacen());
            txtDesripcion.setText(material.getDescripcion());
        }

    }


}