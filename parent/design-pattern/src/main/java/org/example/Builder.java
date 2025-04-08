package org.example;

public @interface Builder {
    class Cake {
        private String name;
        private String packing;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPacking() {
            return packing;
        }

        public void setPacking(String packing) {
            this.packing = packing;
        }

        @Override
        public String toString() {
            return String.format("Cake{name='%s', packing='%s'}", name, packing);
        }
    }

    interface CakeBuilder {
        void prepare();

        void making();

        void packaging();

        Cake complete();
    }

    class CreamCakeBuilder implements CakeBuilder {
        private Cake cake;

        @Override
        public void prepare() {
            cake = new Cake();
        }

        @Override
        public void making() {
            cake.setName("奶油蛋糕");
        }

        @Override
        public void packaging() {
            cake.setPacking("透明包装");
        }

        @Override
        public Cake complete() {
            return cake;
        }
    }

    class ChocolateCakeBuilder implements CakeBuilder {
        private Cake cake;

        @Override
        public void prepare() {
            cake = new Cake();
        }

        @Override
        public void making() {
            cake.setName("巧克力蛋糕");
        }

        @Override
        public void packaging() {
            cake.setPacking("棕色包装");
        }

        @Override
        public Cake complete() {
            return cake;
        }
    }

    class CakeBuilderBaker {
        private final CakeBuilder builder;

        public CakeBuilderBaker(CakeBuilder builder) {
            this.builder = builder;
        }

        public Cake build() {
            builder.prepare();
            builder.making();
            builder.packaging();
            return builder.complete();
        }
    }

    class Computer {
        private final String board;
        private final String cpu;
        private final String memory;
        private final String disk;

        public Computer(String board, String cpu, String memory, String disk) {
            this.board = board;
            this.cpu = cpu;
            this.memory = memory;
            this.disk = disk;
        }

        @Override
        public String toString() {
            return String.format("Computer{board='%s', cpu='%s', memory='%s', disk='%s'}", board, cpu, memory, disk);
        }

        public static class ComputerBuilder {
            private String board;
            private String cpu;
            private String memory;
            private String disk;

            public ComputerBuilder setBoard(String board) {
                this.board = board;
                return this;
            }

            public ComputerBuilder setCpu(String cpu) {
                this.cpu = cpu;
                return this;
            }

            public ComputerBuilder setMemory(String memory) {
                this.memory = memory;
                return this;
            }

            public ComputerBuilder setDisk(String disk) {
                this.disk = disk;
                return this;
            }

            public Computer build() {
                return new Computer(board, cpu, memory, disk);
            }
        }
    }
}
