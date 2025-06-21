
class Computer {
    
    private String cpu;
    private String ram;
    private String storage;
    private String graphicsCard;
    private boolean hasWiFi;
    private boolean hasBluetooth;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.hasWiFi = builder.hasWiFi;
        this.hasBluetooth = builder.hasBluetooth;
    }

    
    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String graphicsCard;
        private boolean hasWiFi;
        private boolean hasBluetooth;

        public Builder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder setRAM(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder setWiFi(boolean hasWiFi) {
            this.hasWiFi = hasWiFi;
            return this;
        }

        public Builder setBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

   
    public void showSpecs() {
        System.out.println("Computer Configuration:");
        System.out.println("CPU: " + cpu);
        System.out.println("RAM: " + ram);
        System.out.println("Storage: " + storage);
        System.out.println("Graphics Card: " + graphicsCard);
        System.out.println("WiFi: " + (hasWiFi ? "Yes" : "No"));
        System.out.println("Bluetooth: " + (hasBluetooth ? "Yes" : "No"));
        System.out.println();
    }
}


public class Main {
    public static void main(String[] args) {
       
        Computer gamingPC = new Computer.Builder()
                .setCPU("Intel Core i9")
                .setRAM("32GB")
                .setStorage("1TB SSD")
                .setGraphicsCard("NVIDIA RTX 4080")
                .setWiFi(true)
                .setBluetooth(true)
                .build();

      
        Computer officePC = new Computer.Builder()
                .setCPU("Intel Core i5")
                .setRAM("8GB")
                .setStorage("512GB SSD")
                .setWiFi(true)
                .setBluetooth(false)
                .build();

     
        gamingPC.showSpecs();
        officePC.showSpecs();
    }
}
