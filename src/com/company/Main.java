package com.company;

/**
 * Created by louis on 3/30/15 to fix Intellij issues
 */
public class Main {
    public static void main(String[] args)
    {
        ConcurrentClient client = new ConcurrentClient(8080, "10.0.0.59");
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            client.runRequest(false);
        }

        long endTime = System.nanoTime();

        long runTime = (endTime - startTime)/1000000;
        System.out.println(runTime);

    }
}
