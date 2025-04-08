package org.example;

public @interface Template {
    abstract class AbstractCook {
        public final void cookProcess() {
            pourOil();
            heatOil();
            pourVegetable();
            pourSauce();
            pourSugar();
            fry();
        }

        public void pourOil() {
            System.out.println("倒油");
        }

        public void heatOil() {
            System.out.println("热油");
        }

        public void pourSugar() {
            if (isPourSugar()) {
                System.out.println("加糖");
            }
        }

        public boolean isPourSugar() {
            return true;
        }

        public abstract void pourVegetable();

        public abstract void pourSauce();

        public void fry() {
            System.out.println("翻炒");
        }
    }

    class CookCabbage extends AbstractCook {
        @Override
        public void pourVegetable() {
            System.out.println("包菜");
        }

        @Override
        public void pourSauce() {
            System.out.println("辣椒");
        }
    }

    class CookFloweringCabbage extends AbstractCook {
        @Override
        public void pourVegetable() {
            System.out.println("菜心");
        }

        @Override
        public void pourSauce() {
            System.out.println("蒜蓉");
        }

        @Override
        public boolean isPourSugar() {
            return false;
        }
    }
}
