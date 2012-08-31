FlipAnimation
=============

This class extends the Animation class and provide extra APIs to perform a horizontal and vertical view flip. It uses the Camera class for transformations to provide a smooth 3D feel.

![1] -> ![2]

![3] -> ![4]

**Download:** [JAR library](https://github.com/downloads/herroWorld/FlipAnimation/flipanimation_v1.0.jar)

## Usage
When using this library, simply add the JAR to your project:

```
Right click on project --> Properties --> Java Build Path --> Libraries --> Add External JARs
```

### Example
```
// Flag to keep track of which view is on top
boolean mIsTopView = true;

// Setting the top and bottom view for flip animation
final FlipAnimation flipAnimation = new FlipAnimation(topView, bottomView);

// Setting a horizontal flip; the other option is a vertical flip
flipAnimation.setOrientation(FlipAnimation.HORIZONTAL);

// Listening to button clicks to perform the flip
button.setOnClickListener(new OnClickListener() {

    @Override
    public void onClick(View v) {
        final boolean flipSuccessful;

        if (mIsTopView) {
            flipSuccessful = flipAnimation.flip(mIsTopView);
        } else {
            flipSuccessful = flipAnimation.flip(mIsTopView);
        }

        // If flip was not successful, that means the animation wasn't
        // done
        if (flipSuccessful) {
            mIsTopView = !mIsTopView;
        }
    }
});
```

## License
* [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

 [1]: https://github.com/downloads/herroWorld/FlipAnimation/flipAnimationExample1.png
 [2]: https://github.com/downloads/herroWorld/FlipAnimation/flipAnimationExample2.png
 [3]: https://github.com/downloads/herroWorld/FlipAnimation/flipAnimationExample3.png
 [4]: https://github.com/downloads/herroWorld/FlipAnimation/flipAnimationExample4.png
