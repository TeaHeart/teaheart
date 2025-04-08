package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public @interface Factory {
    interface Tea {
        String getName();
    }

    class GreenTea implements Tea {
        @Override
        public String getName() {
            return "绿茶";
        }
    }

    class BlackTea implements Tea {
        @Override
        public String getName() {
            return "红茶";
        }
    }

    class TeaFactory {
        public Tea getTea(String name) {
            switch (name) {
                case "GreenTea": {
                    return new GreenTea();
                }
                case "BlackTea": {
                    return new BlackTea();
                }
                default: {
                    return null;
                }
            }
        }
    }

    class SimpleTeaFactory {
        private final Map<String, Class<? extends Tea>> map = new HashMap<>();

        public SimpleTeaFactory(String filename) throws Exception {
            Properties config = new Properties();
            config.load(getClass().getClassLoader().getResourceAsStream(filename));
            for (Map.Entry<Object, Object> entry : config.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                map.put(key, Class.forName(value).asSubclass(Tea.class));
            }
        }

        public Tea getTea(String name) throws Exception {
            Class<? extends Tea> clazz = map.get(name);
            return clazz == null ? null : clazz.newInstance();
        }
    }
}
