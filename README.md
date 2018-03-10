## Auto Complete Text View

![](https://thumbs.gfycat.com/CleverIllfatedAfricanwildcat-small.gif)

This is a module for accessing **Native Android [AutoCompleteTextView](https://developer.android.com/reference/android/widget/AutoCompleteTextView.html)**. This module is designed for Android only with no plans to support iOS. iOS does not have an equivalent native component.

**Note** : Please note that this module has been written based on one particular project requirement. It might not have support of special functionalities you want. In such cases feel free to open an issue. I will add the support at earliest.

### Table of Contents
- [Contribution Guidelines](#contribution-guidelines)
- [Installation](#installation)
  - [Manual Linking](#manual-linking)
- [Usage](#usage)
  - [Props](#props)
- [Notes](#notes)
  - [Must Set Dimensions](#must-set-dimensions)
  - [Remove Underline](#remove-underline)
- [Example](#example)

### Contribution Guidelines
If anyone would like to help me maintain this repo, then that would be a great help to me. Please reach me out on Hangout. My gmail id is rajsuvariya@gmail.com

### Installation

1. Install:
    - Using [npm](https://www.npmjs.com/#getting-started): `npm install autocompletetextview --save`
    - Using [Yarn](https://yarnpkg.com/): `yarn add autocompletetextview`

2. [Link](https://facebook.github.io/react-native/docs/linking-libraries-ios.html):
    - `react-native link autocompletetextview`
    - Or if this fails, link manually using [these steps](#manual-linking)

3. Compile application using `react-native run-android`

#### Manual Linking
Follow these steps if automatic linking (`react-native link`) failed.

1. Include this module in `android/settings.gradle`:

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
import com.reactlibrary.RNAutocompletetextviewPackage; // Add new import

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

### Usage

1. Import it in your JS:

```js
import { AutoCompleteTextView } from 'autocompletetextview';
```

2. Render it:
```js
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

#### Props

| Prop                                                                         | Type     | Default | Required | Description                                                                                              |
|------------------------------------------------------------------------------|----------|---------|----------|----------------------------------------------------------------------------------------------------------|
| dataSource                                                                   | array    |         | Yes      | Array of strings that are offered as autocomplete suggestions.                                           |
| hint                                                                         | string   | ""      |          | The string that will be rendered before text input has been entered.                                     |
| onTextChange                                                                 | function |         | Yes      | Callback that is called when the text input selection is changed. This will be called with `{ text }`.   |
| showDropDown                                                                 | bool     | `false` |          | If should show the list of suggestions on mount.                                                         |
| showDropDownArrow                                                            | bool     | `false` |          | Currently does not do anything. See [#12](https://github.com/rajsuvariya/autocompletetextview/issues/12) |
| value                                                                        | string   |         |          | If omitted, then it is an uncontrolled component.                                                        |
| [...View.props](http://facebook.github.io/react-native/docs/view.html#props) |          |         |          | All other props of the standard react-native `<View>` component.                                         |

### Notes

#### Must Set Dimensions
In current implementation, you must set a `width` and `height` by the `style` prop, otherwise the input field will not be visible.

#### Remove Underline
In current implementation, the underline is always there. You can only cover it, by setting a `backgroundColor` in the `style` prop.

### Example

The code to the screen recording in this readme is the following:

```
import React, { Component } from 'react'
import { View } from 'react-native'
import { AutoCompleteTextView } from 'autocompletetextview'

export default class App extends Component {
    render() {
        return (
            <View style={{ flex:1, justifyContent:'center', alignItems:'center' }}>
                <AutoCompleteTextView
                    style={{ height:60, width:200 }}
                    dataSource={['Bangalore', 'Pune', 'Delhi', 'Goa']}
                    onTextChange={this.handleTextChange}
                    hint="Your Hint"
                />
            </View>
        )
    }
}
```
