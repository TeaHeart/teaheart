package org.example.io;

import org.example.X;

import java.io.*;

public interface SerializeX {
    static <T extends Serializable> byte[] serialize(T obj) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
                oos.writeObject(obj);
                oos.flush();
                return os.toByteArray();
            }
        } catch (IOException e) {
            throw X.throwrt(e);
        }
    }

    static <T extends Serializable> T deserialize(byte[] bytes) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                return X.cast(ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw X.throwrt(e);
        }
    }

    static <T extends Serializable> T clone(T obj) {
        return deserialize(serialize(obj));
    }
}