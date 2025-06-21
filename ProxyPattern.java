
interface Image {
    void display();
}


class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromRemoteServer(); // simulating expensive loading
    }

    private void loadFromRemoteServer() {
        System.out.println("Loading image from remote server: " + filename);
        try {
            Thread.sleep(1000); // Simulate delay
        } catch (InterruptedException e) {
            System.out.println("Interrupted while loading image.");
        }
    }

    public void display() {
        System.out.println("Displaying: " + filename);
    }
}


class ProxyImage implements Image {
    private String filename;
    private RealImage realImage;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // lazy load
        } else {
            System.out.println("Image loaded from cache: " + filename);
        }
        realImage.display();
    }
}


public class Main {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("dog.png");
        Image image2 = new ProxyImage("cat.jpg");

       
        image1.display();
        System.out.println();

        image2.display();
        System.out.println();

        image1.display();
    }
}
