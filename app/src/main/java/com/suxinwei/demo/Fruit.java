package com.suxinwei.demo;

/**
 * ClassName:Fruit. <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/6/14 23:45 <br/>
 *
 * @author suxinwei
 */

public class Fruit {
    public String name;
    public int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}
