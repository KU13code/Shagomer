package org.example;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final String PROSSIBLE_ANSWER1 = "регистрация";
    private static final String PROSSIBLE_ANSWER2 = "логин";


    private static final String ANSWER1 = "цель"; //!
    private static final String ANSWER2 = "ввести данные"; //!
    private static final String ANSWER3 = "вывести данные за месяц";
    private static final String ANSWER4 = "сумма шагов";
    private static final String ANSWER5 = "выход"; //!


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AnnotationConfiguration.class);

        UserDAO userDAO = context.getBean("userDAO", UserDAO.class);

        MonthsDAO monthsDAO = context.getBean("monthsDAO", MonthsDAO.class);

        Scanner scanner = new Scanner(System.in);

        String answerToDoing;
        String nameScan;
        String surnameScan;
        String loginScan;
        String passwordScan;
        String scanMonth;
        int scanDate;
        int scanStep;

        int idUser;

        while (true) {
            System.out.print("Логин || Регистрация: ");
            answerToDoing = scanner.nextLine().toLowerCase();

            switch (answerToDoing) {
                case PROSSIBLE_ANSWER1:
                    System.out.println("РЕГИСТРАЦИЯ!");
                    System.out.print("Введите Имя: ");
                    nameScan = scanner.nextLine();

                    System.out.print("Введите Фамилию: ");
                    surnameScan = scanner.nextLine();

                    System.out.print("Введите логин: ");
                    loginScan = scanner.nextLine();

                    System.out.print("Введите пароль: ");
                    passwordScan = scanner.nextLine();
                    userDAO.addNewUser(nameScan, surnameScan, loginScan, passwordScan);
                    break;


                case PROSSIBLE_ANSWER2:
                    System.out.println("Вход в Шагомер!");
                    System.out.print("Введите логин: ");
                    loginScan = scanner.nextLine();

                    System.out.print("Введите пароль: ");
                    passwordScan = scanner.nextLine();
                    if (userDAO.userLogin(loginScan, passwordScan) == true) {
                        System.out.println("Привет " + loginScan);
                        idUser = userDAO.userId(loginScan);
                    } else break;


                    boolean exit = true;
                    while (exit) {
                        System.out.print("цель || Ввести данные || вывести данные за месяц || сумма шагов || выход: ");
                        Scanner scan = new Scanner(System.in);
                        String scanOtvet;
                        scanOtvet = scan.nextLine().toLowerCase();
                        switch (scanOtvet) {
                            case ANSWER1:
                                System.out.print("цель на день: ");
                                try {
                                    Scanner scanPurpose = new Scanner(System.in);
                                    int purposeScan = scanPurpose.nextInt();
                                    userDAO.purpose(purposeScan, userDAO.userId(loginScan));

                                } catch (InputMismatchException e) {
                                    System.out.println("не корректные данные!!");
                                }
                                break;


                            case ANSWER2:
                                System.out.println("Пример: январь, февраль, март, апрель, май, июнь, июль," +
                                                            " август, сентябрь, октябрь, ноябрь, декабрь.");
                                System.out.print("введите месяц: ");
                                Scanner monthScan = new Scanner(System.in);
                                scanMonth = monthScan.nextLine();
                                if (monthsDAO.proverMes(scanMonth) == false) {
                                    break;
                                }
                                System.out.print("введите день: ");
                                Scanner dateScan = new Scanner(System.in);
                                scanDate = dateScan.nextInt();
                                if (monthsDAO.proverDate(scanDate, scanMonth) == false) {
                                    break;
                                }


                                if (monthsDAO.checkBase() == false) {
                                    if (userDAO.checkDay(scanDate, scanMonth, idUser) == true) { ////????
                                        System.out.println("такой день в базе уже заполнен перезаписать данные ? ДА / НЕТ");
                                        Scanner otvetScan = new Scanner(System.in);
                                        String otvet = otvetScan.nextLine().toLowerCase();
                                        if (otvet.equals("да")) {
                                            int id = monthsDAO.monthId(scanMonth, scanDate);
                                            System.out.print("число шагов: ");
                                            Scanner newStepScan = new Scanner(System.in);
                                            int stepNew = newStepScan.nextInt();
                                            monthsDAO.addToBase(stepNew, id);
                                            break;
                                        }
                                        if (otvet.equals("нет")) {
                                            break;
                                        }


                                    } else {
                                        //System.out.println("такой день отсутствует!");
                                        Scanner stepScan = new Scanner(System.in);
                                        System.out.print("введите шаги: ");
                                        Scanner stepScan1 = new Scanner(System.in);

                                        scanStep = stepScan1.nextInt();

                                        monthsDAO.saveToBase(scanStep, scanDate, scanMonth, idUser);
                                        break;
                                    }

                                } else
                                    System.out.print("введите шаги: ");
                                Scanner stepScan1 = new Scanner(System.in);
                                scanStep = stepScan1.nextInt();
                                monthsDAO.saveToBase(scanStep, scanDate, scanMonth, idUser);//////
                                break;


                            case ANSWER3:
                                System.out.print("\n" + "Введите месяц для вывода информации о нем: ");
                                Scanner scanStepOfDay = new Scanner(System.in);
                                scanMonth = scanStepOfDay.nextLine().toLowerCase();
                                userDAO.monthDay(scanMonth, idUser).forEach(System.out::println);
                                break;


                            case ANSWER4:
                                System.out.print("\n" + "Введите месяц чтобы узнать количество шагов: ");
                                Scanner sumOfSteps = new Scanner(System.in);
                                scanMonth = sumOfSteps.nextLine().toLowerCase();
                                userDAO.sumOfMonth(scanMonth, idUser);
                                break;


                            case ANSWER5:
                                exit = false;
                                break;

                            default:
                                System.out.println("Не верные данные! попробуйте еще раз.");
                                break;
                        }
                    }
            }
        }
    }
}