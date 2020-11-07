package ru.dolbak.vkkavkaton;

import android.graphics.Bitmap;

public class Picture {
    String ID;
    boolean isPrimary;
    Bitmap bitmap;
    String ERROR;

    public Picture(String ID, boolean isPrimary) {
        this.ID = ID;
        this.isPrimary = isPrimary;
    }
    public Picture(String ID, Bitmap bitmap){
        this.ID = ID;
        this.bitmap = bitmap;
        this.ERROR = "OK";
    }

    public Picture(String ID, String ERROR){
        this.ID = ID;
        this.ERROR = ERROR;
    }
}
