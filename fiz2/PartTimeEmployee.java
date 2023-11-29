package fiz2;


class PartTimeEmployee extends Employee {
    private static final double DAILY_WAGE = 240000;
    private static final double EXTRA_HOUR_RATE = 50000;

    public PartTimeEmployee(String name, String position, int extraDays, int extraHours) {
        super(name, position);
        this.extraDays = extraDays;
        this.extraHours = extraHours;
    }

    public PartTimeEmployee(String name, String position) {
        super(name, position);
        // Thêm đoạn này để tránh lỗi
        this.extraDays = 0;
        this.extraHours = 0;
    }

    @Override
    public double calculateSalary() {
        double dailyWage = DAILY_WAGE * extraDays;
        double extraSalary = EXTRA_HOUR_RATE * extraHours;
        return dailyWage + extraSalary;
    }

    // Thêm getter
    public int getExtraHours() {
        return extraHours;
    }
}