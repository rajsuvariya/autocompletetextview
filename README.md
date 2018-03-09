### Auto Complete Text View - Under development

This is a module for accessing Native Android AutoCompleteTextView.
This is a module for accessing **Native Android AutoCompleteTextView.**

To install use the following command
~~`npm install autocompletetextview --save`~~ (Please read below section)

### Installation
1. Add the following dependency in your package.json
```
  "dependencies": {
    "autocompletetextview": "github:rajsuvariya/autocompletetextview#development",
    ...
  }
```
2. Run `npm install`.
3. Add the following lines in your android/settings.gradle
```
  include ':autocompletetextview'
  project(':autocompletetextview').projectDir = file("../node_modules/autocompletetextview/android")
```
4. Add the following dependency in android/app/build.gradle
```
  dependencies {
    compile project(':autocompletetextview')
    ...
  }
```
5. Open MainApplication or ReactApplication located at `android/app/src/main/java/com/<your package name>/` and add following packager
```
  new RNAutocompletetextviewPackage()
```
  and import the following dependency

```
  import com.reactlibrary.RNAutocompletetextviewPackage;
```

  like this

```
  @Override
  protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
              new MainReactPackage(),
              new RNAutocompletetextviewPackage()
      );
  }
```
6. USAGE:
```
    import { AutoCompleteTextView } from 'autocompletetextview';
    render() {
	    ...
        <AutoCompleteTextView
          style={{ flex: 1, flexDirection: 'row', height: 60, alignSelf: 'stretch' }}
          dataSource={["Bangalore", "Pune", "Delhi", "Goa"]}
          onTextChange = {(text)=>console.log(text)}
          showDropDown = {true}
          hint = "Your Hint"
        />
        ...
    }
```
7. Run application using `react-native run-android` command
