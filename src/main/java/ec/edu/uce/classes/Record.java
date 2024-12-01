package ec.edu.uce.classes;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Singleton
    public class Record {
        private String from;
        private String to;
        private String message;

        @PostConstruct
        public void init() {
            this.from = "example.com";
            this.to = "customer@example2.com";
        }
        
        public Record (){
        }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public  String getdata(){
            return String.format("From: %s\nTo: %s,\nMessage: %s", this.from, this.to, this.message);


    }


}

