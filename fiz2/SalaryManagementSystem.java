package fiz2;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


    public class SalaryManagementSystem {

        public void displayEmployeeInformation() {
            System.out.printf("%-15s%-15s%-20s%-20s%-15s\n", "Tên", "Chức vụ", "Số ngày làm thêm", "Số giờ làm thêm", "Tiền công");
            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                Employee employee = entry.getValue();
                System.out.printf("%-15s%-15s%-20s%-20s%-15s\n", employee.getName(), employee.getPosition(),
                        employee.getExtraDays(), employee instanceof PartTimeEmployee ? ((PartTimeEmployee) employee).getExtraHours() : "N/A",
                        employee.calculateSalary());
            }
        }

        private String getUserInput(String errorMessage, List<String> validPositions) {
            String userInput;

            do {

                userInput = scanner.nextLine().trim();

                if (!userInput.isEmpty() && validPositions.contains(userInput)) {
                    return userInput;
                } else {
                    System.out.println(errorMessage);
                }
            } while (true);
        }

        private int getIntInput(String prompt, String errorMessage) {

            int userInput;

            do {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (isInteger(input)) {
                    userInput = Integer.parseInt(input);
                    return userInput;
                } else {
                    System.out.println(errorMessage);
                }
            } while (true);
        }
        private boolean isInteger(String input) {
            try {
                Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        private String getUserInput(String prompt, String errorMessage) {
            String userInput;

            do {
                System.out.print(prompt);
                userInput = scanner.nextLine().trim();

                if (!userInput.isEmpty()) {
                    return userInput;
                } else {
                    System.out.println(errorMessage);
                }
            } while (true);
        }
        private LinkedHashMap<String, Employee> employees = new LinkedHashMap<>();
        private String currentMonth;
        private Scanner scanner = new Scanner(System.in);


        public SalaryManagementSystem() {
            this.currentMonth = getCurrentMonth();
            this.employees = new LinkedHashMap<>();
            this.employees = loadData();
            this.scanner = new Scanner(System.in);
        }
        private LinkedHashMap<String, Employee> loadData() {
            LinkedHashMap<String, Employee> loadedData = new LinkedHashMap<>();
            String fileName = "salary_" + currentMonth + ".txt";

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    String name = tokens[0];
                    String position = tokens[1];
                    int extraDays = Integer.parseInt(tokens[2]);
                    int extraHours = Integer.parseInt(tokens[3]);
                    String salary = tokens[4];

                    Employee employee = (position.equals("Quản lý") || position.equals("Công nhân")) ?
                            new FullTimeEmployee(name, position, extraDays) :
                            new PartTimeEmployee(name, position, extraDays, extraHours);

                    loadedData.put(name, employee);
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Lỗi tải dữ liệu từ tệp: " + e.getMessage());
            }

            return loadedData;
        }

        private void saveData() {
            String fileName = "salary_" + currentMonth + ".txt";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
                for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                    Employee employee = entry.getValue();
                    bw.write(entry.getKey() + "," + employee.getPosition() + ","
                            + employee.getExtraDays() + "," + (employee instanceof PartTimeEmployee ? ((PartTimeEmployee) employee).getExtraHours() : "0") + ","
                            + employee.calculateSalary() + "\n");
                }
            } catch (IOException e) {
                System.out.println("Lỗi ghi vào tệp: " + e.getMessage());
            }
        }

        private void saveToFile(String name, String position, int extraDays, int extraHours) {
            String fileName = "salary_" + currentMonth + ".txt";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                bw.write(name + "," + position + "," + extraDays + "," + extraHours + "," +
                        employees.get(name).calculateSalary() + "\n");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
        public void addEmployeeInformation() {
            int numberOfEmployees = getIntInput("Enter the number of employees to add: ", "Invalid input. Please enter a non-negative integer.");


            for (int i = 0; i < numberOfEmployees; i++) {
                System.out.println("Employee " + (i + 1) + ":");
                addEmployee();
            }
        }

        public void addEmployee() {
            String name = getUserInput("Enter employee name: ", "The entered name is not valid");

            System.out.print("Enter employee position (Quản lý/Công nhân/Thời vụ): ");
            String position = getUserInput("Invalid input. Please enter a valid position: ", Arrays.asList("Quản lý", "Công nhân", "Thời vụ"));

            int extraDays = 0;
            int extraHours = 0;

            if (position.equals("Quản lý") || position.equals("Công nhân")) {
                extraDays = getIntInput("Enter extra days:", "Invalid input.");
            } else if (position.equals("Thời vụ")) {
                extraDays = getIntInput("Enter extra days:", "Invalid input.");
                extraHours = getIntInput("Enter extra hours: ", "Invalid input. Please enter a non-negative integer.");
            }

            Employee employee;
            if (position.equals("Quản lý") || position.equals("Công nhân")) {
                employee = new FullTimeEmployee(name, position, extraDays);
            } else {
                employee = new PartTimeEmployee(name, position, extraDays, extraHours);
            }

            employees.put(name, employee);
            saveToFile(name, position, extraDays, extraHours);
        }




        public void displaySalaryInformation() {
            System.out.printf("%-15s%-15s%-20s%-20s%-15s\n", "Tên", "Chức vụ", "Số ngày làm thêm", "Số giờ làm thêm", "Tiền công");
            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                System.out.println(entry.getValue());
            }
        }

        public void editEmployee() {
            String name = getUserInput("Enter employee name to edit:", "Name cannot be empty.");

            if (employees.containsKey(name)) {
                Employee existingEmployee = employees.get(name);
                System.out.println("Current information:");
                System.out.println(existingEmployee);

                System.out.print("Enter new position (Quản lý/Công nhân/Thời vụ): ");
                String position = getUserInput("Invalid input. Please enter a valid position: ", Arrays.asList("Quản lý", "Công nhân", "Thời vụ"));

                int extraDays = 0;
                int extraHours = 0;

                if (position.equals("Quản lý") || position.equals("Công nhân")) {
                    extraDays = getIntInput("Enter new extra days: ", "Invalid input. Please enter a non-negative integer.");
                } else if (position.equals("Thời vụ")) {
                    extraHours = getIntInput("Enter new extra hours: ", "Invalid input. Please enter a non-negative integer.");
                }

                Employee newEmployee;
                if (position.equals("Quản lý") || position.equals("Công nhân")) {
                    newEmployee = new FullTimeEmployee(name, position);
                } else {
                    newEmployee = new PartTimeEmployee(name, position);
                    ((PartTimeEmployee) newEmployee).setExtraHours(extraHours);  // Set extra hours for PartTimeEmployee
                }

                newEmployee.setExtraDays(extraDays);  // Set extra days for all types of employees
                employees.put(name, newEmployee);
                updateFile();
                System.out.println("Employee information edited successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        }
        public void deleteEmployee() {
            String name = getUserInput("Enter employee name to delete: ","Invalid input.");

            if (employees.containsKey(name)) {
                employees.remove(name);
                updateFile();
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        }

        public void searchSalary() {
            String name = getUserInput("Enter employee name to search: ","Invalid input. ");

            String month = getUserInput("Enter month to search:","Invalid input.");

            searchSalary(name, month);
        }

        private void searchSalary(String name, String month) {
            String fileName = "salary_" + month + ".txt";
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[0].equals(name)) {
                        System.out.printf("%-15s%-15s%-20s%-20s%-15s\n", "Tên", "Chức vụ", "Số ngày làm thêm", "Số giờ làm thêm", "Tiền công");
                        System.out.printf("%-15s%-15s%-20s%-20s%-15s\n", tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
                        return;
                    }
                }
                System.out.println("Employee not found.");
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }

        public void displaySalary() {
            displaySalaryInformation();
        }

        private void updateFile() {
            String fileName = "salary_" + currentMonth + ".txt";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
                for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                    Employee employee = entry.getValue();
                    bw.write(entry.getKey() + "," + employee.getPosition() + ","
                            + employee.getExtraDays() + "," + (employee instanceof PartTimeEmployee ? ((PartTimeEmployee) employee).getExtraHours() : "0") + ","
                            + employee.calculateSalary() + "\n");

                    // Update the values in the original employee instance
                    entry.getValue().setExtraDays(employee.getExtraDays());
                    if (employee instanceof PartTimeEmployee) {
                        ((PartTimeEmployee) employee).setExtraHours(((PartTimeEmployee) employee).getExtraHours());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
            saveData();
        }

        private String getCurrentMonth() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            return dateFormat.format(calendar.getTime());
        }

        public static void main(String[] args) {
            SalaryManagementSystem system = new SalaryManagementSystem();
            int choice;

            do {
                System.out.println("1. Add employee information");
                System.out.println("2. Edit employee information");
                System.out.println("3. Delete employee information");
                System.out.println("4. Search salary information");
                System.out.println("5. Display salary information");
                System.out.println("0. Exit");

                System.out.print("Enter your choice: ");
                choice = system.scanner.nextInt();
                system.scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        system.addEmployeeInformation();
                        break;
                    case 2:
                        system.editEmployee();
                        break;
                    case 3:
                        system.deleteEmployee();
                        break;
                    case 4:
                        system.searchSalary();
                        break;
                    case 5:
                        system.displaySalary();
                        break;
                    case 0:
                        System.out.println("Exiting program. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 0);
        }



    }
