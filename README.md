Trying to extend ReactEditText

### Auto Complete Text View

This is a module for accessing **Native Android AutoCompleteTextView.**

**Note** : Please note that this module has been written based on one particular project requirement. It might not have support of special functionalities you want. In such cases feel free to open an issue. I will add the support at earliest.

### Contribution Guidelines
If anyone would like to help me maintain this repo, then that would be a great help to me. Please reach me out on Hangout. My gmail id is rajsuvariya@gmail.com

### Installation (using npm)
1. Run `npm install autocompletetextview --save`.
2. Add the following lines in your android/settings.gradle
    ```
      include ':autocompletetextview'
      project(':autocompletetextview').projectDir = file("../node_modules/autocompletetextview/android")
    ```
3. Add the following dependency in android/app/build.gradle
    ```
      dependencies {
        compile project(':autocompletetextview')
        ...
      }
    ```
4. Open MainApplication or ReactApplication located at `android/app/src/main/java/com/<your package name>/` and add following packager
    ```
      new RNAutocompletetextviewPackage()
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
    and import the following dependency

    ```
    import com.reactlibrary.RNAutocompletetextviewPackage;
    ```
5. USAGE:
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
6. Run application using `react-native run-android` command
