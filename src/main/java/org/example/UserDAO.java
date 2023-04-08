package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {


    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int  userId(String login){
        User us = findUserByUsername(login);
        return us.getId();

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //вход в личный кабинет
    public boolean userLogin(String login,String password){
        User userFromLogin = findUserByUsername(login);
        if (userFromLogin == null){
            System.out.println("Такой пользователь не существует");
            return false;
        }else {
            if (correctPassword(password,userFromLogin)){
                return true;
                //System.out.println("Привет " + login);
            }else {
                System.out.println("не верный пароль!");
            }
        }return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    //добовление пользователя
    public void addNewUser(String name,String surname,String login,String password){
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            User user = new User(name,surname,login,password);
            session.save(user);
            session.getTransaction().commit();
            session.close();
        }catch (IllegalStateException e){
            System.out.println("не выерные данные в добовлении пользователя! Exception ");
        }
        //////////////////добовлениецели по умолчанию ////////////////////////////////////////
        User userFromLogin = findUserByUsername(login);
        Purposes p = new Purposes();
        purpose(p.getPurpose(), userFromLogin.id);

    }



    private List<User> userList() {
        Session session =sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User ").getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    private User findUserByUsername(String login){
        for (User user : userList()){
            if (user.getUsername().equals(login)){
                return user;
            }
        }return null;
    }

    private boolean correctPassword(String password,User userFromLogin){
        return userFromLogin.getPassword().equals(password);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



  //изменение цели
    public void purpose(int purpose,int id){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = session.get(User.class,id);

        Purposes purposes = new Purposes(purpose,user);

//        user.setPurposes(purposes);
        user.addPurposes(purposes);

        //session.save(user);
        session.persist(user);

        session.getTransaction().commit();
        session.close();
    }



//    public void monthAdd(String month, int day, int step,int id) {
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        User user = session.get(User.class, id);
//
//        Months months = new Months(month,day,step);
//
//
//        user.addMonths(months);
//
//
//        session.persist(user);
//
//        session.getTransaction().commit();
//        session.close();
//
//    }

    public List<Months> monthDay(String monthName,int idUser) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, idUser);

        List<Months> monthDataList = new ArrayList<>();
        for (Months monthLine : user.getMonths()) {
            if (monthLine.getName().equals(monthName)) {
                Months month = new Months(monthLine.getId(), monthLine.getName(),
                        monthLine.getDate(), monthLine.getCount(), user);
                monthDataList.add(month);
            }
        }
        session.getTransaction().commit();
        session.close();
        return monthDataList;
    }

    public void sumOfMonth(String monthName,int idUser) {
        int result = 0;
        Session session;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = session.get(User.class, idUser);

        for (Months monthLine : user.getMonths()) {
            if (monthLine.getName().equals(monthName)) {
                result += monthLine.getCount();
            }
        }
        session.getTransaction().commit();
        session.close();
        System.out.println("В месяц : " + monthName + " в сумме вы прошли : " + result + "\n");
    }


    //проверяет и перезаписывает шаги если день уже заполнен
    public boolean checkDay(int dayScan, String monthsScan, int idUser) {
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    User user = session.get(User.class, idUser);


    for (Months monthLine : user.getMonths()) {

        if (monthLine.getDate() == dayScan && monthsScan.equals(monthLine.getName())) {
            session.getTransaction().commit();
            session.close();
            return true;
        }
    }
    session.getTransaction().commit();
    session.close();
    return false;
}



}
