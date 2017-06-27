package com.example.bit.my_camera;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;



public class PermissionRequester {

    public static final int NOT_SUPPORT_VERSION = 2;

    public static final int ALREADY_GRANTED = -1;

    public static final int REQUEST_PERMISSION = 0;

    private Activity context;
    private android.app.AlertDialog.Builder builder;

    private void setBuilder(android.app.AlertDialog.Builder builder){

        this.builder = builder;
    }

    private  PermissionRequester(Activity context){

        this.context = context;
    }

    public int request(final String permission, final int requestCode, final OnClickDenyButtonListener denyAction){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            int permissionCheck = ContextCompat.checkSelfPermission(context, permission);

            if(permissionCheck == PackageManager.PERMISSION_DENIED){

                if(context.shouldShowRequestPermissionRationale(permission)){

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle(builder.getTitle())
                            .setMessage(builder.getMessage())
                            .setPositiveButton(builder.getPositiveButtonName(),new DialogInterface.OnClickListener(){
                           @Override
                            public void onClick(DialogInterface dialog, int which){

                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                context.requestPermission(new String[]{permission},requestCode);
                            }
                        }
                    });
                    .setNegativeButton(builder.getNegativeButtonName(),new DialogInterface.OnClickListener(){
                        @override
                        public void onClick(DialogInterface dialog,int which){
                                denyAction.onClick(context);
                        }
                    }).create().show();
                    return  REQUEST_PERMISSION;
                } else{

                    return ALREADY_GRANTED;
                }
            }

            return NOT_SUPPORT_VERSION;
        }
        public static class Builder{

            private PermissionRequest request;

            public Builder(Activity context){
                    request = new PermissionRequest(context);
            }
            private  String title = "권한 요청";
            private  String message ="기능의 사용을 위해 권한이 필요합니다.";
            private  String positiveButtonName = "네";
            private  String negativeButtonName ="아니요";

            public String getTitle(){
                return  title;
            }
            public  Builder setTitle(String title){
                this.title=title;
                return  this;
            }
            public  String  getMessage(){
                return message;
            }
            public  Builder setMessage(String message){
                this.message = message;
                return  this;
            }
            public String getPositiveButtonName(){
                return positiveButtonName;
            }
            public Builder setPositiveButtonName(String positiveButtonName){
                this.positiveButtonName =positiveButtonName;
                return  this;
            }
            public String getNegativeButtonName(){
                return  negativeButtonName;
            }
            public  Builder setNegativeButtonName;
            return this;
        }
        public PermissionRequest creat(){
            this.request.setBuilder(this);
            return  this.request;
    }

    }
        public interface onClickDenyButtonListener{
            public void onClick(Activity activity);
        }
}
