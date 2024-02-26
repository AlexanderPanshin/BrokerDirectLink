import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class SymList extends AbstractList{
    public SymList(String file) {
        super(Role.SYMLINK, file);
    }
    public boolean creatSymLinks(FileList files){
        LinkedHashSet<Path> filesSet = files.getPathsList();
        ArrayList <Path> fileList = new ArrayList<>(filesSet);
        ArrayList <Path> symList = new ArrayList<>(getPathsList());
        if(!fileList.isEmpty() && fileList.size()!=symList.size()){
            for (int i = 0; i < fileList.size();i++){
                try {
                    Path tempPath = Paths.get(getPathDirect() + "/" + fileList.get(i).getFileName());
                    if(!Files.exists(tempPath)) {
                        Files.createSymbolicLink(tempPath, fileList.get(i));
                    }
                } catch (IOException e) {
                    System.out.println("SimList not creat symLink");
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
        System.out.println("SimList массивы не равны нужно обновление");
        return false;
    }
}
