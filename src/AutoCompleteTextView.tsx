import React, {useState, useEffect, FunctionComponent} from 'react';
import {View, NativeEventEmitter, requireNativeComponent} from 'react-native';

const RNAutoCompleteTextView = requireNativeComponent('RNAutoCompleteTextView');

interface NativeProps {
  dataSource: string[];
  itemFormat: string;
  value: string;
  onChangeText?: Function;
  onFocus: Function;
  showDropDown: boolean;
  hint?: string;
  onItemClick: Function;
}

const AutoCompleteTextView: FunctionComponent<NativeProps & View> = (props) => {
  const eventEmitter = new NativeEventEmitter(RNAutoCompleteTextView);
  const {dataSource, itemFormat, ...rest} = props;
  const data = {dataSource: JSON.stringify(dataSource), itemFormat};
  const [lastValue, setLastValue] = useState(false);

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
    <RNAutoCompleteTextView {...rest} dataSource={data} onChange={onChange} />
  );
};

export default AutoCompleteTextView;
