package org.example;

public @interface AbstractFactory {
    interface Dessert {
        String getName();
    }

    class Tiramisu implements Dessert {
        @Override
        public String getName() {
            return "提拉米苏";
        }
    }

    class MochaMousse implements Dessert {
        @Override
        public String getName() {
            return "抹茶慕斯";
        }
    }

    interface DessertFactory {
        Dessert getDessert();
    }

    class TiramisuFactory implements DessertFactory {
        @Override
        public Dessert getDessert() {
            return new Tiramisu();
        }
    }

    class MochaMousseFactory implements DessertFactory {
        @Override
        public Dessert getDessert() {
            return new MochaMousse();
        }
    }

    class AbstractDessertFactory {
        public DessertFactory getFactory(String name) {
            switch (name) {
                case "Tiramisu":
                    return new TiramisuFactory();
                case "MochaMousse":
                    return new MochaMousseFactory();
                default:
                    return null;
            }
        }
    }
}
