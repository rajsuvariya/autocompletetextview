## Auto Complete Text View - Under development

This is a module for accessing Native Android AutoCompleteTextView.
This is a module for accessing **Native Android AutoCompleteTextView.**

To install use the following command
~~`npm install autocompletetextview --save`~~ (Please read below section)

### Usage
Require it:

```js
import { AutoCompleteTextView } from 'autocompletetextview';
```

Render it:
```
  render() {
    return (
      ...
      <AutoCompleteTextView
        style={{ flex: 1, flexDirection: 'row', height: 60, alignSelf: 'stretch' }}
        dataSource={["Bangalore", "Pune", "Delhi", "Goa"]}
        onTextChange = {(text)=>console.log(text)}
        showDropDown = {true}
        hint = "Your Hint"
      />
      ...
    )
  }
```

### Installation
1. Add the following dependency in your package.json
```
  "dependencies": {
    "autocompletetextview": "github:rajsuvariya/autocompletetextview#development",
    ...
  }
```
2. Run `npm install`.
3. Include this module in `android/settings.gradle`:

```
...
include ':autocompletetextview' // Add this
project(':autocompletetextview').projectDir = file("../node_modules/autocompletetextview/android") // Add this
...
include ':app'
```

2. Add a dependency to your app build in `android/app/build.gradle`:

```
dependencies {
   ...
   compile project(':autocompletetextview') // Add this
}
```

3. Change your main application to "import" and "add" a new package, in `android/app/src/main/.../MainApplication.java`:

```java
import com.lugg.ReactSnackbar.ReactSnackbarPackage; // Add new import

public class MainApplication extends Application implements ReactApplication {
  ...
  
  @Override
  protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
      new MainReactPackage(),
      new RNAutocompletetextviewPackage() // Add the package here
    );
  }
}
```

4. Re-compile application using `react-native run-android`
