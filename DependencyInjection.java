
interface CustomerRepository {
    String findCustomerById(String customerId);
}


class CustomerRepositoryImpl implements CustomerRepository {
    public String findCustomerById(String customerId) {
       
        if (customerId.equals("C101")) {
            return "Customer: Divyadarshini A (Gold Member)";
        } else {
            return "Customer not found.";
        }
    }
}

class CustomerService {
    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void showCustomer(String customerId) {
        String result = repository.findCustomerById(customerId);
        System.out.println(result);
    }
}


public class Main {
    public static void main(String[] args) {
      
        CustomerRepository repo = new CustomerRepositoryImpl();

        CustomerService service = new CustomerService(repo);

        service.showCustomer("C101");
        service.showCustomer("C999");
    }
}
