package com.datadriven;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DataDrivenTesting {

    public void readExcel(String filepath, String filename, String sheetName) throws IOException {
        File filePath;
        File file=new File(filePath+"\\"+filename);
        FileInputStream fis=new FileInputStream(file);
        Workbook loginWorkbook=null;

        String fileExtension=filename.substring(filename.indexOf("."));
        if(fileExtension.equals(".xlsx"))
        {
            loginWorkbook=new XSSFWorkbook(fis);
        }
        else if(fileExtension.equals("xls"))
        {
            loginWorkbook=new HSSFWorkbook(fis);
        }
        Sheet loginSheet=loginWorkbook.getSheet(sheetName);
        int rowCount=loginSheet.getLastRowNum()-loginSheet.getFirstRowNum();
        for(int i=1;i<rowCount+1;i++)
        {
            Row row=loginSheet.getRow(i);
            String username=row.getCell(0).getStringCellValue();
            String password=row.getCell(0).getStringCellValue();
            test(username,password);
        }
    }

    public void test(String username,String password){
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://learner.demo.edunext.co/");

        driver.findElement(By.cssSelector("#login-email")).sendKeys(username);
        driver.findElement(By.cssSelector("#login-password")).sendKeys(password);
        driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
        Thread.sleep(3000);
        driver.quit();
    }

    public static void main(String[] args) throws IOException {
        DataDrivenTesting readFile=new DataDrivenTesting();
        String filePath="/home/knoldus/Documents/DDT/TestData";
        readFile.readExcel(filePath, "ExportExcel.xlsx","Sheet1");

    }
}
