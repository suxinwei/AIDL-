package com.suxinwei.demo;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Fruit[] mFruits = {new Fruit("Apple", R.mipmap.apple), new Fruit("Banana", R.mipmap.banana),
            new Fruit("Orange", R.mipmap.orange), new Fruit("Watermelon", R.mipmap.watermelon),
            new Fruit("Pear", R.mipmap.pear), new Fruit("Grape", R.mipmap.grape),
            new Fruit("Pineapple", R.mipmap.pineapple), new Fruit("Strawberry", R.mipmap.strawberry),
            new Fruit("Cherry", R.mipmap.cherry), new Fruit("Mango", R.mipmap.mango)};

    private List<Fruit> mFruitList = new ArrayList<>();
    private FruitAdapter2 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();

        ListView listView = findViewById(R.id.list_view);

        mAdapter = new FruitAdapter2(getBaseContext(), mFruitList);
        listView.setAdapter(mAdapter);
    }

    private void initFruits() {
        mFruitList.clear();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int index = random.nextInt(mFruits.length);
            Fruit fruit = mFruits[index];
            mFruitList.add(fruit);
        }
    }
}
