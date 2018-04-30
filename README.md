# Android Activity Navigator

Generate boilerplate code for starting Android Activity's and passing extras using annotations!


## Usage 
With Android Activity Navigator, all you need to do is annotate fields of your Activity with `@Required`, e.g.,

```java
public class ExampleActivity extends Activity {
    @Required public int index;
    @Required public String name;
...
}
```

Whenever you want to start this activity, simple call the following function from another context.

`Navigator.startExampleActivity(context, index, name);`

This will start `ExampleActivity` and pass the given extras. To retreive these extras call the `bind` function of the Navigator, i.e.,

```java
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_example);
    Navigator.bind(this);
  }
```

This will assign the extras to their respective fields, `index` and `name` in our example.

If you prefer not omit the assignment of a value, just turn of the binding like:

`@Require(bind = false) public int index;`

## Under the hood

Android Activity Navigator generates a Navigator class with functions to start Activity's and bind the extras.

```java
  public static void startDemoActivity(final Context context, final int index, final String name) {
    Intent intent = new Intent(context, DemoActivity.class);
    intent.putExtra("index", index);
    intent.putExtra("name", name);
    context.startActivity(intent);
  }

  public static void bind(final DemoActivity activity) {
    activity.index = activity.getIntent().getIntExtra("index", -1);
    activity.name = activity.getIntent().getStringExtra("name");
  }
```

## Adding Android Activity Navigator your App
This library is available through jitpack central. Just add the following to your app-level gradle file:

```gradle
dependencies {
  implementation 'com.github.mhmtk:android-activity-navigator:-SNAPSHOT'
  annotationProcessor 'com.github.mhmtk:android-activity-navigator:-SNAPSHOT'
}
```
and, the following to your top-level gradle file if you don't already have it:

```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
For importing it via another method, check out https://jitpack.io/#mhmtk/android-activity-navigator

Feel free to leave any comments/suggestions!

### MIT License

Copyright (c) [2018] [Mehmet Kologlu]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
