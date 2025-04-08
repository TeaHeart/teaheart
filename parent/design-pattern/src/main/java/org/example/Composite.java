package org.example;

import java.util.ArrayList;
import java.util.List;

public @interface Composite {
    abstract class MenuComponent {
        protected String name;
        protected int level;

        public MenuComponent(String name) {
            this.name = name;
        }

        public void add(MenuComponent component) {
        }

        public MenuComponent get(int index) {
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < level; i++) {
                sb.append(' ');
            }
            sb.append(name).append('\n');
            return sb.toString();
        }
    }

    class Menu extends MenuComponent {
        private final List<MenuComponent> components = new ArrayList<>();

        public Menu(String name) {
            super(name);
        }

        @Override
        public void add(MenuComponent component) {
            component.level = level + 1;
            components.add(component);
        }

        @Override
        public MenuComponent get(int index) {
            return components.get(index);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(super.toString());
            for (MenuComponent component : components) {
                sb.append(component);
            }
            return sb.toString();
        }
    }

    class MenuItem extends MenuComponent {
        public MenuItem(String name) {
            super(name);
        }
    }
}
