package BabyNamesW4;
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Write a description of babyNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class babyNames {

    public void totalBirths(FileResource fr){
        int totalBirths = 0;
        int girlsTotal = 0;
        int boysTotal = 0;
        int girlsCount = 0;
        int boysCount = 0;
        
        for (CSVRecord record: fr.getCSVParser(false)){
           int rowBirth = Integer.parseInt(record.get(2));
           totalBirths += rowBirth;
           if (record.get(1).equals("M")){
               boysTotal += rowBirth;
               boysCount += 1;            
            } else {
               girlsTotal += rowBirth;
               girlsCount +=1;            
            }
        }
        
        System.out.println ("The total number of babies born was "+totalBirths);
        System.out.println ("The total number of girls born was "+ girlsTotal+" and the total number of boys born was "+boysTotal);
        System.out.println ("The total count of girls name was "+girlsCount+" and the total count of boys names was "+boysCount);
    
    }
    
    public String getFilePath (int year){
        String yearS = Integer.toString(year);
        String fileName = null;
        String filePath = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            fileName = f.getName();
            if (fileName.indexOf(yearS) != -1){
                filePath = f.getAbsolutePath();            
            }
        }
        
        return filePath;        
    }
    
    public int getRank (int year, String name, String gender){
        //int nameRank = -1;
        int nameRank = 0;
        //String filePath = getFilePath(year);
        //System.out.println(filePath);
        String filePath = "C:\\Users\\manmiche\\Documents\\ATA\\BabyNamesW4\\us_babynames_by_year\\yob"+year+".csv";
        //System.out.println(filePath);
        //int girlsCount = 0;
        //int boysCount = 0;
        if (filePath == null){
            return -1;
        }else{
            FileResource fr = new FileResource(filePath);
            for (CSVRecord rec: fr.getCSVParser(false)){
                
                //if (rec.get(1).equals("M")){
                //   boysCount += 1;            
                //} else {
                 //   girlsCount += 1;            
                //}
                
                if (rec.get(1).equals(gender)){
                    nameRank +=1;
                    if (rec.get(0).equals(name)){
                        return nameRank;
                    }
                }
                
                                      
            }
            
        }
        
        return -1;    
    }
    
    public String getName(int year, int rank, String gender){
        String rankName = "No Name";
        //String filePath = getFilePath(year);
        String filePath = "C:\\Users\\manmiche\\Documents\\ATA\\BabyNamesW4\\us_babynames_by_year\\yob"+year+".csv";
        //System.out.println(filePath);
        int girlsCount = 0;
        int boysCount = 0;
        if (filePath == null){
            return "No year file";
        }else{
            FileResource fr = new FileResource(filePath);
            for (CSVRecord rec: fr.getCSVParser(false)){
             
                if (rec.get(1).equals("M")){
                    boysCount += 1;            
                } else {
                    girlsCount += 1;            
                }
                
                if (gender.equals("M") && rank == boysCount){
                       rankName = rec.get(0);
                }else if (gender.equals ("F") && rank == girlsCount){
                       rankName = rec.get(0);
                }
                }
                        
                
            }
            
        return rankName;    
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int nameRank = getRank(year,name,gender);
        String newName = getName(newYear,nameRank,gender);
        System.out.println(name+" born in "+year+" would be "+newName+" if he/she was born in "+newYear);
                  
    }
    
    public void yearOfHighestRank (String name, String gender){
        int nameRank = -1;
        int yearHighest = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            String fileName = f.getName();
            int fileYear = Integer.parseInt(fileName.substring(3,7));
            System.out.println("File year is "+fileYear);
            int fileRank = getRank(fileYear,name,gender);
            System.out.println("Rank of this year is "+fileRank);
            if (nameRank == -1){
                nameRank = fileRank;
                yearHighest = fileYear;
                
            } else if ((fileRank != -1) && fileRank < nameRank){
                nameRank = fileRank;
                yearHighest = fileYear;
                
            }
            
        }
        
        if (nameRank == -1){
        System.out.println("This name is not in the file");
        }else {
            System.out.println("In year "+yearHighest+", "+name+" has the highest ranking of "+nameRank);
        }
    }
    
    public double getAvgRank(String name, String gender){
        double totalRank = 0;
        double countRank = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            String fileName = f.getName();
            int fileYear = Integer.parseInt(fileName.substring(3,7));
            double fileRank = getRank(fileYear,name,gender);
            System.out.println("Rank of this year is "+fileRank);
            if (fileRank != -1.0){
            totalRank += fileRank;
            countRank +=1;
           }
        }
        
        if (countRank == 0){
            return -1.0;       
        }else{
        double avgRank = totalRank/countRank;
        return avgRank;
        }
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int nameRank = getRank(year,name,gender);
        int totalBirths = 0;
        int boysCount = 0;
        int girlsCount = 0;
        String filePath = "C:\\Users\\manmiche\\Documents\\ATA\\BabyNamesW4\\us_babynames_by_year\\yob"+year+".csv";
        FileResource fr = new FileResource(filePath);
        for (CSVRecord rec: fr.getCSVParser(false)){
            String recGender = rec.get(1);
            
            if (recGender.equals("M")){
                    boysCount += 1;            
                } else {
                    girlsCount += 1;            
                }
            
            if (gender.equals("M") && recGender.equals(gender) && nameRank > boysCount){
                totalBirths += Integer.parseInt(rec.get(2));
            }else if (gender.equals("F")&& recGender.equals(gender) && nameRank > girlsCount){
                totalBirths += Integer.parseInt(rec.get(2));
            }
            
        }
        
        return totalBirths;
    
    }
    
    public void testYear(){
        String name = "Genevieve";
        String gender = "F";
        yearOfHighestRank (name,gender);
        //System.out.println(getAvgRank(name,gender));
        //System.out.println(getTotalBirthsRankedHigher(1990,name,gender));
    }
    
    public void testWhatNameInYear(){
        String name = "Owen";
        int year = 1974;
        int newYear = 2014;
        String gender = "M";
        whatIsNameInYear(name,year,newYear,gender);    
    }
    
    public void testTotalBirths(){
        FileResource fr = new FileResource();
        totalBirths(fr);    
    }
    
    public void testgetRank(){
        int nameRank = getRank(1971,"Frank","M");
        if (nameRank == -1){
            System.out.println("This name does not exist in the year file");        
        }else{
        
        System.out.println("The ranking is "+nameRank);
       }
    }
    
    public void testGetName(){
        String name = getName(1982,450,"M");
        System.out.println("The name with this ranking is "+name);
    
    }
    
}
