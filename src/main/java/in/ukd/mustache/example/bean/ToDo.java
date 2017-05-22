package in.ukd.mustache.example.bean;

import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

/**
 * Created by udadh on 5/22/2017.
 */
public class ToDo {

    public ToDo() {
    }

    public ToDo(String title, String text) {
        this.title = title;
        this.text = text;
        createdOn = new Date();
    }

    private String title;
    private String text;
    private boolean done = false;

    private Date createdOn;
    private Date completedOn;

    public void markAsDone() {
        done = true;
        completedOn = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public long doneSince() {
        return done ? Duration
                .between(createdOn.toInstant(), completedOn.toInstant())
                .toMinutes() : 0;
    }

    public Function<Object, Object> handleDone() {
        return (obj) -> done ? String.format("<small>Done %s minutes ago<small>", obj) : "";

    }

}
