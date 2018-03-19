package com.reactlibrary;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView;

public class AtTokenizer implements MultiAutoCompleteTextView.Tokenizer {
    private char startSigil = '@';
    public int findTokenStart(CharSequence text, int cursor) {
      int i = cursor;

      android.util.Log.v("ReactNative", "findTokenStart, i: " + i);


      // hello @Bangalore angaloraloralor goa gao.
      // hello @Yasir ali. and hi @Dima or @Yasir @Dima @Ali is a match. but this should not match cuz its an email right yasir@blah.edu? cool that didnt match
      //Move i to next char after first '.'.
      while (i > 0 && text.charAt(i - 1) != startSigil) {
        i--;
      }
      //Move i past all contiguous spaces after a '.'. OLD
      while (i < cursor && text.charAt(i) == startSigil) {
        i++;
      }

      return i;
    }

    public int findTokenEnd(CharSequence text, int cursor) {
      int i = cursor;
      int len = text.length();

      android.util.Log.v("ReactNative", "findTokenEnd, i: " + i);

      //Find sigil if there.
      while (i < len) {
        if (text.charAt(i) == startSigil) {
          return i;
        } else {
          i++;
        }
      }

      return len;
    }

    public CharSequence terminateToken(CharSequence text) {
      int i = text.length();

      android.util.Log.v("ReactNative", "terminateToken, i: " + i);

      //Backup from the right end of the string until the first non empty. OLD
      while (i > 0 && text.charAt(i - 1) == startSigil) {
        i--;
      }

      //If at a sigil return full string.
      if (i > 0 && text.charAt(i - 1) == startSigil) {
        return text;
      } else {
        if (text instanceof Spanned) {
          SpannableString sp = new SpannableString(text);
          TextUtils.copySpansFrom((Spanned) text,
                                  0,
                                  text.length(),
                                  Object.class,
                                  sp,
                                  0);
          return sp;
        } else {
          return text;
        }
      }
    }

}
