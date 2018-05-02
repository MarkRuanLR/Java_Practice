package com.tw;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    public boolean someLibraryMethod() {

        return true;
    }

    public void menu() {

        printMenu();

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        boolean flag = true;
        int temp;
        for (; flag; ) {
            printMenu();
            temp = scanner.nextInt();
            if (temp == 1) {
                System.out.println("请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：");
                for (; true; ) {
                    boolean i = false;
                    i = addStudentInfo(scanner.next());
                    if (i)
                        break;
                    System.out.println("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
                }
            } else if (temp == 2) {
                printStudentScoreTable();
            } else if (temp == 3) {
                flag = false;
            }
        }
    }

    public void printMenu() {
        System.out.println("1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：");

    }

    public boolean addStudentInfo(String student_info) {
        Map<String, Integer> score = new HashMap<>();

        String[] student_infomation = student_info.split(", ");

        if (student_infomation.length < 2)
            return false;
        Student temp_student = new Student(student_infomation[0], student_infomation[1]);
        int sum = 0;
        for (int i = 2; i < student_infomation.length; i++) {
            String[] student_score = student_infomation[i].split(": ");
            score.put(student_score[0], Integer.valueOf(student_score[1]));

            sum = Integer.valueOf(student_score[1]) + sum;
        }
        temp_student.setSum(sum);
        temp_student.setAverage(sum / (student_infomation.length - 2));

        temp_student.setScore(score);
        students.add(temp_student);
        return true;
    }

    public void printStudentScoreTable() {
        List<String> courseList = new ArrayList<>();
        String courses = "";
        for (Student student : students) {
            courseList.addAll(student.getScore().keySet());
        }
        courseList = courseList.stream()
                .distinct()
                .collect(Collectors.toList());

        for (String course : courseList) {
            courses = courses + course + "|";
        }

        System.out.println("成绩单\n" +
                "姓名|" + courses + "平均分|总分\n" +
                "========================\n");

        String temp_score = "";

        for (Student student : students) {
            temp_score += student.getName() + "|";
            for (String course : courseList) {
                if (student.getScore().get(course) == null)
                    temp_score += "0|";
                else
                    temp_score += student.getScore().get(course).toString() + "|";
            }
            temp_score += String.valueOf(student.getAverage()) + "|" + String.valueOf(student.getSum());
            System.out.println(temp_score + "\n");
            temp_score = "";
        }

        System.out.println("========================\n");

        List<Integer> totalScore = new ArrayList<>();
        for (Student student : students) {
            totalScore.add(student.getSum());
        }

        totalScore.stream().sorted().collect(Collectors.toList());
        int sum_total = 0;
        for (Integer totalscore : totalScore) {
            sum_total += totalscore;
        }

        System.out.println("全班总分平均数：" + sum_total / totalScore.size());

        int midnumber = 0;
        if (totalScore.size() % 2 == 0)
            midnumber = totalScore.get(totalScore.size() / 2 - 1) + totalScore.get(totalScore.size() / 2);
        else
            midnumber = totalScore.get((totalScore.size() / 2));
        System.out.println("全班总分中位数：" + midnumber);
    }


    public List<Student> getStudents() {
        return students;
    }

    private List<Student> students = new ArrayList<>();

    public class Student {
        private String name;
        private String id;
        private Map<String, Integer> score;
        private int average;
        private int sum;

        public void setSum(int sum) {
            this.sum = sum;
        }

        public int getSum() {

            return sum;
        }

        public int getAverage() {

            return average;
        }

        public void setAverage(int average) {

            this.average = average;
        }

        public Student(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public Map<String, Integer> getScore() {
            return score;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setScore(Map<String, Integer> score) {
            this.score = score;
        }
    }
}
