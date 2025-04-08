package org.example;

public @interface Singleton {
    class Single1 {
        private static final Single1 INSTANCE = new Single1();

        private Single1() {
        }

        public static Single1 getInstance() {
            return INSTANCE;
        }
    }

    enum Single2 {
        INSTANCE;

        public static Single2 getInstance() {
            return INSTANCE;
        }
    }

    class Single3 {
        private Single3() {
        }

        public static Single3 getInstance() {
            return Holder.INSTANCE;
        }

        private static final class Holder {
            private static final Single3 INSTANCE = new Single3();
        }
    }

    class Single4 {
        private static volatile Single4 INSTANCE;

        private Single4() {
        }

        public static Single4 getInstance() {
            if (INSTANCE == null) {
                synchronized (Single4.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new Single4();
                    }
                }
            }
            return INSTANCE;
        }
    }
}
