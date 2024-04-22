package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

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

    public static void insertarTelefono(Telefono telefono) {
        Document docTelefono = new Document("marca", telefono.getMarca())
                .append("modelo", telefono.getModelo())
                .append("sistemaOperativo", telefono.getSistemaOperativo())
                .append("tamanoPantalla", telefono.getTamanoPantalla())
                .append("memoriaRAM", telefono.getMemoriaRAM())
                .append("almacenamientoInterno", telefono.getAlmacenamientoInterno())
                .append("tieneCamara", telefono.isTieneCamara())
                .append("resolucionCamara", telefono.getResolucionCamara())
                .append("esSmartphone", telefono.isEsSmartphone())
                .append("imei", telefono.getImei());
        collection.insertOne(docTelefono);
        System.out.println("Telefono insertado correctamente.");
    }

    public static void mostrarTelefonos() {
        FindIterable<Document> telefonos = collection.find();
        for (Document docTelefono : telefonos) {
            String marca = docTelefono.getString("marca");
            String modelo = docTelefono.getString("modelo");
            String sistemaOperativo = docTelefono.getString("sistemaOperativo");
            double tamanoPantalla = docTelefono.getDouble("tamanoPantalla");
            int memoriaRAM = docTelefono.getInteger("memoriaRAM");
            int almacenamientoInterno = docTelefono.getInteger("almacenamientoInterno");
            boolean tieneCamara = docTelefono.getBoolean("tieneCamara");
            int resolucionCamara = docTelefono.getInteger("resolucionCamara");
            boolean esSmartphone = docTelefono.getBoolean("esSmartphone");
            String imei = docTelefono.getString("imei");

            System.out.println("Marca: " + marca + ", Modelo: " + modelo + ", Sistema Operativo: " + sistemaOperativo + ", Tamaño de Pantalla: " + tamanoPantalla + ", Memoria RAM: " + memoriaRAM + ", Almacenamiento Interno: " + almacenamientoInterno + ", Tiene Cámara: " + tieneCamara + ", Resolución de Cámara: " + resolucionCamara + ", Es Smartphone: " + esSmartphone + ", IMEI: " + imei);
        }
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

    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada con MongoDB.");
        }
    }
}