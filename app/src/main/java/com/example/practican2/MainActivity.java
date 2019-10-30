package com.example.practican2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    EditText txtNombre;
    EditText txtApellido;
    EditText txtEdad;

    TextView txtGenerado;

    RadioGroup rgrupo;
    RadioButton rbtnHombre;
    RadioButton rbtnMujer;

    Button btnGenerador;
    Button btnLimpiar;

    Switch switchHijos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciar
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtApellido = (EditText)findViewById(R.id.txtApellido);
        txtEdad = (EditText)findViewById(R.id.txtEdad);
        txtGenerado = (TextView)findViewById(R.id.txtGenerado);

        rgrupo = (RadioGroup)findViewById(R.id.radioGroup);
        rbtnHombre = (RadioButton)findViewById(R.id.rbtnHombre);
        rbtnMujer = (RadioButton)findViewById(R.id.rbtnMujer);

        btnGenerador = (Button)findViewById(R.id.btnGenerar);
        btnLimpiar = (Button)findViewById(R.id.btnLimpiar);

        switchHijos = (Switch)findViewById(R.id.switchHijos);

        final Spinner spn1;
        ArrayAdapter adaptadorDeArrayDeFichero;

        spn1 = (Spinner)findViewById(R.id.listaEstados);
        adaptadorDeArrayDeFichero = ArrayAdapter.createFromResource(this, R.array.estadoCivil, R.layout.support_simple_spinner_dropdown_item);
        spn1.setAdapter(adaptadorDeArrayDeFichero);


        //LISTENER BOTÓN GENERAR.
        btnGenerador.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String nombre = "";
                String apellido = "";
                String radioSeleccion = "";
                String estadoCivil = "";
                int miEdad;
                boolean hijos;
                boolean checked;

                checked = eleccionGenero(rbtnHombre, rbtnMujer);
                hijos = hijosSiNo(switchHijos);

                //Comprobar si algun campo esta vacio || ESPAÑOL
                if (txtNombre.getText().toString().isEmpty() || txtNombre.equals("") || txtNombre.length() == 0)
                {
                    txtGenerado.setText(getString(R.string.fNombre));
                    txtGenerado.setTextColor(getResources().getColor(R.color.rojo, null));
                }
                else if (txtApellido.getText().toString().isEmpty() || txtApellido.equals("") || txtApellido.length() == 0)
                {
                    txtGenerado.setText(getString(R.string.fApellido));
                    txtGenerado.setTextColor(getResources().getColor(R.color.rojo, null));
                }
                else if (txtEdad.getText().toString().isEmpty() || txtEdad.equals("") || txtEdad.length() == 0)
                {
                    txtGenerado.setText(getString(R.string.fEdad));
                    txtGenerado.setTextColor(getResources().getColor(R.color.rojo, null));
                }
                else if (checked == false)
                {
                    txtGenerado.setText(getString(R.string.fGenero));
                    txtGenerado.setTextColor(getResources().getColor(R.color.rojo, null));
                }
                else if (spn1.getSelectedItem().toString().equals(getString(R.string.seleccionaEstado)))
                {
                    //R.string.seleccionaEstado;
                    txtGenerado.setText(R.string.fCivil);
                    txtGenerado.setTextColor(getResources().getColor(R.color.rojo, null));
                }
                else
                    {
                    //Si no hay nada vacio, comprobar la edad y si tiene hijos
                    try
                    {
                        miEdad = Integer.parseInt(txtEdad.getText().toString());

                        if (rbtnHombre.isChecked())
                        {
                            radioSeleccion = getString(R.string.rbtnMale);
                        }
                        else if (rbtnMujer.isChecked())
                        {
                            radioSeleccion = getString(R.string.rbtnFemale);
                        }

                        estadoCivil = spn1.getSelectedItem().toString();
                        nombre = txtNombre.getText().toString();
                        apellido = txtApellido.getText().toString();

                        if (miEdad >= 18)
                        {
                            if (hijos == true)
                            {
                                txtGenerado.setText(apellido + ", " + nombre + ", " + getString(R.string.mayorEdad) + ", " + radioSeleccion + " " + estadoCivil + " " + getString(R.string.conHijos));
                                txtGenerado.setTextColor(getResources().getColor(R.color.negro, null));
                            } else
                                {
                                txtGenerado.setText(apellido + ", " + nombre + ", " + getString(R.string.mayorEdad) + ", " + radioSeleccion + " " + estadoCivil + " " + getString(R.string.sinHijos));
                                txtGenerado.setTextColor(getResources().getColor(R.color.negro, null));
                            }
                        } else if (miEdad < 18)
                        {
                            if (hijos == true)
                            {
                                txtGenerado.setText(apellido + ", " + nombre + ", " + getString(R.string.menorEdad) + ", " + radioSeleccion + " " + estadoCivil + " " + getString(R.string.conHijos));
                                txtGenerado.setTextColor(getResources().getColor(R.color.negro, null));
                            } else
                                {
                                txtGenerado.setText(apellido + ", " + nombre + ", " + getString(R.string.menorEdad) + ", " + radioSeleccion + ", " + estadoCivil + " " + getString(R.string.sinHijos));
                                txtGenerado.setTextColor(getResources().getColor(R.color.negro, null));
                            }
                        }
                    }
                    catch (NumberFormatException nfe)
                    {
                    }
                }
            }
        });


        //Listener Boton Limpiar
        btnLimpiar.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtNombre.setText("");
                txtApellido.setText("");
                txtEdad.setText("");
                rgrupo.clearCheck();
                spn1.setSelection(0);
                switchHijos.setChecked(false);
                txtGenerado.setText("");
            }
        });
    }



    //MÉTODO OBTENER SI HA ESCOGIDO GÉNERO
    public boolean eleccionGenero(RadioButton rbtnHombre, RadioButton rbtnMujer){

        boolean checked = false;

        if (rbtnHombre.isChecked() || rbtnMujer.isChecked())
        {
            checked = true;
        }

        return checked;
    }


    //MÉTODO PARA SABER SI TIENE HIJOS O NO
    public boolean hijosSiNo(Switch switchHijos)
    {

        boolean checked = false;

        if (switchHijos.isChecked())
        {
            checked = true;
        }

        return checked;
    }
}