package org.example;

public @interface Bridge {
    interface VideoFile {
        void decode(String filename);
    }

    class AVI implements VideoFile {
        @Override
        public void decode(String filename) {
            System.out.printf("AVI.decode:%s%n", filename);
        }
    }

    class RMVB implements VideoFile {
        @Override
        public void decode(String filename) {
            System.out.printf("RMVB.decode:%s%n", filename);
        }
    }

    abstract class OperatingSystem {
        protected VideoFile file;

        public OperatingSystem(VideoFile file) {
            this.file = file;
        }

        public void setFile(VideoFile file) {
            this.file = file;
        }

        public abstract void play(String filename);
    }

    class Windows extends OperatingSystem {
        public Windows(VideoFile file) {
            super(file);
        }

        @Override
        public void play(String filename) {
            System.out.println("Windows.play");
            file.decode(filename);
        }
    }

    class Mac extends OperatingSystem {
        public Mac(VideoFile file) {
            super(file);
        }

        @Override
        public void play(String filename) {
            System.out.println("Mac.play");
            file.decode(filename);
        }
    }
}
