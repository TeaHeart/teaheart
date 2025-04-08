package org.example;

import java.util.ArrayList;
import java.util.List;

public @interface Visitor {
    interface Animal {
        void accept(FeedVisitor master);
    }

    class Cat implements Animal {
        @Override
        public void accept(FeedVisitor visitor) {
            visitor.feed(this);
        }
    }

    class Dog implements Animal {
        @Override
        public void accept(FeedVisitor visitor) {
            visitor.feed(this);
        }
    }

    interface FeedVisitor {
        void feed(Cat cat);

        void feed(Dog dog);
    }

    class Owner implements FeedVisitor {
        @Override
        public void feed(Cat cat) {
            System.out.println("Owner.feed");
        }

        @Override
        public void feed(Dog dog) {
            System.out.println("Owner.feed");
        }
    }

    class Other implements FeedVisitor {
        @Override
        public void feed(Cat cat) {
            System.out.println("Other.feed");
        }

        @Override
        public void feed(Dog dog) {
            System.out.println("Other.feed");
        }
    }

    class Home {
        private final List<Animal> animals = new ArrayList<>();

        public void add(Animal animal) {
            animals.add(animal);
        }

        public void feed(FeedVisitor visitor) {
            for (Animal animal : animals) {
                animal.accept(visitor);
            }
        }
    }
}
