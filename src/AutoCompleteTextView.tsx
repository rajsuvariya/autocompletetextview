import React, {useState, useEffect, FunctionComponent, useRef} from 'react';
import {View, NativeEventEmitter, requireNativeComponent} from 'react-native';

interface NativeProps {
  dataSource: string[];
  itemFormat: string;
  value: string;
  onChangeText?: Function;
  onFocus: Function;
  onBlur: Function;
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

  useEffect(() => {
    const subscription = eventEmitter.addListener('onItemClick', props.onItemClick);
    return () => subscription.remove();
  }, [props.onItemClick]);

  useEffect(() => {
    const subscription = eventEmitter.addListener('onFocus', props.onFocus);
    return () => subscription.remove();
  }, [props.onFocus]);

  useEffect(() => {
    const subscription = eventEmitter.addListener('onBlur', props.onBlur);
    return () => subscription.remove();
  }, [props.onBlur]);

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
