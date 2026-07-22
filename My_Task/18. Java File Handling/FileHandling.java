import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandling {
    public static void main (String [] args){

        // 1. create a folder
        File folder = new File("MyFolder");
        if(folder.mkdir()){
            System.out.println("Folder created successfully");
        }
        else{
            System.out.println("Folder already exists.");
        }


        // 2. create a file
        File file =new File("MyFolder/sample.txt");
        
       try {
            if (file.createNewFile()) {
            System.out.println("File created successfully");
        }else {
            System.out.println("File already exists.");
            
        }

         // 3. Write Data into the File

         FileWriter fw = new FileWriter("file");
         fw.write("Hello World");
         fw.write("\nWelcome to Java File Handling");
         fw.write("\nI am learning how to write data into a file.");
         fw.close();

         System.out.println("Data written to the file successfully.");

         // 4. Read Data from the File
         Scanner sc = new Scanner(file);
         System.out.println("Data read from the file:");

         while(sc.hasNextLine()){
            String line = sc.nextLine();
            System.out.println(line);
         }
         sc.close();

         //5. Delete the file

         if (file.delete()){
            System.out.println("File deleted successfully.");
         }else{
            System.out.println("Failed to delete the file.");
         }


        } catch (IOException e) {
           System.out.println("An error occurred: " + e.getMessage());
            
        }

    }
}