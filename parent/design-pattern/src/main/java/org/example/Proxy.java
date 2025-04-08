package org.example;

public @interface Proxy {
    interface Http {
        void send();
    }

    class Client implements Http {
        @Override
        public void send() {
            System.out.println("Client.send");
        }

        public void accept() {
            System.out.println("Client.accept");
        }
    }

    class StaticProxy implements Http {
        private final Http client = new Client();

        @Override
        public void send() {
            System.out.println("StaticProxy.before");
            client.send();
            System.out.println("StaticProxy.after");
        }
    }

    class DynamicProxy {
        public static Http getProxy(Http client) {
            Class<? extends Http> clazz = client.getClass();
            return (Http) java.lang.reflect.Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, args) -> {
                System.out.println("DynamicProxy.before");
                Object result = method.invoke(client, args);
                System.out.println("DynamicProxy.after");
                return result;
            });
        }
    }
}
