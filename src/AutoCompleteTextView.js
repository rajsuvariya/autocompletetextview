import PropTypes from 'prop-types';
import React from 'react'
import { requireNativeComponent, View } from 'react-native';

import ColorPropType from 'react-native/Libraries/StyleSheet/ColorPropType'

class AutoCompleteTextView extends React.Component {
  constructor(props) {
    super(props);
    this._onChange = this._onChange.bind(this);
  }
  _onChange(event: Event) {
    if (!this.props.onTextChange) {
      return;
    }
    this.props.onTextChange(event.nativeEvent.text);
  }
  render() {
    return <RCTAutoCompleteTextView {...this.props} onChange={this._onChange} />;
  }
}

AutoCompleteTextView.propTypes = {
  dataSource: PropTypes.array.isRequired,
  // disableFullscreenUI: PropTypes.bool,
  editable: PropTypes.bool,
  onTextChange: PropTypes.func,
  placeholder: PropTypes.string,
  placeholderTextColor: ColorPropType,
  // returnKeyType: PropTypes.oneOf([
  //   // Cross-platform
  //   'done',
  //   'go',
  //   'next',
  //   'search',
  //   'send',
  //   // Android-only
  //   'none',
  //   'previous'
  // ]),
  selectTextOnFocus: PropTypes.bool,
  showDropDown: PropTypes.bool,
  // showDropDownArrow: PropTypes.bool, // not supported
  threshold: PropTypes.number,
  value: PropTypes.string,
  ...View.propTypes
};

var RCTAutoCompleteTextView = requireNativeComponent('RCTAutoCompleteTextView', AutoCompleteTextView, {
  nativeOnly: {onChange: true}
});

export { AutoCompleteTextView }
