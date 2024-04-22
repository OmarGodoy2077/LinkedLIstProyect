package org.example;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // Llamar a la conexión.
        TelefonoCRUD.conexion();

        // Llamar al ingreso de documentos.
        //TelefonoCRUD.insertarTelefono();

        // Llamar a la función para mostrar telefonos.
        //  TelefonoCRUD.mostrarTelefonos();

          // Llamar a la función de actualización de telefonos.
        TelefonoCRUD.actualizarTelefono("1234567890123452", "PepePhone");

        // Llamar a la función de eliminar un telefono.
        //TelefonoCRUD.eliminarTelefono("1234567890123452");


        // Llamar a la función para mostrar telefonos.
        TelefonoCRUD.leerTelefono();




        // Llamar a la función cerrar conexión.
        //TelefonoCRUD.cerrarConexion();
    }
}