package org.example;

public @interface Strategy {
    interface MarketingStrategy {
        void show();
    }

    class MarketingStrategyA implements MarketingStrategy {
        @Override
        public void show() {
            System.out.println("买一送一");
        }
    }

    class MarketingStrategyB implements MarketingStrategy {
        @Override
        public void show() {
            System.out.println("满200减50");
        }
    }

    class MarketingStrategyC implements MarketingStrategy {
        @Override
        public void show() {
            System.out.println("满300减100");
        }
    }

    class SaleMan {
        private MarketingStrategy strategy;

        public MarketingStrategy getStrategy() {
            return strategy;
        }

        public void setStrategy(MarketingStrategy strategy) {
            this.strategy = strategy;
        }

        public void sealManShow() {
            strategy.show();
        }
    }
}
