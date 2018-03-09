using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Autocompletetextview.RNAutocompletetextview
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNAutocompletetextviewModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNAutocompletetextviewModule"/>.
        /// </summary>
        internal RNAutocompletetextviewModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNAutocompletetextview";
            }
        }
    }
}
