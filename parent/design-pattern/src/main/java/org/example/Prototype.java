package org.example;

import java.io.*;

public @interface Prototype {
    class Teacher implements Cloneable {
        private String name;

        public Teacher(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("Teacher{name='%s'}", name);
        }

        @Override
        public Teacher clone() {
            try {
                Teacher clone = (Teacher) super.clone();
                clone.name = name;
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new Error(e);
            }
        }
    }

    class Student implements Serializable {
        private final String name;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("Student{name='%s'}", name);
        }

        public Student serializeClone() {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
                oos.writeObject(this);
                oos.flush();
                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(os.toByteArray()))) {
                    return (Student) ois.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
