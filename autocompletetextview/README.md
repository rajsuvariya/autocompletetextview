
# react-native-autocompletetextview

## Getting started

`$ npm install react-native-autocompletetextview --save`

### Mostly automatic installation

`$ react-native link react-native-autocompletetextview`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-autocompletetextview` and add `RNAutocompletetextview.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNAutocompletetextview.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNAutocompletetextviewPackage;` to the imports at the top of the file
  - Add `new RNAutocompletetextviewPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-autocompletetextview'
  	project(':react-native-autocompletetextview').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-autocompletetextview/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-autocompletetextview')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNAutocompletetextview.sln` in `node_modules/react-native-autocompletetextview/windows/RNAutocompletetextview.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Autocompletetextview.RNAutocompletetextview;` to the usings at the top of the file
  - Add `new RNAutocompletetextviewPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNAutocompletetextview from 'react-native-autocompletetextview';

// TODO: What to do with the module?
RNAutocompletetextview;
```
  