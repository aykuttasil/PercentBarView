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
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 2", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg", "text 3", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 4", PercentBarView.BarField.RIGHT));
        mList.add(new BarImageModel("https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg", "text 5", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 6", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://yt3.ggpht.com/-wh6msZtAzCU/AAAAAAAAAAI/AAAAAAAAAAA/ERwt1WSIOUA/s900-c-k-no-rj-c0xffffff/photo.jpg", "text 7", PercentBarView.BarField.LEFT));
        mList.add(new BarImageModel("https://pbs.twimg.com/profile_images/2658478840/b923df35a917b4acdce24d0e03c1a61a_400x400.png", "text 8", PercentBarView.BarField.LEFT));

        View alphaView = findViewById(R.id.ImageViewSoruImage);
        percentBarView = (PercentBarView) findViewById(R.id.PercentBarView);
        percentBarView.addAlphaView(alphaView);
        percentBarView.setRightValue(70);
        percentBarView.setLeftValue(1);
        percentBarView.setImages(mList);
        percentBarView.setTitleList("X List");
        percentBarView.setRightBarColor(Color.MAGENTA);
```
AND
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


<p align="center">
  <img src="https://cloud.githubusercontent.com/assets/3179872/18619207/44e432da-7dff-11e6-8988-91d1faf4eed0.gif" width="350"/>
</p>


# Installation

Gradle

Add it as a dependency in your app's build.gradle file
```
compile 'com.aykuttasil:percentbar:2.0.0'
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
