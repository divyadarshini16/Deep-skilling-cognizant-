class Task {
    int taskId;
    String taskName;
    String status;

    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + taskId + "] " + taskName + " - " + status;
    }
}

class Node {
    Task task;
    Node next;

    public Node(Task task) {
        this.task = task;
        this.next = null;
    }
}

public class Main {
    private static Node head = null;

    public static void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }
        System.out.println(" Added: " + task.taskName);
    }

    public static void listTasks() {
        System.out.println("\nTask List:");
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.task);
            temp = temp.next;
        }
    }


    public static Task searchTask(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.task.taskId == id)
                return temp.task;
            temp = temp.next;
        }
        return null;
    }


    public static void deleteTask(int id) {
        if (head == null) {
            System.out.println(" Task list is empty.");
            return;
        }

        if (head.task.taskId == id) {
            System.out.println(" Deleted: " + head.task.taskName);
            head = head.next;
            return;
        }

        Node prev = head;
        Node curr = head.next;
        while (curr != null) {
            if (curr.task.taskId == id) {
                System.out.println("Deleted: " + curr.task.taskName);
                prev.next = curr.next;
                return;
            }
            prev = curr;
            curr = curr.next;
        }

        System.out.println(" Task not found.");
    }

    public static void main(String[] args) {
        addTask(new Task(1, "Design UI", "Pending"));
        addTask(new Task(2, "Build Backend", "In Progress"));
        addTask(new Task(3, "Write Tests", "Pending"));

        listTasks();

        System.out.println("\n Searching for task ID 2:");
        Task result = searchTask(2);
        System.out.println(result != null ? "Found: " + result : "Not Found");

        deleteTask(2);
        listTasks();
    }
}
