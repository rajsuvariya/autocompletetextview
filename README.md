### Auto Complete Text View
 
This is a module for accessing Native Android AutoCompleteTextView.
This is a module for accessing **Native Android AutoCompleteTextView.**
 
To install use the following command
~~`npm install autocompletetextview --save`~~ (Please read below section)

### Installation (copy method) (requires RN >= 0.47)

1. Download a zip of this repo
2. Copy the files (`AutoCompleteTextViewManager.java`, `AutoCompleteTextViewPackager.java`, & `MyAutoCompleteTextView.java`) from `autocompletetextview/android/app/src/main/java/com/autocompletetextview/` and paste it into your project at the path `/YOUR_PROJECT_NAME\android\app\src\main\java\com\YOUR_PROJECT_NAME\`
3. Paste into the import section of `YOUR_PROJECT_NAME\android\app\src\main\java\com\YOUR_PROJECT_NAME\MainApplication.java` the following:

   ```
   import com.autocompletetextview.AutoCompleteTextViewPackager;
   ```
 
 4. Into this same file, add `new AutoCompleteTextViewPackager()` in the `getPackages()` section like this:
 
    ```
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new AutoCompleteTextViewPackager() // <<<< add this
      );
    }    
    ```
5. Copy from this repo, the `autocomletetextview/src/AutoCompleteTextView.js` and paste it into anywhere in your javascript sources
6. Install the `prop-types` package by doing `npm i prop-types`
7. Then import and use it in your javascript as usual like this:

   ```
    import React, { Component } from 'react';
    import { Text, View } from 'react-native';

    import AutoCompleteTextView from './AutoCompleteTextView'

    export default class App extends Component {
      state = {
        value: ''
      }
      render() {
        const { value } = this.state;

        return (
          <View style={{ flex:1, justifyContent:'center', alignItems:'center'}}>
            <AutoCompleteTextView dataSource={['cat', 'dog', 'tiger']} value={value} onTextChange={this.handleTextChange} />
          </View>
        );
      }

      handleTextChange = value => console.log('value:', value);
    }
   ```
8. Make sure to recompile by doing `react-native run-android`
    

### Help Required

This module is built on the old version of React Native, if anyone is interested contribute by migrating it to new version please connect with me on rajsuvariya@gmail.com. It would be a great help to me and to the community.

Also, there were some issues while pushing it to npm so if you are installing it from npm then it won't work as expected. Will throw lot of errors like [https://github.com/rajsuvariya/autocompletetextview/issues/1].

Ad-hoc solution: If you need it very urgently then just clone the repo and copy paste required files, it should work as expected.
