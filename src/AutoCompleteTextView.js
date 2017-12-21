import PropTypes from 'prop-types';
import React from 'react'
import { requireNativeComponent, View } from 'react-native';

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
  value: PropTypes.string.isRequired,
  onTextChange: PropTypes.func.isRequired,
  showDropDown: PropTypes.bool,
  showDropDownArrow: PropTypes.bool,
  hint: PropTypes.string,
  ...View.propTypes
};

var RCTAutoCompleteTextView = requireNativeComponent('RCTAutoCompleteTextView', AutoCompleteTextView, {
  nativeOnly: {onChange: true}
});

export default AutoCompleteTextView 
