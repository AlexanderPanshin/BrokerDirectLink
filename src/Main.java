public class Main {
    public static void main(String[] args) {
        String fileFolder = "/run/media/punch/swap2/temp/music";
        String  symFolder = "/run/media/punch/swap2/temp/symLink";
        BrokerLink brokerLink = new BrokerLink(fileFolder,symFolder);
        while (true) {
            brokerLink.startBroker();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}