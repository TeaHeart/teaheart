package org.example;

public @interface Facade {
    class Light {
        public void on() {
            System.out.println("Light.on");
        }

        public void off() {
            System.out.println("Light.off");
        }
    }

    class TV {
        public void on() {
            System.out.println("TV.on");
        }

        public void off() {
            System.out.println("TV.off");
        }
    }

    class AirConditioner {
        public void on() {
            System.out.println("AirConditioner.on");
        }

        public void off() {
            System.out.println("AirConditioner.off");
        }
    }

    class IntelligentTerminal {
        private final Light light = new Light();
        private final TV tv = new TV();
        private final AirConditioner airConditioner = new AirConditioner();

        public void listen(String msg) {
            if (msg.contains("打开")) {
                light.on();
                tv.on();
                airConditioner.on();
            } else if (msg.contains("关闭")) {
                light.off();
                tv.off();
                airConditioner.off();
            }
        }
    }
}
