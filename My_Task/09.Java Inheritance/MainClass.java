class Employee8 {
    String Name;
    int id;
   

   void displayInfo(){
    System.out.println("Employee Name: " + Name);
    System.out.println("Employee ID: " + id);
   }
}
class FullTimeEmployee extends Employee8{
    double MonthlySalary;

    void CalculateSalary(){
        System.out.println("Monthly Salary:RS. " + MonthlySalary);
    }
}

class PartTimeEmployee extends Employee8{
    
    int hoursWorked;
    double hourlyRate;

    void calculateSalary(){
        double Salary= hoursWorked * hourlyRate;
        System.out.println("Part Time Salary:RS " + Salary);
    }

}

public class MainClass{
    public static void main(String[] args) {
        
       FullTimeEmployee f1= new FullTimeEmployee();
       
       f1.Name= "Izza";
       f1.id = 101;
       f1.MonthlySalary= 85000;
        
       System.out.println("\n \n");
       System.out.println("\n===== Full Time Employee =====\n");
       f1.displayInfo();
       f1.CalculateSalary();

        System.out.println();

       PartTimeEmployee p1= new PartTimeEmployee();
         
       p1.Name= "Fathima";
       p1.id = 102;
       p1.hoursWorked =80;
       p1.hourlyRate = 500;

       System.out.println("\n===== Part Time Employee =====\n");
       p1.displayInfo();
       p1.calculateSalary();
       System.out.println("\n \n");
    }
}
