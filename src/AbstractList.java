import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public abstract class AbstractList {
    private LinkedHashSet<Path> pathsList;
    private LinkedHashSet<Path> errorList;
    private Role role;
    private String pathDirect;

    public AbstractList(Role role, String pathDirect) {
        pathsList = new LinkedHashSet<>();
        errorList = new LinkedHashSet<>();
        this.role = role;
        this.pathDirect = pathDirect;
    }

    public void fillList() {
        Path root = Paths.get(pathDirect);
        if (Files.notExists(root)) {
            System.out.println("Abstract list Директории не существует!");
            return;
        } else {
            try {
                Files.walkFileTree(root, new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        System.out.println("Abstract list добавляем в сет файл " + file.getFileName());
                        pathsList.add(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        errorList.add(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        System.out.println("Abstrct list Переход в директорию " + dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveProperty() {
        String name = (role == Role.FILE) ? "file.property" : "simLink.property";
        String saveFile = System.getProperty("user.dir") + "/" + name;
        System.out.println("Abstract list start method saveProperty");
        FileOutputStream saveFileStream;
        try {
            saveFileStream = new FileOutputStream(saveFile);
        } catch (FileNotFoundException e) {
            System.out.println("Не могу записать в файл");
            throw new RuntimeException(e);
        }
        Properties properties = new Properties();
        System.out.println("Abstract list method saveProperty save to : " + saveFile);
        if (!pathsList.isEmpty()) {
            properties.setProperty("Lenght", String.valueOf(pathsList.size()));
            int count = 0;
            for(Path path : pathsList){
                properties.setProperty(String.valueOf(count),path.toString());
                count = count + 1;
            }
            try {
                properties.store(saveFileStream, "Сохраненные данные");
            } catch (IOException e) {
                System.out.println("Сохранение не удалось " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
    public boolean loadProperty(){
        String name = (role == Role.FILE) ? "file.property" : "simLink.property";
        String loadFile = System.getProperty("user.dir") + "/" + name;
        File file = new File(loadFile);
        if(file.exists()){
            FileInputStream fis;
            Properties properties = new Properties();
            try {
                fis = new FileInputStream(loadFile);
                properties.load(fis);
                String lenghtStr = properties.getProperty("Lenght");
                int lenghyInt = Integer.parseInt(lenghtStr);
                for (int i = 0; i < lenghyInt; i++){
                    pathsList.add(Paths.get(properties.getProperty(String.valueOf(i))));
                }
            } catch (IOException e) {
                System.out.println("Abstract list error load property");
                throw new RuntimeException(e);
            }
            System.out.println("Abstract list load property");
            return true;
        }else {
            System.out.println("Abstract list file not found");
            return false;
        }
    }
    public void SaveTxt(){
        if (role == Role.FILE){
            File file = new File(pathDirect + "/list/listSymList.txt");//тут путь до директории м файлом
            String domain= "https://swap.mygemorr.ru:81/music/";
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                FileWriter writer = new FileWriter(file);
                if(!pathsList.isEmpty()){
                    for(Path p:pathsList){
                        System.out.println("Abstract List method Save txt = " + p.toString());
                        var temp = p.toString().split("music/");
                        if(temp.length>1) {
                            writer.write(domain + temp[1] + System.lineSeparator());
                        }
                    }
                }
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public LinkedHashSet<Path> getPathsList() {
        return pathsList;
    }

    public String getPathDirect() {
        return pathDirect;
    }
}
