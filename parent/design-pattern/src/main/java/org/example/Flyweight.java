package org.example;

import java.util.HashMap;
import java.util.Map;

public @interface Flyweight {
    interface AbstractBox {
        String getShape();

        default void show(String color) {
            System.out.printf("%s:%s%n", color, getShape());
        }
    }

    class IBox implements AbstractBox {
        @Override
        public String getShape() {
            return "I";
        }
    }

    class LBox implements AbstractBox {
        @Override
        public String getShape() {
            return "L";
        }
    }

    class OBox implements AbstractBox {
        @Override
        public String getShape() {
            return "O";
        }
    }

    final class BoxFactory {
        private static final BoxFactory INSTANCE = new BoxFactory();
        private final Map<String, AbstractBox> map = new HashMap<>();

        private BoxFactory() {
            IBox i = new IBox();
            map.put(i.getShape(), i);
            LBox l = new LBox();
            map.put(l.getShape(), l);
            OBox o = new OBox();
            map.put(o.getShape(), o);
        }

        public static BoxFactory getInstance() {
            return INSTANCE;
        }

        public AbstractBox getBox(String shape) {
            return map.get(shape);
        }
    }
}
