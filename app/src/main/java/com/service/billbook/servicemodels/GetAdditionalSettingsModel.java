package com.service.billbook.servicemodels;

import java.util.List;

public class GetAdditionalSettingsModel {

    private Body body;
    private String message;
    private String response;
    public Body getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Body {
        private AdditionalColumn additionalColumn;
        private SettingList settingList;

        public AdditionalColumn getAdditionalColumn() {
            return additionalColumn;
        }

        public void setAdditionalColumn(AdditionalColumn additionalColumn) {
            this.additionalColumn = additionalColumn;
        }

        public SettingList getSettingList() {
            return settingList;
        }

        public void setSettingList(SettingList settingList) {
            this.settingList = settingList;
        }
    }

    public static class AdditionalColumn {
        private List<_AdditionalList> _AdditionalList;
        private int bigintAdditionalId;
        private int bigintUserId;
        private int bigintSettingTypeId;

        public List<_AdditionalList> get_AdditionalList() {
            return _AdditionalList;
        }

        public void set_AdditionalList(List<_AdditionalList> _AdditionalList) {
            this._AdditionalList = _AdditionalList;
        }

        public int getBigintAdditionalId() {
            return bigintAdditionalId;
        }

        public void setBigintAdditionalId(int bigintAdditionalId) {
            this.bigintAdditionalId = bigintAdditionalId;
        }

        public int getBigintUserId() {
            return bigintUserId;
        }

        public void setBigintUserId(int bigintUserId) {
            this.bigintUserId = bigintUserId;
        }

        public int getBigintSettingTypeId() {
            return bigintSettingTypeId;
        }

        public void setBigintSettingTypeId(int bigintSettingTypeId) {
            this.bigintSettingTypeId = bigintSettingTypeId;
        }
    }

    public static class _AdditionalList {
        private int bigintAdditionalId;
        private String strColumnName;
        private int bigintUserId;
        private int bigintSettingTypeId;

        public int getBigintAdditionalId() {
            return bigintAdditionalId;
        }

        public void setBigintAdditionalId(int bigintAdditionalId) {
            this.bigintAdditionalId = bigintAdditionalId;
        }

        public String getStrColumnName() {
            return strColumnName;
        }

        public void setStrColumnName(String strColumnName) {
            this.strColumnName = strColumnName;
        }

        public int getBigintUserId() {
            return bigintUserId;
        }

        public void setBigintUserId(int bigintUserId) {
            this.bigintUserId = bigintUserId;
        }

        public int getBigintSettingTypeId() {
            return bigintSettingTypeId;
        }

        public void setBigintSettingTypeId(int bigintSettingTypeId) {
            this.bigintSettingTypeId = bigintSettingTypeId;
        }
    }

    public static class SettingList {
        private List<_SettingList> _SettingList;
        private int bigintAdditionalId;
        private int bigintUserId;
        private int bigintSettingTypeId;

        public List<_SettingList> get_SettingList() {
            return _SettingList;
        }

        public void set_SettingList(List<_SettingList> _SettingList) {
            this._SettingList = _SettingList;
        }

        public int getBigintAdditionalId() {
            return bigintAdditionalId;
        }

        public void setBigintAdditionalId(int bigintAdditionalId) {
            this.bigintAdditionalId = bigintAdditionalId;
        }

        public int getBigintUserId() {
            return bigintUserId;
        }

        public void setBigintUserId(int bigintUserId) {
            this.bigintUserId = bigintUserId;
        }

        public int getBigintSettingTypeId() {
            return bigintSettingTypeId;
        }

        public void setBigintSettingTypeId(int bigintSettingTypeId) {
            this.bigintSettingTypeId = bigintSettingTypeId;
        }
    }

    public static class _SettingList {
        private int bigintAdditionalId;
        private int bigintUserId;
        private String settingValue;
        private boolean isDropdown;
        private boolean ischeckbox;
        private boolean isRadio;
        private boolean isactive;
        private String strIdName;
        private String strSettingTypeName;
        private int bigintSettingId;
        private int bigintSettingTypeId;

        public int getBigintAdditionalId() {
            return bigintAdditionalId;
        }

        public void setBigintAdditionalId(int bigintAdditionalId) {
            this.bigintAdditionalId = bigintAdditionalId;
        }

        public int getBigintUserId() {
            return bigintUserId;
        }

        public void setBigintUserId(int bigintUserId) {
            this.bigintUserId = bigintUserId;
        }

        public String getSettingValue() {
            return settingValue;
        }

        public void setSettingValue(String settingValue) {
            this.settingValue = settingValue;
        }

        public boolean getIsDropdown() {
            return isDropdown;
        }

        public void setIsDropdown(boolean isDropdown) {
            this.isDropdown = isDropdown;
        }

        public boolean getIscheckbox() {
            return ischeckbox;
        }

        public void setIscheckbox(boolean ischeckbox) {
            this.ischeckbox = ischeckbox;
        }

        public boolean getIsRadio() {
            return isRadio;
        }

        public void setIsRadio(boolean isRadio) {
            this.isRadio = isRadio;
        }

        public boolean getIsactive() {
            return isactive;
        }

        public void setIsactive(boolean isactive) {
            this.isactive = isactive;
        }

        public String getStrIdName() {
            return strIdName;
        }

        public void setStrIdName(String strIdName) {
            this.strIdName = strIdName;
        }

        public String getStrSettingTypeName() {
            return strSettingTypeName;
        }

        public void setStrSettingTypeName(String strSettingTypeName) {
            this.strSettingTypeName = strSettingTypeName;
        }

        public int getBigintSettingId() {
            return bigintSettingId;
        }

        public void setBigintSettingId(int bigintSettingId) {
            this.bigintSettingId = bigintSettingId;
        }

        public int getBigintSettingTypeId() {
            return bigintSettingTypeId;
        }

        public void setBigintSettingTypeId(int bigintSettingTypeId) {
            this.bigintSettingTypeId = bigintSettingTypeId;
        }
    }
}
