package ooo;



class Employee {
    String name;
    String employeeType;
    String position;
    int extraDays;
    double extraHours;
    double totalSalary;

    public Employee(String name, String employeeType, String position, int extraDays, double extraHours) {
        this.name = name;
        this.employeeType = employeeType;
        this.position = position;
        this.extraDays = extraDays;
        this.extraHours = extraHours;
        this.totalSalary = calculateSalary(); // Tính lương ngay khi khởi tạo đối tượng
    }

    private double calculateSalary() {
        if (employeeType.equals("Fulltime")) {
            if (position.equals("Quản lý")) {
                return 10000000 + (extraDays * 500000);
            } else if (position.equals("Công nhân")) {
                return 6000000 + (extraDays * 500000);
            }
        } else if (employeeType.equals("Parttime")) {
            return 240000 * extraHours;
        }
        return 0; // Giá trị mặc định, có thể điều chỉnh phù hợp với yêu cầu cụ thể
    }
    public String getSalaryInfo() {
        return "Name: " + name + ", Employee Type: " + employeeType + ", Position: " + position + ", Total Salary: " + totalSalary;
}
}