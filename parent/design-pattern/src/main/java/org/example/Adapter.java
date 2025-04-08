package org.example;

public @interface Adapter {
    interface TFCard {
        void writeTF(String data);
    }

    class TFCardImpl implements TFCard {
        @Override
        public void writeTF(String data) {
            System.out.printf("TFCardImpl.writeTF:%s%n", data);
        }
    }

    interface SDCard {
        void writeSD(String data);
    }

    class SDCardImpl implements SDCard {
        @Override
        public void writeSD(String data) {
            System.out.printf("SDCardImpl.writeSD:%s%n", data);
        }
    }

    class Computer {
        public void write(SDCard card) {
            card.writeSD(card.toString());
        }
    }

    class ClassAdapter extends TFCardImpl implements SDCard {
        @Override
        public void writeSD(String data) {
            writeTF(data);
        }
    }

    class ObjectAdapter implements SDCard {
        private final TFCard card;

        public ObjectAdapter(TFCard card) {
            this.card = card;
        }

        @Override
        public void writeSD(String data) {
            card.writeTF(data);
        }
    }
}
