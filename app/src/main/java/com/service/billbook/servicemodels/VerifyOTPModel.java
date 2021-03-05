package com.service.billbook.servicemodels;

public class VerifyOTPModel {


    private Body body;
    private String message;
    private String response;

    public static class Body {
        private String item4;
        private String item3;
        private String item2;
        private boolean item1;

        public String getItem4() {
            return item4;
        }

        public void setItem4(String item4) {
            this.item4 = item4;
        }

        public String getItem3() {
            return item3;
        }

        public void setItem3(String item3) {
            this.item3 = item3;
        }

        public String getItem2() {
            return item2;
        }

        public void setItem2(String item2) {
            this.item2 = item2;
        }

        public boolean isItem1() {
            return item1;
        }

        public void setItem1(boolean item1) {
            this.item1 = item1;
        }
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
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
}
