package org.example;

import javax.persistence.*;

@Entity
@Table(name = "purposes")
public class Purposes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,updatable = false)
    int id;

    @Column(name = "purpose")
    int purpose = 5000;


    @OneToOne(cascade = CascadeType.REFRESH,mappedBy = "purposes")
    private User user;

    public Purposes() {
    }


    public Purposes(int purpose) {
        this.purpose = purpose;
    }
    public Purposes(int purpose, User user) {
        this.purpose = purpose;
        this.user = user;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }








    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Purposes{" +
                "id=" + id +
                ", purpose=" + purpose +
                ", user=" + user.getName() +
                '}';
    }
}
