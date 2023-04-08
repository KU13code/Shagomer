package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,updatable = false)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purpose_id")
    private Purposes purposes;

    public Purposes getPurposes() {
        return purposes;
    }

    public void setPurposes(Purposes purposes) {
        this.purposes = purposes;
    }



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")                    // исправления 22.03.23
    //@JoinColumn(name = "user_id")                                             // исправления 22.03.23
    private List<Months> months;


    public User(String name, String surname, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    public User() {
    }


    public void addMonths(Months mon) {
        if (months == null) {
            months = new ArrayList<>();
        }months.add(mon);
        mon.setUser(this);
    }



    public void addPurposes(Purposes pur) {
        if (this.purposes != null) {
            purposes.setPurpose(pur.getPurpose());
        } else this.purposes = pur;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Months> getMonths() {
        return months;
    }

    public void setMonths(List<Months> months) {
        this.months = months;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", purposes=" + purposes +
                ", months=" + months +
                '}';
    }
}
