package com.service.billbook.servicemodels;

import java.util.List;

public  class CategoryListModel {

    private List<Body> body;
    private String message;
    private String response;

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
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

    public static class Body {
        private String strCategoryName;
        private int bigintUserId;
        private int bigintCategoryId;
        private int number_of_items;

        public String getStrCategoryName() {
            return strCategoryName;
        }

        public void setStrCategoryName(String strCategoryName) {
            this.strCategoryName = strCategoryName;
        }

        public int getBigintUserId() {
            return bigintUserId;
        }

        public void setBigintUserId(int bigintUserId) {
            this.bigintUserId = bigintUserId;
        }

        public int getBigintCategoryId() {
            return bigintCategoryId;
        }

        public void setBigintCategoryId(int bigintCategoryId) {
            this.bigintCategoryId = bigintCategoryId;
        }

        public int getNumber_of_items() {
            return number_of_items;
        }

        public void setNumber_of_items(int number_of_items) {
            this.number_of_items = number_of_items;
        }
    }
}
