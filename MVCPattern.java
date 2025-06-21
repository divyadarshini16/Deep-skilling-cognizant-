
    private String name;
    private String id;
    private String grade;

  
    public Student(String name, String id, String grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

  
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}

class StudentView {
    public void displayStudentDetails(String name, String id, String grade) {
        System.out.println("=== Student Details ===");
        System.out.println("Name : " + name);
        System.out.println("ID   : " + id);
        System.out.println("Grade: " + grade);
    }
}


class StudentController {
    private Student model;
    private StudentView view;

    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView() {
        view.displayStudentDetails(model.getName(), model.getId(), model.getGrade());
    }


    public void setStudentName(String name) { model.setName(name); }
    public void setStudentGrade(String grade) { model.setGrade(grade); }
}


public class Main {
    public static void main(String[] args) {
       
        Student student = new Student("Divya", "S123", "A");

        StudentView view = new StudentView();

        StudentController controller = new StudentController(student, view);

        controller.updateView();

        controller.setStudentName("Divyadarshini");
        controller.setStudentGrade("A+");

        controller.updateView();
    }
}
