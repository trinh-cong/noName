package ooo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EmployeeManagementSystem {
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static final String fileName = "salary_202311.txt"; // Thay đổi tên file theo tháng hiện tại


    // Phương thức để ghi thông tin lương của nhân viên vào file
    public static void writeSalaryToFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Employee employee : employees) {
                bufferedWriter.write(employee.getSalaryInfo()); // Hàm này trả về thông tin lương của nhân viên dưới dạng String
                bufferedWriter.newLine(); // Xuống dòng cho thông tin nhân viên tiếp theo
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để thêm thông tin lương của nhân viên
    public static void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên nhân viên:");
        String name = scanner.nextLine();
        System.out.println("Nhập loại công nhân viên (Fulltime/Parttime):");
        String employeeType = scanner.nextLine();
        System.out.println("Nhập chức vụ (Quản lý/Công nhân):");
        String position = scanner.nextLine();

        System.out.println("Thông tin thêm (ngày làm thêm hoặc giờ làm thêm):");
        double extraTime = scanner.nextDouble(); // Nhập số ngày làm thêm hoặc số giờ làm thêm tùy theo loại công nhân viên
        int extraDays = 0;
        double extraHours = 0;
        if (employeeType.equalsIgnoreCase("Fulltime")) {
            extraDays = (int) extraTime;
        } else if (employeeType.equalsIgnoreCase("Parttime")) {
            extraHours = extraTime;
        }

        Employee employee = new Employee(name, employeeType, position, extraDays, extraHours);
        employees.add(employee);
    }

    // Phương thức để xóa thông tin lương của nhân viên
    public static void removeEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên nhân viên cần xóa:");
        String name = scanner.nextLine();
        employees.removeIf(employee -> employee.name.equals(name));
    }

    // Phương thức để sửa thông tin lương của nhân viên
    public static void editEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên nhân viên cần sửa:");
        String name = scanner.nextLine();

        for (Employee employee : employees) {
            if (employee.name.equals(name)) {
                // Cập nhật thông tin nhân viên
                System.out.println("Nhập chức vụ mới (Quản lý/Công nhân):");
                String newPosition = scanner.nextLine();
                if (employee.employeeType.equalsIgnoreCase("Fulltime")) {
                    // Cập nhật số ngày làm thêm cho nhân viên chính thức
                    System.out.println("Nhập số ngày làm thêm mới:");
                    int extraDays = scanner.nextInt();
                    employee.extraDays = extraDays;
                } else if (employee.employeeType.equalsIgnoreCase("Parttime")) {
                    // Cập nhật số giờ làm thêm cho nhân viên thời vụ
                    System.out.println("Nhập số giờ làm thêm mới:");
                    double extraHours = scanner.nextDouble();
                    employee.extraHours = extraHours;
                }
                // Cập nhật lại tổng lương
//                employee.totalSalary = employee.calculateSalary();
            }
        }
    }
//    search


    // Phương thức để hiển thị danh sách nhân viên và thông tin lương
    public static void displayEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee.getSalaryInfo());
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManagementSystem employeeManagementSystem = new EmployeeManagementSystem();

        while (true) {
            System.out.println("Chọn một trong các chức năng sau:");
            System.out.println("1. Thêm thông tin lương nhân viên");
            System.out.println("2. Xóa thông tin lương nhân viên");
            System.out.println("3. Sửa thông tin lương nhân viên");
            System.out.println("4. Hiển thị danh sách nhân viên và thông tin lương");
            System.out.println("5. Ghi thông tin lương vào file");
            System.out.println("6. Thoát chương trình");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    employeeManagementSystem.addEmployee();
                    break;
                case 2:
                    employeeManagementSystem.removeEmployee();
                    break;
                case 3:
                    employeeManagementSystem.editEmployee();
                    break;
                case 4:
                    employeeManagementSystem.displayEmployees();
                    break;
                case 5:
                    employeeManagementSystem.writeSalaryToFile(fileName);
                    break;
                case 6:
                    System.out.println("Thoát chương trình");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ");
            }
        }

    }
    private Map<String, Map<String, Double>> salaryRecords = new HashMap<>(); // Khai báo và khởi tạo biến 'salaryRecords'

    public Double searchSalaryRecord(String employeeName, String month) {
        if (salaryRecords.containsKey(employeeName)) {
            Map<String, Double> employeeRecord = salaryRecords.get(employeeName);
            if (employeeRecord.containsKey(month)) {
                return employeeRecord.get(month);
            }
        }
        return null;
    }

    }

