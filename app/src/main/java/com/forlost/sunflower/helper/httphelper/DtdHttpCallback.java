package com.forlost.sunflower.helper.httphelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DtdHttpCallback<T> {
    Class<T> clazz;
    Type type;

    public Type getType() {
        return type;
    }

    public DtdHttpCallback() {
        Type genType = getClass().getGenericSuperclass();
        if (genType == null) {
            return;
        }
        type = ((ParameterizedType) genType).getActualTypeArguments()[0];
    }

    public void onStart() {

    }

    public void onSuccess(T result) {
    }

    //    public void onSuccess(JsonElement result) {
//    }
    public void onFailure(String msg) {

    }
}
