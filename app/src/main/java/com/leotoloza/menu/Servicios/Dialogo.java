package com.leotoloza.menu.Servicios;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Dialogo {
    public static void mostrardialogo(Activity actividad) {
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(actividad);
        constructorDialogo.setTitle("Atención!");
        constructorDialogo.setMessage("Desea salir del sistema?");
        constructorDialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                actividad.finish();
                System.exit(0);
            }
        });
        constructorDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialogo = constructorDialogo.create();
        dialogo.show();
    }
}
