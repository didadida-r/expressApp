package com.example.groovemax1.uitest.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.groovemax1.uitest.R;

/**
 * 文件名：
 * 描述：自定义布局dialog
 * 作者：
 * 时间：
 */
public class MyDialog extends Dialog{

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder{
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private Button positiveButton;
        private View contentView;

        private DialogInterface.OnClickListener
                positiveButtonClickListener,
                negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog message from String
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Button getPositiveButton(){
            return positiveButton;
        }

        /**
         * Set the positive button text and it's listener
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public MyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MyDialog dialog = new MyDialog(context,
                    R.style.MyDialog);

            // instantiate the dialog layout
            View layout = inflater.inflate(R.layout.express_mydialog, null);
            dialog.addContentView(layout, new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));

            // set the dialog title
            ((TextView) layout.findViewById(R.id.dialogTitle)).setText(title);

            positiveButton = ((Button) layout.findViewById(R.id.dialogBtn));
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.dialogBtn))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialogBtn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialogBtn).setVisibility(
                        View.GONE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(
                        R.id.dialogContent)).setText(message);
            }
            /*
            else if (contentView != null) {
            // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView,
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));
            }
             */
            dialog.setContentView(layout);
            return dialog;
        }

    }
}
