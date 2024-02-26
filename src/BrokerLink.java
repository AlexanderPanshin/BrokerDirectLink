public class BrokerLink {
    private FileList fileList;
    private SymList symList;
    public BrokerLink(String fileFolder, String symFolder) {
        fileList = new FileList(fileFolder);
        symList = new SymList(symFolder);
    }
    public boolean comparisonList(){
        boolean compare =  fileList.getPathsList().equals(symList.getPathsList());
        System.out.println("Broker Link Сравнение листов дало = " + compare);
        return  compare;
    }
    public boolean loadProperty(){
        return fileList.loadProperty() && symList.loadProperty();
    }
    public void saveProperty(){
        fileList.saveProperty();
        symList.saveProperty();
    }
    public void startBroker(){
        var statusProperty = loadProperty();
        System.out.println("Broker Link load property : " + statusProperty);
        fileList.fillList();
        var statusCompare = comparisonList();
        System.out.println("Broker Link compare list status : " + statusCompare);
        if(!statusCompare){
            symList.creatSymLinks(fileList);
            symList.fillList();
            fileList.SaveTxt();
            saveProperty();
        }
    }
}
