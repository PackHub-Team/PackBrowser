package com.mattmx.packbrowser.util.fetch;

import com.mattmx.packbrowser.util.packhub.AbstractPack;
import com.mattmx.packbrowser.util.packhub.PackHub;

public class Main {
    public static void main(String[] args) {
        // URL example
        new Fetch<>("https://www.google.com/")
                .then(System.out::println) // print the content of the webpage
                .fail(Throwable::printStackTrace) // print stacktrace on a failure
                .get(); // actually fetch the data

        // Function example
        // Provide the DataType that the function returns
        new Fetch<AbstractPack>(() -> PackHub.api().getPackFromId(1))
                // function to convert String into an AbstractPack (JSON string -> AbstractPack)
                .then(result -> {
                    // print result.getName() if isValid() is true, otherwise print the error.
                    System.out.println(result.isValid() ? result.getName() : result.getError());
                }).fail(exception -> {
                    exception.printStackTrace(); // print stacktrace on failure
                }).get(); // fetch data
        System.out.println("Async!");
    }
}