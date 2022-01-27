package com.latinsud.android.slidetypekeyboard;

import android.text.InputType;
import android.view.inputmethod.EditorInfo;

public final class InputTypeUtils implements InputType {

    private static final int WEB_TEXT_PASSWORD_INPUT_TYPE =
            TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_WEB_PASSWORD;
    private static final int WEB_TEXT_EMAIL_ADDRESS_INPUT_TYPE =
            TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS;
    private static final int NUMBER_PASSWORD_INPUT_TYPE =
            TYPE_CLASS_NUMBER | TYPE_NUMBER_VARIATION_PASSWORD;
    private static final int TEXT_PASSWORD_INPUT_TYPE =
            TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD;
    private static final int TEXT_VISIBLE_PASSWORD_INPUT_TYPE =
            TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
    private static final int[] SUPPRESSING_AUTO_SPACES_FIELD_VARIATION = {
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
            InputType.TYPE_TEXT_VARIATION_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_URI,
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD };
    public static final int IME_ACTION_CUSTOM_LABEL = EditorInfo.IME_MASK_ACTION + 1;

    private InputTypeUtils() {
        // This utility class is not publicly instantiable.
    }
    private static boolean isWebEditTextInputType(int inputType) {
        return inputType == (TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
    }
    private static boolean isWebPasswordInputType(int inputType) {
        return WEB_TEXT_PASSWORD_INPUT_TYPE != 0
                && inputType == WEB_TEXT_PASSWORD_INPUT_TYPE;
    }
    private static boolean isWebEmailAddressInputType(int inputType) {
        return WEB_TEXT_EMAIL_ADDRESS_INPUT_TYPE != 0
                && inputType == WEB_TEXT_EMAIL_ADDRESS_INPUT_TYPE;
    }
    private static boolean isNumberPasswordInputType(int inputType) {
        return NUMBER_PASSWORD_INPUT_TYPE != 0
                && inputType == NUMBER_PASSWORD_INPUT_TYPE;
    }
    private static boolean isTextPasswordInputType(int inputType) {
        return inputType == TEXT_PASSWORD_INPUT_TYPE;
    }
    private static boolean isWebEmailAddressVariation(int variation) {
        return variation == TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS;
    }
    public static boolean isEmailVariation(int variation) {
        return variation == TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        || isWebEmailAddressVariation(variation);
    }
    public static boolean isWebInputType(int inputType) {
        final int maskedInputType =
                inputType & (TYPE_MASK_CLASS | TYPE_MASK_VARIATION);
        return isWebEditTextInputType(maskedInputType) || isWebPasswordInputType(maskedInputType) ||
        isWebEmailAddressInputType(maskedInputType);
    }
    // Please refer to TextView.isPasswordInputType
    public static boolean isPasswordInputType(int inputType) {
        final int maskedInputType =
                inputType & (TYPE_MASK_CLASS | TYPE_MASK_VARIATION);
        return isTextPasswordInputType(maskedInputType) || isWebPasswordInputType(maskedInputType)
                || isNumberPasswordInputType(maskedInputType);
    }
    // Please refer to TextView.isVisiblePasswordInputType
    public static boolean isVisiblePasswordInputType(int inputType) {
        final int maskedInputType =
                inputType & (TYPE_MASK_CLASS | TYPE_MASK_VARIATION);
        return maskedInputType == TEXT_VISIBLE_PASSWORD_INPUT_TYPE;
    }

    public static boolean isAutoSpaceFriendlyType(final int inputType) {
        if (TYPE_CLASS_TEXT != (TYPE_MASK_CLASS & inputType)) return false;
        final int variation = TYPE_MASK_VARIATION & inputType;
        for (final int fieldVariation : SUPPRESSING_AUTO_SPACES_FIELD_VARIATION) {
            if (variation == fieldVariation) return false;
        }
        return true;
    }

    public static int getImeOptionsActionIdFromEditorInfo(final EditorInfo editorInfo) {
        final int actionId = editorInfo.imeOptions & EditorInfo.IME_MASK_ACTION;
        if ((editorInfo.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
            return EditorInfo.IME_ACTION_NONE;
        } else if (editorInfo.actionLabel != null) {
            return IME_ACTION_CUSTOM_LABEL;
        } else {
            return actionId;
        }
    }

    public static int getConcreteActionIdFromEditorInfo(final EditorInfo editorInfo) {
        final int actionId = getImeOptionsActionIdFromEditorInfo(editorInfo);
        return actionId == InputTypeUtils.IME_ACTION_CUSTOM_LABEL ? editorInfo.actionId : actionId;
    }
}