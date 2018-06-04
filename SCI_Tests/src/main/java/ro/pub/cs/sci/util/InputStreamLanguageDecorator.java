package ro.pub.cs.sci.util;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamLanguageDecorator extends InputStream {

    private InputStream stream;

    public InputStreamLanguageDecorator(InputStream stream, String language) {
        this.stream = stream;
        temp = language.getBytes();
    }

    final byte[] temp;
    int position = 0;

    boolean writingTemp = false;

    @Override
    public int read() throws IOException {
        int toReturn;

        if (writingTemp) {
            if (position == temp.length) {
                position = 0;
                writingTemp = false;
                toReturn = read();
            } else {
                toReturn = temp[position++];
            }
        } else {
            int original = stream.read();

            if (original == '#') {
                writingTemp = true;
                toReturn = read();
            } else {
                toReturn = original;
            }
        }

        return toReturn;
    }

}
