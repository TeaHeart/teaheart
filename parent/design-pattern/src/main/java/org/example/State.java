package org.example;

public @interface State {
    abstract class LiftState {
        protected Context context;

        public abstract void open();

        public abstract void close();

        public abstract void run();

        public abstract void stop();
    }

    class OpeningState extends LiftState {
        @Override
        public void open() {
            System.out.println("OpeningState.open");
        }

        @Override
        public void close() {
            context.setState(Context.CLOSING);
            context.close();
        }

        @Override
        public void run() {
        }

        @Override
        public void stop() {
        }
    }

    class ClosingState extends LiftState {
        @Override
        public void open() {
            context.setState(Context.OPENING);
            context.open();
        }

        @Override
        public void close() {
            System.out.println("ClosingState.close");
        }

        @Override
        public void run() {
            context.setState(Context.RUNNING);
            context.run();
        }

        @Override
        public void stop() {
            context.setState(Context.STOPPING);
            context.stop();
        }
    }

    class RunningState extends LiftState {
        @Override
        public void open() {
        }

        @Override
        public void close() {
        }

        @Override
        public void run() {
            System.out.println("RunningState.run");
        }

        @Override
        public void stop() {
            context.setState(Context.STOPPING);
            context.stop();
        }
    }

    class StoppingState extends LiftState {
        @Override
        public void open() {
            context.setState(Context.OPENING);
            context.open();
        }

        @Override
        public void close() {
            context.setState(Context.CLOSING);
            context.close();
        }

        @Override
        public void run() {
            context.setState(Context.RUNNING);
            context.run();
        }

        @Override
        public void stop() {
            System.out.println("StoppingState.stop");
        }
    }

    class Context {
        public static final LiftState OPENING = new OpeningState();
        public static final LiftState CLOSING = new ClosingState();
        public static final LiftState RUNNING = new RunningState();
        public static final LiftState STOPPING = new StoppingState();

        private LiftState state;

        public void setState(LiftState state) {
            this.state = state;
            this.state.context = this;
        }

        public void open() {
            state.open();
        }

        public void close() {
            state.close();
        }

        public void run() {
            state.run();
        }

        public void stop() {
            state.stop();
        }
    }
}
