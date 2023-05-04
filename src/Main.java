import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static StringBuilder tmpString = new StringBuilder();

    public static void main(String[] args) {

        //делаем массив с папками, файлами и файлом лога
        String[] paths = {
                "/Users/anatolii/games/src",
                "/Users/anatolii/games/res",
                "/Users/anatolii/games/saveGames",
                "/Users/anatolii/games/temp",
                "/Users/anatolii/games/src/main",
                "/Users/anatolii/games/src/main/Main.java",
                "/Users/anatolii/games/src/main/Utils.java",
                "/Users/anatolii/games/src/test",
                "/Users/anatolii/games/res/drawables",
                "/Users/anatolii/games/res/vectors",
                "/Users/anatolii/games/res/icons",
                "/Users/anatolii/games/temp/temp.txt"
        };
        //и еще один массив, чтобы проставить признак необходимого действия с элементом первого массива
        int[] typeOfAction = { //1 - это создать каталог, 2 - создать файл, 3 - записать данные в лог
                1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 3
        };

        try {
            for (int i = 0; i < typeOfAction.length; i++) { //идем по массиву с флагами действий

                //описываем, что делать если в массиве каталог
                if (typeOfAction[i] == 1) CreateDirectory(paths[i]); //берем из массива адрес папки и создаем ее

                //описываем, что делать внутри, если в массиве указание на файл или лог-файл
                if (typeOfAction[i] == 2 || typeOfAction[i] == 3)
                    CreateFile(paths[i]); //берем из массива адрес файла и создаем его

                // описываем, что делаем внутри, если в массиве указание на лог
                if (typeOfAction[i] == 3) CreateLog(paths[i]);  //создаем лог-файл по адресу из массива
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void CreateLog(String localPath) throws IOException {
        FileWriter out = new FileWriter(localPath);
        out.write(tmpString.toString());
        out.close();
    }

    ;

    public static void CreateFile(String localPath) throws IOException {
        File localTmpFile = new File(localPath);
        if (localTmpFile.createNewFile())  //пробуем создать файл
            writeLog(tmpString, localTmpFile, true); //если получилось - записываем лог успеха создания файла
        else
            writeLog(tmpString, localTmpFile, false); //иначе записываем неуспех
    }

    public static void CreateDirectory(String localPath) {
        File file = new File(localPath);
        if (file.mkdirs())  //если получается создать каталог, то
            writeLog(tmpString, file, true); //логируем создание каталога
        else
            writeLog(tmpString, file, false);
    }

    private static void writeLog(StringBuilder tmp, File file, boolean b) {
        tmp
                .append("\n") //добавляем новую строку
                .append(b ? "" : "Ошибка! ") //если в параметре true то ничего не добавляем, если false - ошибка
                .append(file.isDirectory() ? "Директория " : "Файл: ") //если в параметре передан каталог то в лог добавляем "Директория", иначе "Файл"
                .append(file.getAbsolutePath()) //добавляем полный путь переданного в параметре file
                .append(b ? " - создан" : " - не создан"); //добавляем в лог итог, если в параметре true - то создан, если false - то не создан
    }
}