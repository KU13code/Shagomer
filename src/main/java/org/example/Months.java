package org.example;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "months")
public class Months {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,updatable = false)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "date")
    int date;

    @Column(name = "count")
    int count;



    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;




    public Months(String name, int date, int count) {
        this.name = name;
        this.date = date;
        this.count = count;
    }

    public Months(int id,String name, int date, int count, User user) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.count = count;
        this.user = user;
    }





    public Months() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Months{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", count=" + count +
                ", user=" + user.getName() +
                '}';
    }
}
