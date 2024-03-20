package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.*;
import java.util.Collections;
import java.util.stream.Collectors;

public class LambdaRequestStreamHandler implements RequestStreamHandler {

    public void handleRequest(InputStream input, OutputStream out, Context contex) throws IOException {
        String s = new BufferedReader(
                new InputStreamReader(input,"UTF-8"))
                        .lines()
                        .collect(Collectors.joining());

        out.write(("Hello World" + s).getBytes());
    }
}
