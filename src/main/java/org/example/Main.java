package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CRUD {

    // Configura la conexión a MongoDB
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public CRUD() {
        // Establece la conexión con MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("mi_base_de_datos");
        collection = database.getCollection("mi_coleccion");
    }

    // Método para leer todos los registros de la colección y almacenarlos en un ArrayList
    public List<Telefono> leerRegistros() {
        List<Telefono> telefonos = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            // Aquí necesitarás mapear los documentos de MongoDB a objetos Telefono
            Telefono telefono = mapDocumentoATelefono(doc);
            telefonos.add(telefono);
        }
        cursor.close();
        return telefonos;
    }

    // Método para insertar registros en una lista enlazada
    public LinkedList<Telefono> insertarEnListaEnlazada() {
        LinkedList<Telefono> listaEnlazada = new LinkedList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            // Mapea el documento a un objeto Telefono
            Telefono telefono = mapDocumentoATelefono(doc);
            // Inserta el telefono al principio de la lista enlazada
            listaEnlazada.addFirst(telefono);
        }
        cursor.close();
        return listaEnlazada;
    }

    // Método para mapear un documento de MongoDB a un objeto Telefono
    private Telefono mapDocumentoATelefono(Document doc) {
        Telefono telefono = new Telefono();
        telefono.setMarca(doc.getString("marca"));
        telefono.setModelo(doc.getString("modelo"));
        // Continúa para el resto de los atributos
        return telefono;
    }

    // Cerrar la conexión a MongoDB cuando sea necesario
    public void cerrarConexion() {
        mongoClient.close();
    }
}
