package com.mattmx.packbrowser.util.packhub;

import lombok.Getter;

public abstract class ValidResponse {
    @Getter private String error;
     @Getter private String message;
    public boolean isValid() {
        return error == null;
    }
}
