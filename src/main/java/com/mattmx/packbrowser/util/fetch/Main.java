package com.mattmx.packbrowser.util.fetch;

import com.mattmx.packbrowser.util.packhub.AbstractPack;
import com.mattmx.packbrowser.util.packhub.PackHub;

public class Main {
    public static void main(String[] args) {
        new Fetch<>(() -> PackHub.api().getPackFromId(1))
                .then(result -> System.out.println(result.isValid() ? result.getName() : result.getError()))
                .fail(Throwable::printStackTrace).get();
    }
}