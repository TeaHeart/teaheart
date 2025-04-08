package org.example;

public @interface Iterator {
    class Student {
        private final int id;
        private final String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("Student{id=%d, name='%s'}", id, name);
        }
    }

    interface StudentIterator {
        boolean hasNext();

        Student next();
    }

    class StudentIteratorImpl implements StudentIterator {
        private final Student[] students;
        private final int fence;
        private int cursor = 0;

        public StudentIteratorImpl(Student[] students, int size) {
            this.students = students;
            fence = size;
        }

        @Override
        public boolean hasNext() {
            return cursor != fence;
        }

        @Override
        public Student next() {
            return students[cursor++];
        }
    }

    interface StudentAggregate {
        void add(Student student);

        StudentIterator iterator();
    }

    class StudentAggregateImpl implements StudentAggregate {
        private final Student[] students;
        private int size;

        public StudentAggregateImpl(int length) {
            students = new Student[length];
        }

        @Override
        public void add(Student student) {
            students[size++] = student;
        }

        @Override
        public StudentIterator iterator() {
            return new StudentIteratorImpl(students, size);
        }
    }
}
