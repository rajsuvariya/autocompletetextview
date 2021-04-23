### Auto Complete Text View

This is a module for accessing **Native Android AutoCompleteTextView.**

**Note** : Please note that this module has been written based on one particular project requirement. It might not have support of special functionalities you want. In such cases feel free to open an issue. I will add the support at earliest.

### Installation (using npm)
1. Run `npm install rn-android-autocompletetextview --save`.
2. Add the following lines in your android/settings.gradle
    ```
      include ':rn-android-autocompletetextview'
      project(':rn-android-autocompletetextview').projectDir = file("../node_modules/rn-android-autocompletetextview/android")
    ```
3. Add the following dependency in android/app/build.gradle
    ```
      dependencies {
        compile project(':rn-android-autocompletetextview')
        ...
      }
    ```
4. Open MainApplication or ReactApplication located at `android/app/src/main/java/com/<your package name>/` and add following packager
    ```
      new RNAutocompleteTextViewPackage()
    ```


    like this

    ```
      @Override
      protected List<ReactPackage> getPackages() {
          return Arrays.<ReactPackage>asList(
                  new MainReactPackage(),
                  new RNAutocompleteTextViewPackage()
          );
      }
    ```
    and import the following dependency

    ```
    import com.reactlibrary.RNAutocompleteTextViewPackage;
    ```
5. USAGE:
    ```
    import { AutoCompleteTextView } from 'rn-android-autocompletetextview';

    const items = [
      {firstName: 'Bob', lastName: 'Smith},
      {firstName: 'John', lastName: 'Jones},
    ];

    const onDropdownClick = (position: number) => {
      const selectedItem = items[position];
      // Do something with item
    };

    render() {
      ...
        <AutoCompleteTextView
          style={{ flex: 1, flexDirection: 'row', height: 60, alignSelf: 'stretch' }}
          dataSource={items}
          itemFormat={'${firstName} ${lastName}'}
          value={value}
          onChangeText={(text)=>console.log(text)}
          showDropDown={false}
          hint={'My Hint'}
          onItemClick={onDropdownClick}
        />
        ...
    }
    ```
6. Run application using `react-native run-android` command
