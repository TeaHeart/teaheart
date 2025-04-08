package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public @interface Command {
    class Order {
        private final int id;
        private final Map<String, Integer> foods = new HashMap<>();

        public Order(int id) {
            this.id = id;
        }

        public Order addFood(String name, int num) {
            foods.put(name, num);
            return this;
        }
    }

    class Chef {
        public void makeFood(String name, int num) {
            System.out.printf("%s:%d个\n", name, num);
        }
    }

    interface Cmd {
        void exec();
    }

    class OrderCmd implements Cmd {
        private final Chef receiver;
        private final Order order;

        public OrderCmd(Chef receiver, Order order) {
            this.receiver = receiver;
            this.order = order;
        }

        @Override
        public void exec() {
            System.out.printf("开始订单:%d\n", order.id);
            for (Map.Entry<String, Integer> entry : order.foods.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                receiver.makeFood(key, value);
            }
            System.out.printf("完成订单:%d\n", order.id);
        }
    }

    class Waiter {
        private final List<Cmd> cmdList = new ArrayList<>();

        public Waiter accept(Cmd cmd) {
            cmdList.add(cmd);
            return this;
        }

        public void orderUp() {
            for (Cmd cmd : cmdList) {
                cmd.exec();
            }
        }
    }
}
