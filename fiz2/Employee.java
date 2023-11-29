package fiz2;


public abstract class Employee {
    protected String name;
    protected String position;
    protected int extraDays;
    protected int extraHours;

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public abstract double calculateSalary();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getExtraDays() {
        return extraDays;
    }

    public void setExtraDays(int extraDays) {
        this.extraDays = extraDays;
    }

    public int getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(int extraHours) {
        this.extraHours = extraHours;
    }

    @Override
    public String toString() {
        return String.format("%-15s%-15s%-20s%-20s%-15s\n", name, position, extraDays, extraHours, calculateSalary());
    }
}