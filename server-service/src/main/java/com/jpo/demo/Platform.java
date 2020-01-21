package com.jpo.demo;

public class Platform {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj instanceof Platform){
            if (((Platform) obj).x == this.x && ((Platform) obj).y == this.y)
                return true;
        }
        return false;
    }
}
