package fiz2;



class FullTimeEmployee extends Employee {
    private static final double BASE_SALARY_MANAGER = 10000000;
    private static final double BASE_SALARY_WORKER = 6000000;
    private static final double DAILY_WAGE = 500000;

    public FullTimeEmployee(String name, String position, int extraDays) {
        super(name, position);
        this.extraDays = extraDays;
    }

    public FullTimeEmployee(String name, String position) {
        super(name, position);
        // Thêm đoạn này để tránh lỗi
        this.extraDays = 0;
    }

    @Override
    public double calculateSalary() {
        double baseSalary = position.equals("Quản lý") ? BASE_SALARY_MANAGER : BASE_SALARY_WORKER;
        double extraSalary = DAILY_WAGE * extraDays;
        return baseSalary + extraSalary;
    }

    // Thêm getter
    public int getExtraDays() {
        return extraDays;
    }
}
