package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TelefonoCRUD {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;




    //Conectar con la Base de Datos.
        public static void conexion() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("mi_base_de_datos");
        collection = database.getCollection("mi_coleccion");
    }


    public static void insertarTelefono() {
      Document Telefono1 = new Document("modelo", "Galaxy S21")
              .append("marca", "Samsung")
              .append("sistemaOperativo", "Android")
              .append("tamanoPantalla", 6.2)
              .append("memoriaRAM", 8)
              .append("almacenamientoInterno", 128)
              .append("tieneCamara", true)
              .append("resolucionCamara", 64)
              .append("esSmartphone", true)
              .append("imei", "123456789012345");

      Document Telefono2 = new Document("modelo", "Iphone 15 Pro Max")
              .append("marca", "Apple")
              .append("sistemaOperativo", "iOS")
              .append("tamanoPantalla", 6.7)
              .append("memoriaRAM", 6)
              .append("almacenamientoInterno", 256)
              .append("tieneCamara", true)
              .append("resolucionCamara", 24)
              .append("esSmartphone", true)
              .append("imei", "987654321098765");

      Document Telefono3 = new Document("modelo", "Nothing Phone 2")
              .append("marca", "Nothing")
              .append("sistemaOperativo", "Android")
              .append("tamanoPantalla", 6.4)
              .append("memoriaRAM", 12)
              .append("almacenamientoInterno", 128)
              .append("tieneCamara", true)
              .append("resolucionCamara", 108)
              .append("esSmartphone", true)
              .append("imei", "543216789012345");

      Document Telefono4 = new Document("modelo", "Nokia 1100")
              .append("marca", "Nokia")
              .append("sistemaOperativo", "Symbian")
              .append("tamanoPantalla", 1.5)
              .append("memoriaRAM", 0)
              .append("almacenamientoInterno", 2)
              .append("tieneCamara", false)
              .append("resolucionCamara", 0)
              .append("esSmartphone", false)
              .append("imei", "123156416516516");

      Document Telefono5 = new Document("modelo", "Nokia 3310 5G")
                .append("marca", "Nokia")
                .append("sistemaOperativo", "YunOS")
                .append("tamanoPantalla", 2.4)
                .append("memoriaRAM", 2)
                .append("almacenamientoInterno", 16)
                .append("tieneCamara", true)
                .append("resolucionCamara", 2)
                .append("esSmartphone", false)
                .append("imei", "1234567890123452");

        //Insertar en la colección.
        collection.insertOne(Telefono1);
        collection.insertOne(Telefono2);
        collection.insertOne(Telefono3);
        collection.insertOne(Telefono4);
        collection.insertOne(Telefono5);

        System.out.println("Se ha insertado correctamente.");

    }



    public static void actualizarTelefono(String imei, String nuevaMarca) {
        Document filtro = new Document("imei", imei);
        Document actualizacion = new Document("$set", new Document("marca", nuevaMarca));
        collection.updateOne(filtro, actualizacion);
        System.out.println("Marca de telefono actualizada.");
    }

    public static void eliminarTelefono(String imei) {
        try {
            Document filtro = new Document("imei", imei);
            DeleteResult result = collection.deleteOne(filtro);

            if (result.getDeletedCount() > 0) {
                System.out.println("Telefono con IMEI: " + imei + " eliminado exitosamente.");
            } else {
                System.out.println("No se encontró ningun telefono con IMEI: " + imei + ".");
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el telefono: " + e.getMessage());
        }
    }


    public static Nodo obtenerTelefonostoLinkedList() {
        Nodo cabeza = null;
        Nodo ultimo = null;
        try {
            FindIterable<Document> documents = collection.find();

            for (Document doc : documents) {
                Telefono telefono = leertelefono(doc);
                Nodo nodo = new Nodo(telefono);
                if (cabeza == null) {
                    cabeza = nodo;
                } else {
                    ultimo.setSiguiente(nodo);
                }
                ultimo = nodo;
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los teléfonos: " + e.getMessage());
        }
        return cabeza;
    }
    private static Telefono leertelefono(Document doc) {
        Telefono telefono = new Telefono();
        telefono.setMarca(doc.getString("marca"));
        telefono.setModelo(doc.getString("modelo"));
        telefono.setSistemaOperativo(doc.getString("sistemaOperativo"));
        telefono.setTamanoPantalla(doc.getDouble("tamanoPantalla"));
        telefono.setMemoriaRAM(doc.getInteger("memoriaRAM"));
        telefono.setAlmacenamientoInterno(doc.getInteger("almacenamientoInterno"));
        telefono.setTieneCamara(doc.getBoolean("tieneCamara"));
        telefono.setResolucionCamara(doc.getInteger("resolucionCamara"));
        telefono.setEsSmartphone(doc.getBoolean("esSmartphone"));
        telefono.setImei(doc.getString("imei"));
        return telefono;
    }


    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada con MongoDB.");
        }
    }
}