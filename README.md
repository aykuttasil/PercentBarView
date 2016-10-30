[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PercentBarView-green.svg?style=true)](https://android-arsenal.com/details/1/4373)

# PercentBar 

Show result in Percent Bar with animation.

# How to Use

```
        List<BarImageModel> mList = new ArrayList<>();
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/2b/74/04/2b74046c2e2e488fe1f2be60680671dd.jpg", "Abc", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/65/01/b5/6501b5ae42a0c1aa863b1ae33a746ce2.jpg", "Def", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/5a/e9/50/5ae9501fc3b49810db7901873f77d6f7.jpg", "Lorem", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/9d/1c/86/9d1c861252a4d0067def3f567467b175.jpg", "Qwerty", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/5e/bd/17/5ebd1769bf115773ad83803b29ea4bac.jpg", "WWW", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://s-media-cache-ak0.pinimg.com/564x/67/51/c3/6751c3d814fdda7db7e4844c3007db7c.jpg", "Aykut", PercentBarView.BarField.LEFT));
        
        View alphaView = findViewById(R.id.ImageViewSoruImage);
        percentBarView = (PercentBarView) findViewById(R.id.PercentBarView);
        percentBarView.addAlphaView(alphaView);
        percentBarView.setRightBarValue(70);
        percentBarView.setLeftBarValue(30);
        percentBarView.setImages(mList);
        percentBarView.setImagesListTitle("X List");
        percentBarView.setRightBarColor(Color.MAGENTA);        
```

**and**

```
                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent">

                    <com.aykuttasil.percentbarapp.util.AutoFitTextView
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:ellipsize="none"
                        android:gravity="center"
                        android:maxLines="5"
                        android:padding="5dp"
                        android:text="X"
                        android:textSize="100sp" />

                    <com.aykuttasil.percentbar.PercentBarView
                        android:id="@+id/PercentBarView"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:layout_alignParentBottom="true"
                        android:layout_centerHorizontal="true"
                        custom:barLeftColor="@color/colorAccent"
                        custom:barRightColor="@color/colorPrimary" />

                </RelativeLayout>           
```

**All Feature**

```
        View alphaView = findViewById(R.id.ImageViewSoruImage);
        PercentBarView percentBarView = (PercentBarView) findViewById(R.id.PercentBarView);
        
        percentBarView.setRightBarValue(70);
        percentBarView.setLeftBarValue(1);
        
        percentBarView.setImages(mList);
        percentBarView.setImagesListTitle("X List");
        percentBarView.setImageListItemSize(50)
        
        percentBarView.setLeftBarColor(Color.GREEN);
        percentBarView.setRightBarColor(Color.MAGENTA);

        percentBarView.addAlphaView(alphaView);
        percentBarView.setAlphaViewValue(0.5f); // alpha value
        percentBarView.setAnimAlphaViewDuration(3000);
        percentBarView.setAnimBarDuration(2000);
        
        percentBarView.setLeftBarWidth(60);
        percentBarView.setRightBarWidth(40);
        
        percentBarView.setAutoShow(true); // show bar when start app
```

<!-- ![2016_10_24_13_53_14](https://cloud.githubusercontent.com/assets/3179872/19643347/7f6be100-99f2-11e6-984d-aa8a966b705a.gif)-->

<p align="center">
  <img src="https://cloud.githubusercontent.com/assets/3179872/19643347/7f6be100-99f2-11e6-984d-aa8a966b705a.gif" width="350"/>
</p>

<!--<p align="center">
  <img src="https://cloud.githubusercontent.com/assets/3179872/18619207/44e432da-7dff-11e6-8988-91d1faf4eed0.gif" width="350"/>
</p>-->


# Installation

Gradle

Add it as a dependency in your app's build.gradle file
```
compile 'com.aykuttasil:percentbar:3.0.3'
```


# License 

```
Copyright 2016 aykuttasil - Aykut Asil

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
