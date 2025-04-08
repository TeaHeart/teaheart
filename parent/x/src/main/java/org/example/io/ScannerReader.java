package org.example.io;

import org.example.function.Func1;
import org.example.function.FuncX;

import java.io.*;
import java.util.StringTokenizer;

public class ScannerReader extends Reader {
    private final BufferedReader br;
    private StringTokenizer st = new StringTokenizer("");

    public ScannerReader(BufferedReader br) {
        super(br);
        this.br = br;
    }

    public ScannerReader(InputStream in) {
        this(new BufferedReader(new InputStreamReader(in)));
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return br.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        br.close();
    }

    public String nextLine() {
        return FuncX.sneaky(br::readLine);
    }

    public String next() {
        while (!st.hasMoreTokens()) {
            st = new StringTokenizer(nextLine());
        }
        return st.nextToken();
    }

    public <T> T next(Func1<String, T> parser) {
        return FuncX.sneaky(parser, next());
    }

    public int nextInt() {
        return next(Integer::parseInt);
    }

    public long nextLong() {
        return next(Long::parseLong);
    }

    public float nextFloat() {
        return next(Float::parseFloat);
    }

    public double nextDouble() {
        return next(Double::parseDouble);
    }
}