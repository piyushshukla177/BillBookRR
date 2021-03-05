package com.service.billbook.model;

import java.io.Serializable;

public class CustomFieldModel implements Serializable {

    private String FieldName;
    private String FieldId;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getFieldId() {
        return FieldId;
    }

    public void setFieldId(String fieldId) {
        FieldId = fieldId;
    }
}
