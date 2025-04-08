package org.example;

import java.util.ArrayList;
import java.util.List;

public @interface Observer {
    interface VideoObserver {
        void update(String msg);
    }

    class User implements VideoObserver {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public void update(String msg) {
            System.out.printf("%s<-%s\n", name, msg);
        }
    }

    interface Video {
        void attach(VideoObserver observer);

        void detach(VideoObserver observer);

        void notify(String msg);
    }

    class SubscriptionVideo implements Video {
        private final List<VideoObserver> observers = new ArrayList<>();

        @Override
        public void attach(VideoObserver observer) {
            observers.add(observer);
        }

        @Override
        public void detach(VideoObserver observer) {
            observers.remove(observer);
        }

        @Override
        public void notify(String msg) {
            for (VideoObserver observer : observers) {
                observer.update(msg);
            }
        }
    }
}
