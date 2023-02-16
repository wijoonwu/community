package com.callbus.community.biz.domain.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Message {

    @Getter
    @AllArgsConstructor
    public static class DataMessage {

        private StatusEnum status;
        private String message;
        private Object data;

        public DataMessage() {
            this.status = StatusEnum.BAD_REQUEST;
            this.data = null;
            this.message = null;
        }

        public void setStatus(StatusEnum status) {
            this.status = status;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }


    @Getter
    @AllArgsConstructor
    public static class StatusMessage {
        private StatusEnum status;
        private String message;

        public StatusMessage(){
            this.status = StatusEnum.BAD_REQUEST;
            this.message = null;
        }

        public void setStatus(StatusEnum status){
            this.status = status;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
