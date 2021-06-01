import React, {useState, useEffect, FunctionComponent, useRef} from 'react';
import {View, NativeEventEmitter, requireNativeComponent} from 'react-native';
// import setAndForwardRef from 'react-native/Libraries/Utilities/setAndForwardRef';
// https://github.com/facebook/react-native/Libraries/Components/TextInput/TextInput.js

interface NativeProps {
  dataSource: string[];
  itemFormat: string;
  value: string;
  onChangeText?: Function;
  onFocus: Function;
  showDropDown: boolean;
  hint?: string;
  onItemClick: Function;
  forwardedRef: any;
}

const AutoCompleteTextView: FunctionComponent<NativeProps & View> = (props) => {
  const eventEmitter = new NativeEventEmitter(RNAutoCompleteTextView);
  const {dataSource, itemFormat, forwardedRef, ...rest} = props;
  const data = {dataSource: JSON.stringify(dataSource), itemFormat};
  const [lastValue, setLastValue] = useState(false);
  const inputRef = useRef(null);

  // const _setNativeRef = setAndForwardRef({
  //   getForwardedRef: () => forwardedRef,
  //   setLocalRef: (ref) => {
  //     inputRef.current = ref;
  //   },
  // });

  useEffect(() => {
    eventEmitter.addListener('onItemClick', props.onItemClick);
    return () => eventEmitter.removeListener('onItemClick', props.onItemClick);
  }, [props.onItemClick]);

  useEffect(() => {
    eventEmitter.addListener('onFocus', props.onFocus);
    return () => eventEmitter.removeListener('onFocus', props.onFocus);
  }, [props.onFocus]);

  const onChange = (event: Event) => {
    if (!props.onChangeText || lastValue === event.nativeEvent.text) {
      return;
    }
    setLastValue(event.nativeEvent.text);
    props.onChangeText(event.nativeEvent.text);
  };

  return (
    <RNAutoCompleteTextView {...rest} dataSource={data} ref={forwardedRef} onChange={onChange} />
  );
};

var RNAutoCompleteTextView = requireNativeComponent('RNAutoCompleteTextView', AutoCompleteTextView, {
  nativeOnly: {onChange: true}
});

export { AutoCompleteTextView };
