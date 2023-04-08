package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MonthsDAO {
    private final SessionFactory sessionFactory;



    Map<String, Integer> month2 = new HashMap<String, Integer>() {{
        put("январь", 31);
        put("февраль", 28);
        put("март", 31);
        put("апрель", 30);
        put("май", 31);
        put("июнь", 30);
        put("июль", 31);
        put("август", 31);
        put("сентябрь", 30);
        put("ноябрь", 31);
        put("октябрь", 30);
        put("декабрь", 31);
    }};

    public MonthsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //проверка существует ли месяц
    public boolean proverMes(String scanMonth) {
        boolean exit = true;
        while (exit) {
            if (month2.containsKey(scanMonth) == true) {
                //System.out.println("такой месяц существует");
                return true;
            } else
                System.out.println("такой месяц не существует");
            return false;
        }
        return false;
    }

    //проверка на дату в определенном месяце
    public boolean proverDate(int scanDate, String scanMonth) {
        boolean x = false;
        try {
            if (scanDate <= month2.get(scanMonth) && scanDate > 0) {
                //System.out.println("такой день существует в этом месяце");
                x = true;
                
            } else {
                System.out.println("такого числа в этом месяце нет!");
                x = false;
            }
        } catch (InputMismatchException e) {
            System.out.println("не верные данные!");
        }
        return x;
    }

    //проверка базы на данные
    public boolean checkBase() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Months> months = session.createQuery("from Months").getResultList();

        session.getTransaction().commit();
        session.close();

        boolean isEmpty = months.isEmpty();
        if (!isEmpty) {
            return false;
        } else return true;
    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //добовление в базу с данными
    public void addToBase(int scanStep, int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();


        Months months = session.get(Months.class, id);

        months.setCount(scanStep);
        session.save(months);

        session.getTransaction().commit();
        session.close();

    }

    public int monthId(String nameMonth, int dateMonth) {
        Months months = findMonths(nameMonth, dateMonth);
        return months.getId();
    }


    private List<Months> monthList() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Months> months = session.createQuery("from Months ").getResultList();
        session.getTransaction().commit();
        session.close();
        return months;
    }

    private Months findMonths(String nameMonth, int dateMonth) {
        for (Months months : monthList()) {
            if (months.name.equals(nameMonth) && months.date == dateMonth) {
                return months;
            }

        }
        return null;
    }



    //добавление значения в базу
    public void saveToBase(int scanStep, int scanDate, String scanMonth, int idUser) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User user = session.get(User.class, idUser);
        Months months = new Months(scanMonth, scanDate, scanStep);

        user.addMonths(months);

        session.save(months);
        session.getTransaction().commit();
        session.close();
    }
}

