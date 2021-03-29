package com.zqq.shell.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.zqq.shell.R;
import com.zqq.shell.utils.DpUtils;
import com.zqq.shell.utils.StringUtils;
import com.zqq.shell.utils.ToastUtils;

import java.lang.reflect.Field;

public class EditTextLayout extends LinearLayout {

    float el_text_size = 16f; //字体大小
    int el_text_color = 0;  //字体颜色
    int el_backgroudn = 0;  //背景
    int el_style = 0 ; //显示的右侧按钮类型
    int el_inputtype= 0 ;//输入类型
    boolean el_ispassword = false; //是否是密码
    int el_regType = 0; //正则校验方式 1电话校验 2密码校验
    int el_max_length=0;//最大长度
    private Context context;

    private final static String reg_phone = "^1[3456789]\\d{9}$";
    private final static String reg_password = "^(?![0-9]+)(?![a-zA-Z]+)[0-9A-Za-z]{6,16}$";
    private String el_hint;

    public EditTextLayout(Context context) {
        this(context,null);
    }

    public EditTextLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditTextLayout(Context context, @Nullable AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);

        this.context =context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.EditTextLayout);

        el_text_size =
                typedArray.getDimension(R.styleable.EditTextLayout_el_text_size, DpUtils.dp2px(getContext(), 20f)); //字体大小
        el_text_color = typedArray.getColor(R.styleable.EditTextLayout_el_text_color, Color.BLACK); //字体颜色
        el_backgroudn =
                typedArray.getResourceId(R.styleable.EditTextLayout_el_background, R.drawable.el_background); //背景 颜色/图片
        el_style = typedArray.getInteger(R.styleable.EditTextLayout_el_style, 1); //显示右侧按钮类型 1清空 2没想好
        el_max_length = typedArray.getInteger(R.styleable.EditTextLayout_el_max_length, 0);
        el_inputtype =
                typedArray.getInt(R.styleable.EditTextLayout_el_inputtype, InputType.TYPE_CLASS_TEXT);
        el_ispassword =  typedArray.getBoolean(R.styleable.EditTextLayout_el_ispassword,false);

        el_regType = typedArray.getInt(R.styleable.EditTextLayout_el_regType, 0);
        el_hint = typedArray.getString(R.styleable.EditTextLayout_el_hint);

        init();
    }

    EditText editText = null;
    ImageView imageView = null;
    int dp2px = DpUtils.dp2px(getContext(), 10f);
    Bitmap bitmap = null;


    /**
     * 初始化自定义控件
     */
    @SuppressLint("NewApi")
    private void init() {
        setBackgroundResource(el_backgroudn);
        setPadding(dp2px,0,dp2px,0);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.delete);

        editText = new EditText(context);
        addView(editText);

        LinearLayout.LayoutParams et_layoutParams  = (LayoutParams) editText.getLayoutParams();
        et_layoutParams.gravity = Gravity.CENTER_VERTICAL;
        editText.setLayoutParams(et_layoutParams);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setSingleLine();
        if(el_inputtype == EditorInfo.TYPE_NUMBER_FLAG_DECIMAL){
            editText.setInputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL| EditorInfo.TYPE_CLASS_NUMBER);
        }else{
            editText.setInputType(el_inputtype);
        }

        editText.setTextSize( DpUtils.px2dp(context,el_text_size));
        editText.setHint(el_hint);
        editText.setHintTextColor(getResources().getColor(R.color.grey));
        //设置光标颜色
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, R.drawable.el_cursor_dra);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(el_max_length >0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(el_max_length)});
        }
        if(el_ispassword){
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        imageView =new ImageView(context);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.delete));
        addView(imageView);

        LinearLayout.LayoutParams iv_layoutParams  = (LayoutParams) imageView.getLayoutParams();
        iv_layoutParams.gravity = Gravity.CENTER_VERTICAL;
        iv_layoutParams.width = bitmap.getWidth();
        iv_layoutParams.height=bitmap.getHeight();
        imageView.setLayoutParams(iv_layoutParams);
        imageView.setVisibility(INVISIBLE);
        imageView.setFocusable(false);

        /**
         * 按钮清空
         */
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                editText.requestFocus();
                if(myOnDeleteClickListener!=null){
                    myOnDeleteClickListener.onClick(v);
                }
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    switch (el_regType){
                        case  1 :
                            {
                            String trim = editText.getText().toString().trim();
                            if(!StringUtils.isEmpty(trim)){
                                boolean matches = trim.matches(reg_phone);
                                if(!matches){
                                    ToastUtils.showMessage(context,"手机格式错误！");
                                }
                            }
                        }
                        break;
                        case   2 :
                            {
                            String trim = editText.getText().toString().trim();
                            if(!StringUtils.isEmpty(trim)){
                                boolean matches = trim.matches(reg_password);
                                if(!matches){
                                    ToastUtils.showMessage(context,"密码格式错误！6~18位数字字母");
                                }
                            }
                        }
                    }
                }else{
                    editText.selectAll();
                }
            }
        });


        /**
         * 事件监听
         */
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event!=null && event.getAction() == KeyEvent.ACTION_DOWN){  //物理键盘会触发两次
                    return false;
                }
                if( onActivtionListener != null) {
                    return onActivtionListener.onActivition(v, actionId, event);
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if(text.length()>0){
                    imageView.setVisibility(VISIBLE);
                }else{
                    imageView.setVisibility(INVISIBLE);
                }
                if(myOnAfterTextChange!=null) {
                    myOnAfterTextChange.afterTextChange(text);
                }
            }
        });
    }

    public void setGravity(int gravity){
        editText.setGravity(gravity);
    }


    /**
     * 获取焦点
     */
    public void requestFocusable(){
        editText.requestFocus();
    }

    public void setText(String str) {
        imageView.setVisibility(VISIBLE);
        editText.setText(str);
    }

    /**
     * 获取内容
     */
    public String getText(){
        return editText.getText().toString().trim();
    }

    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        for(int i=0;i<getChildCount();i++){
            View childAt = getChildAt(i);
            if(childAt instanceof EditText){
                editText.setWidth(width -bitmap.getWidth() - 2*dp2px-3);
            }
        }
    }

    /**
     * 输入框键盘事件监听
     */
    public void setOnActivtionListener(OnActivtionListener onActivtionListener){
        this.onActivtionListener = onActivtionListener;
    }

    private OnActivtionListener onActivtionListener;

    public void clear() {
        editText.setText(null);
    }

    public void setEditType(int editType) {
        editText.setInputType(editType);
    }

    public interface OnActivtionListener{
        boolean onActivition(TextView v, int actionId, KeyEvent event);
    }

    /**
     * 删除按钮点击监听
     */
    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener ){
        this.myOnDeleteClickListener = onDeleteClickListener;
    }

    OnDeleteClickListener myOnDeleteClickListener;

    public  interface OnDeleteClickListener{
        void onClick(View v);
    }

    /**
     * 输入框输入监听
     */
    public void setOnAfterTextChange(OnAfterTextChange onAfterTextChange){
        this.myOnAfterTextChange = onAfterTextChange;
    }


    OnAfterTextChange myOnAfterTextChange;

    public interface OnAfterTextChange{
        void afterTextChange(String text);
    }
}
