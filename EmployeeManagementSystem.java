class Employee {
    int employeeId;
    String name;
    String position;
    double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String toString() {
        return "[" + employeeId + "] " + name + " - " + position + " - ₹" + salary;
    }
}

public class Main {
    private static Employee[] employees = new Employee[100]; // Fixed array size
    private static int count = 0;

    public static void addEmployee(Employee emp) {
        if (count < employees.length) {
            employees[count++] = emp;
            System.out.println("Added: " + emp.name);
        } else {
            System.out.println("Array full. Cannot add more employees.");
        }
    }

    public static Employee searchEmployee(int id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == id) {
                return employees[i];
            }
        }
        return null;
    }

    public static void deleteEmployee(int id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == id) {
                System.out.println("Deleted: " + employees[i].name);
                for (int j = i; j < count - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[--count] = null;
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public static void listEmployees() {
        System.out.println("\nEmployee List:");
        for (int i = 0; i < count; i++) {
            System.out.println(employees[i]);
        }
    }

    public static void main(String[] args) {
    
        addEmployee(new Employee(101, "Divya", "Manager", 80000));
        addEmployee(new Employee(102, "Arun", "Developer", 55000));
        addEmployee(new Employee(103, "Priya", "Designer", 60000));

        listEmployees();

        System.out.println("\nSearching for employee ID 102:");
        Employee e = searchEmployee(102);
        System.out.println(e != null ? " Found: " + e : " Not Found");

        deleteEmployee(102);

        listEmployees();
    }
}
